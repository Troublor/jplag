package jplag.clone;

import jplag.*;

import javax.naming.directory.InvalidAttributesException;
import java.io.*;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jplag.solidity.SolidityTokenConstants.FILE_END;
import static jplag.solidity.SolidityTokenConstants.SEPARATOR_TOKEN;

public class Cluster {
    private Set<Bucket> buckets;
    private Double similarityThreshold;

    private Matches matches = new Matches();

    public Cluster(Double similarityThreshold) throws ExitException {
        this.similarityThreshold = similarityThreshold;
        this.buckets = new HashSet<>();
    }

    public void add(Contract contract) {
        var added = false;
        for (var bucket :
                this.buckets) {
            double sim = compare(bucket.getBaseContract(), contract);
            if (sim >= this.similarityThreshold) {
                bucket.add(contract, sim);
                added = true;
            }
        }
        if (!added) {
            this.buckets.add(new Bucket(contract));
        }
    }

    public void save(String cluster_dir_path) throws IOException {
        File dir = new File(cluster_dir_path);
        if (!dir.exists()) {
            var r = dir.mkdirs();
            if (!r) {
                throw new IOException(String.format("Could not create dir %s", cluster_dir_path));
            }
        }
        File clusterInfoFile = new File(dir, "ClusterInfo.dat");
        if (!clusterInfoFile.exists()) {
            if (!clusterInfoFile.createNewFile()) {
                throw new IOException(String.format("Could not create file %s", clusterInfoFile.getName()));
            }
        }
        FileWriter fw = new FileWriter(clusterInfoFile.getAbsoluteFile());
        fw.write(String.format("%f", this.similarityThreshold));
        fw.close();
        int count = 0;
        for (var bucket :
                this.buckets) {
            File bucketDir = new File(dir, String.format("bucket%d", count));
            if (!bucketDir.exists()) {
                var r = bucketDir.mkdirs();
                if (!r) {
                    throw new IOException(String.format("Could not create dir %s", bucketDir.getName()));
                }
            }
            bucket.save(bucketDir);
            count++;
        }
    }

    public static Cluster retrive(String cluster_dir_path) throws IOException, ExitException {
        var cluster = new Cluster(50.0);
        File dir = new File(cluster_dir_path);
        if (!dir.exists()) {
            return cluster;
        }
        File clusterInfoFile = new File(dir, "ClusterInfo.dat");
        var fr = new FileReader(clusterInfoFile);
        var br = new BufferedReader(fr);
        var line = br.readLine();
        cluster.similarityThreshold = Double.parseDouble(line);
        for (var f :
                dir.listFiles()) {
            if (f.isDirectory()) {
                try {
                    cluster.buckets.add(Bucket.retrive(f));
                } catch (InvalidAttributesException ignored) {
                }
            }
        }
        return cluster;
    }

    public Double compare(Contract c1, Contract c2) {
        Structure structA = c1.struct;
        Structure structB = c2.struct;

        var mml = Contract.LANGUAGE.min_token_match();

        // FILE_END used as pivot

        // init
        Token[] A = structA.tokens;
        Token[] B = structB.tokens;
        int lengthA = structA.size() - 1;  // minus pivots!
        int lengthB = structB.size() - 1;  // minus pivots!
        AllMatches allMatches = new AllMatches(c1, c2);

        if (lengthA < mml || lengthB < mml)
            return (double) allMatches.percentMaxAB();

        // Initialize

        for (int i = 0; i <= lengthA; i++)
            A[i].marked = A[i].type == FILE_END || A[i].type == SEPARATOR_TOKEN;

        for (int i = 0; i <= lengthB; i++)
            B[i].marked = B[i].type == FILE_END || B[i].type == SEPARATOR_TOKEN;


        // start:
        if (structA.hash_length != Contract.LANGUAGE.min_token_match()) {
            create_hashes(structA, mml, false);
        }
        if (structB.hash_length != Contract.LANGUAGE.min_token_match()
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

        return (double) allMatches.percentMaxAB();
    }

    private void create_hashes(Structure s, int hashLength, boolean makeTable) {
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

}
