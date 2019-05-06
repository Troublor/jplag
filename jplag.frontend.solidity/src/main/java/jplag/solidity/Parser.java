package jplag.solidity;

import jplag.Structure;
import jplag.solidity.grammar.SolidityLexer;
import jplag.solidity.grammar.SolidityParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Parser extends jplag.Parser implements SolidityTokenConstants {

    private Structure struct = new Structure();
    private String currentFile;

    public static void main(String args[]) {
        if (args.length < 1) {
            System.out.println("Only one or more files as parameter allowed.");
            System.exit(-1);
        }
        Parser parser = new Parser();
        parser.setProgram(new jplag.StrippedProgram());
        jplag.Structure struct = parser.parse(null, args);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(args[0])));
            int lineNr = 1;
            int token = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                if (token < struct.size()) {
                    boolean first = true;
                    while (struct.tokens[token] != null
                            && struct.tokens[token].getLine() == lineNr) {
                        if (!first) {
                            System.out.println();
                        }
                        SolidityToken tok = (SolidityToken) struct.tokens[token];
                        System.out.print(SolidityToken.type2string(tok.type) + " ("
                                + tok.getLine() + ","
                                + tok.getColumn() + ","
                                + tok.getLength() + ")\t");
                        first = false;
                        token++;
                    }
                    if (first) {
                        System.out.print("                \t");
                    }
                }
                System.out.println(line);
                lineNr++;
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public jplag.Structure parse(File dir, String files[]) {
        struct = new Structure();
        errors = 0;
        for (int i = 0; i < files.length; i++) {
//            getProgram().print(null, "Parsing file " + files[i] + "\n");
            if (!parseFile(dir, files[i])) {
                errors++;
            }
            System.gc();//Emeric
            struct.addToken(new SolidityToken(FILE_END, files[i], -1, -1, -1));
        }
        this.parseEnd();
        return struct;
    }

    public boolean parseFile(File dir, String file) {
        BufferedInputStream fis;

        ANTLRInputStream input;
        try {
            fis = new BufferedInputStream(new FileInputStream(new File(dir, file)));
            currentFile = file;
            input = new ANTLRInputStream(fis);

            // create a lexer that feeds off of input CharStream
            SolidityLexer lexer = new SolidityLexer(input);

            // create a buffer of tokens pulled from the lexer
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            // create a parser that feeds off the tokens buffer
            SolidityParser parser = new SolidityParser(tokens);
//            File_inputContext in = parser.file_input();
            SolidityParser.SourceUnitContext in = parser.sourceUnit();

            ParseTreeWalker ptw = new ParseTreeWalker();
            for (int i = 0; i < in.getChildCount(); i++) {
                ParseTree pt = in.getChild(i);
                ptw.walk(new JplagSolidityListener(this), pt);
            }

        } catch (IOException e) {
            getProgram().addError("Parsing Error in '" + file + "':\n" + e.getMessage() + "\n");
            return false;
        }

        return true;
    }

    public void add(int type, org.antlr.v4.runtime.Token tok) {
        struct.addToken(new SolidityToken(type, (currentFile == null ? "null" : currentFile), tok.getLine(), tok.getCharPositionInLine() + 1,
                tok.getText().length()));
    }

    /**
     * add literal token
     *
     * @param text String
     */
    public void add(String text, org.antlr.v4.runtime.Token tok) {
        struct.addToken(new SolidityToken(text, (currentFile == null ? "null" : currentFile), tok.getLine(),
                tok.getCharPositionInLine() + 1,
                tok.getText().length()));

    }

    public void addEnd(int type, org.antlr.v4.runtime.Token tok) {
        struct.addToken(new SolidityToken(type, (currentFile == null ? "null" : currentFile), tok.getLine(), struct.tokens[struct.size() - 1].getColumn() + 1, 0));
    }

}
