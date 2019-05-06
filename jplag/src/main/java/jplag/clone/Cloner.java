package jplag.clone;

import jplag.*;
import jplag.clone.util.Pair;
import jplag.solidity.SolidityToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.*;

import static jplag.solidity.SolidityTokenConstants.FILE_END;
import static jplag.solidity.SolidityTokenConstants.SEPARATOR_TOKEN;

public class Cloner {
    private String root_dir;
    private Language language;
    private List<Contract> contracts;
    private List<List<Pair<Contract, Double>>> clusters;

    private Matches matches = new Matches();

    public Cloner() throws ExitException {
        this.contracts = new Vector<>();
        try {
            Constructor<?>[] languageConstructors = Class.forName("jplag.solidity.Language").getDeclaredConstructors();
            Constructor<?> cons = languageConstructors[1];
//            Object[] ob = { program };
            // All Language have to have a program as Constructor
            // Parameter
            // ->public Language(ProgramI prog)
            Language tmp = (Language) cons.newInstance();
            this.language = tmp;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new jplag.ExitException("Illegal value: Language instantiation failed", ExitException.BAD_LANGUAGE_ERROR);
        }
    }

    public String getRoot_dir() {
        return root_dir;
    }

    public void setRoot_dir(String root_dir) {
        this.root_dir = root_dir;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public void createTasks() throws ExitException, IOException {
        File root_dir = new File(this.root_dir);
        if (!root_dir.isDirectory()) {
            throw new jplag.ExitException("\"" + this.root_dir + "\"" + "is not a directory");
        }
        String[] list = null;
        try {
            list = root_dir.list();
        } catch (SecurityException e) {
            throw new jplag.ExitException("Unable to retrieve directory: " + this.root_dir + " Cause : " + e.toString());
        }
        assert list != null;

        for (int i = 0; i < list.length; i++) {
            File f = new File(root_dir, list[i]);
            if (!f.isDirectory()) {
                this.contracts.add(new Contract(this.language, root_dir, f.getName()));
            }
        }
        this.contracts.sort(new SortByTime());
    }

    public void cluster() {
        this.clusters = new ArrayList<>();
        AllMatches match = null;
        for (var contract : this.contracts) {
            var added = false;
            for (var i = 0; i < clusters.size(); i++) {
                var bucket = clusters.get(i);
                var similar = false;
                for (var innerC : bucket) {
                    match = this.compare(contract, innerC.getVal1());
                    if (match.percentMaxAB() >= 50) {
                        similar = true;
                        break;
                    }
                }
                if (similar) {
                    bucket.add(new Pair<Contract, Double>(contract, (double) match.percentMaxAB()));
                    added = true;
                    break;
                }
            }
            if (!added) {
                var b = new ArrayList<Pair<Contract, Double>>();
                b.add(new Pair<>(contract, 100.0));
                this.clusters.add(b);
            }
        }
    }

    public AllMatches compare(Contract c1, Contract c2) {
        Structure structA = c1.struct;
        Structure structB = c2.struct;

        var mml = this.language.min_token_match();

        // FILE_END used as pivot

        // init
        Token[] A = structA.tokens;
        Token[] B = structB.tokens;
        int lengthA = structA.size() - 1;  // minus pivots!
        int lengthB = structB.size() - 1;  // minus pivots!
        AllMatches allMatches = new AllMatches(c1, c2);

        if (lengthA < mml || lengthB < mml)
            return allMatches;

        // Initialize

        for (int i = 0; i <= lengthA; i++)
            A[i].marked = A[i].type == FILE_END || A[i].type == SEPARATOR_TOKEN;

        for (int i = 0; i <= lengthB; i++)
            B[i].marked = B[i].type == FILE_END || B[i].type == SEPARATOR_TOKEN;


        // start:
        if (structA.hash_length != this.language.min_token_match()) {
            create_hashes(structA, mml, false);
        }
        if (structB.hash_length != this.language.min_token_match()
                || structB.table == null) {
            create_hashes(structB, mml, true);
        }

        int maxmatch;
        int[] elemsB;

        do {
            maxmatch = mml;
            matches.clear();
            for (int x = 0; x <= lengthA - maxmatch; x++) {
                if (A[x].marked || A[x].hash == -1
                        || (elemsB = structB.table.get(A[x].hash)) == null)
                    continue;
                inner:
                for (int i = 1; i <= elemsB[0]; i++) { // elemsB[0] contains the length of the Array
                    int y = elemsB[i];
                    if (B[y].marked || maxmatch > lengthB - y) continue;

                    int j, hx, hy;
                    for (j = maxmatch - 1; j >= 0; j--) { //begins comparison from behind
                        if (A[hx = x + j].type != B[hy = y + j].type || A[hx].marked || B[hy].marked)
                            continue inner;
                    }

                    // expand match
                    j = maxmatch;
                    while (A[hx = x + j].type == B[hy = y + j].type && !A[hx].marked && !B[hy].marked)
                        j++;

                    if (j > maxmatch) {  // new biggest match? -> delete current smaller
                        matches.clear();
                        maxmatch = j;
                    }
                    matches.addMatch(x, y, j);  // add match
                }
            }
            for (int i = matches.size() - 1; i >= 0; i--) {
                int x = matches.matches[i].startA;  // begining of sequence A
                int y = matches.matches[i].startB;  // begining of sequence B
                allMatches.addMatch(x, y, matches.matches[i].length);
                //in order that "Match" will be newly build     (because reusing)
                for (int j = matches.matches[i].length; j > 0; j--)
                    A[x++].marked = B[y++].marked = true;   // mark all Token!
            }

        } while (maxmatch != mml);

        return allMatches;
    }

    public void create_hashes(Structure s, int hashLength, boolean makeTable) {
        // Hier wird die obere Grenze der Hash-Laenge festgelegt.
        // Sie ist bestimmt durch die Bitzahl des 'int' Datentyps und der Anzahl
        // der Token.
        if (hashLength < 1) hashLength = 1;
        hashLength = (hashLength < 26 ? hashLength : 25);

        if (s.size() < hashLength) return;

        int modulo = ((1 << 6) - 1);   // Modulo 64!

        int loops = s.size() - hashLength;
        s.table = (makeTable ? new Table(3 * loops) : null);
        int hash = 0;
        int i;
        int hashedLength = 0;
        for (i = 0; i < hashLength; i++) {
            hash = (2 * hash) + (s.tokens[i].type & modulo);
            hashedLength++;
            if (s.tokens[i].marked)
                hashedLength = 0;
        }
        int factor = (hashLength != 1 ? (2 << (hashLength - 2)) : 1);

        if (makeTable) {
            for (i = 0; i < loops; i++) {
                if (hashedLength >= hashLength) {
                    s.tokens[i].hash = hash;
                    s.table.add(hash, i);   // add into hashtable
                } else
                    s.tokens[i].hash = -1;
                hash -= factor * (s.tokens[i].type & modulo);
                hash = (2 * hash) + (s.tokens[i + hashLength].type & modulo);
                if (s.tokens[i + hashLength].marked)
                    hashedLength = 0;
                else
                    hashedLength++;
            }
        } else {
            for (i = 0; i < loops; i++) {
                s.tokens[i].hash = (hashedLength >= hashLength) ? hash : -1;
                hash -= factor * (s.tokens[i].type & modulo);
                hash = (2 * hash) + (s.tokens[i + hashLength].type & modulo);
                if (s.tokens[i + hashLength].marked)
                    hashedLength = 0;
                else
                    hashedLength++;
            }
        }
        s.hash_length = hashLength;
    }

    public static void main(String[] args) throws ExitException, IOException {
        Cloner cloner = new Cloner();
        cloner.setRoot_dir("/home/troublor/Desktop/contract_code");
        cloner.createTasks();
        cloner.cluster();
        System.out.println();
    }
}
