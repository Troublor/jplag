package jplag.solidity;

import java.util.HashMap;
import java.util.Map;

public class SolidityToken extends jplag.Token implements SolidityTokenConstants {
    private static Integer serialCount = 0;
    private static Map<Integer, String> serialMap = new HashMap<>();

    private int line, column, length;

    private String text;

    public SolidityToken(int type, String file, int line, int column, int length) {
        super(type, file, line, column, length);
        this.text = "";
    }

    public SolidityToken(String text, String file, int line, int column, int length) {
        super(-1, file, line, column, length);
        this.text = text;
        this.type = serialize(text);
    }

    private int serialize(String text) {
        int baseSerialCount = 100;
        var serial = baseSerialCount + SolidityToken.serialCount;
        SolidityToken.serialMap.put(serial, text);
        SolidityToken.serialCount++;
        return serial;
    }

    public boolean isMarked() { return this.marked; }

    public void setMarked(boolean marked) { this.marked = marked; }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public int getLength() {
        return length;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public static String type2string(int type) {
        switch (type) {
            case SolidityTokenConstants.FILE_END:
                return "****************";
            case SolidityTokenConstants.SEPARATOR_TOKEN:
                return "METHOD_SEPARATOR";
            case SolidityTokenConstants.PRAGMA:
                return "PRAGMA          ";
            case SolidityTokenConstants.CONTRACT_BEGIN:
                return "CONTRACT       {";
            case SolidityTokenConstants.CONTRACT_END:
                return "}       CONTRACT";
            case SolidityTokenConstants.FUNCTION_BEGIN:
                return "FUNCTION       {";
            case SolidityTokenConstants.FUNCTION_END:
                return "}       FUNCTION";
            case SolidityTokenConstants.STRUCT_BEGIN:
                return "STRUCT         {";
            case SolidityTokenConstants.STRUCT_END:
                return "}         STRUCT";
            case SolidityTokenConstants.CONSTRUCTOR_BEGIN:
                return "CONSTRUCTOR    {";
            case SolidityTokenConstants.CONSTRUCTOR_END:
                return "}    CONSTRUCTOR";
            case SolidityTokenConstants.MODIFIER_BEGIN:
                return "MODIFIER       {";
            case SolidityTokenConstants.MODIFIER_END:
                return "}       MODIFIER";
            case SolidityTokenConstants.IF_BEGIN:
                return "IF             {";
            case SolidityTokenConstants.IF_END:
                return "}             IF";
            case SolidityTokenConstants.WHILE_BEGIN:
                return "WHILE          {";
            case SolidityTokenConstants.WHILE_END:
                return "}          WHILE";
            case SolidityTokenConstants.DO_WHILE_BEGIN:
                return "DO_WHILE       {";
            case SolidityTokenConstants.DO_WHILE_END:
                return "}       DO_WHILE";
            case SolidityTokenConstants.STATE_DECLARE:
                return "STATE_DECLARE   ";
            case SolidityTokenConstants.EVENT_DECLARE:
                return "EVENT_DECLARE   ";
            case SolidityTokenConstants.VAR_DECLARE:
                return "VAR_DECLARE     ";
            case SolidityTokenConstants.ENUM_DECLARE:
                return "ENUM_DECLARE    ";
            case SolidityTokenConstants.ASSIGN:
                return "ASSIGN          ";
            case SolidityTokenConstants.APPLY:
                return "APPLY           ";
            case SolidityTokenConstants.USING_FOR:
                return "USING_FOR       ";
            case SolidityTokenConstants.EMIT:
                return "EMIT            ";
            case SolidityTokenConstants.THROW:
                return "THROW           ";
            case SolidityTokenConstants.FUNC_CALL:
                return "FUNC_CALL       ";
            case SolidityTokenConstants.RETURN:
                return "RETURN          ";
            case SolidityTokenConstants.BREAK:
                return "BREAK           ";
            case SolidityTokenConstants.CONTINUE:
                return "CONTINUE        ";
            case SolidityTokenConstants.REQUIRE:
                return "REQUIRE         ";
            case SolidityTokenConstants.ASSERT:
                return "ASSERT          ";

            default:
//                System.err.println("*UNKNOWN: " + type);
                return "LITERAL        ";
        }
    }


    public static int numberOfTokens() {
        return NUM_DIFF_TOKENS;
    }
}
