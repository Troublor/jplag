package jplag.solidity;

import jplag.ProgramI;

import java.io.File;

public class Language implements jplag.Language {

    private jplag.solidity.Parser parser;

    public Language(){
        this.parser = new jplag.solidity.Parser();
        this.parser.setProgram(null);
    }

    public Language(ProgramI program) {
        this.parser = new jplag.solidity.Parser();
        this.parser.setProgram(program);
    }

    public String[] suffixes() {
        String[] res = {".sol", ".txt"};
        return res;
    }

    public int errorsCount() {
        return this.parser.errorsCount();
    }

    public String name() {
        return "Solidity Parser";
    }

    public String getShortName() {
        return "solidity";
    }

    public int min_token_match() {
        return 12;
    }

    public jplag.Structure parse(File dir, String[] files) {
        return this.parser.parse(dir, files);
    }

    public boolean errors() {
        return this.parser.getErrors();
    }

    public boolean supportsColumns() {
        return true;
    }

    public boolean isPreformated() {
        return true;
    }

    public boolean usesIndex() {
        return false;
    }

    public int noOfTokens() {
        return jplag.solidity.SolidityToken.numberOfTokens();
    }

    public String type2string(int type) {
        return jplag.solidity.SolidityToken.type2string(type);
    }
}
