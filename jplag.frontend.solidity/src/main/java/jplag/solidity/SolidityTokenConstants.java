package jplag.solidity;

public interface SolidityTokenConstants extends jplag.TokenConstants {

    final static int FILE_END = 0;
    final static int SEPARATOR_TOKEN = 1;

    final static int LITERAL = 2;

    final static int PRAGMA = 3;

    final static int CONTRACT_BEGIN = 4;
    final static int CONTRACT_END = 5;
    final static int FUNCTION_BEGIN = 6;
    final static int FUNCTION_END = 7;
    final static int STRUCT_BEGIN = 8;
    final static int STRUCT_END = 9;
    final static int CONSTRUCTOR_BEGIN = 10;
    final static int CONSTRUCTOR_END = 11;
    final static int MODIFIER_BEGIN = 12;
    final static int MODIFIER_END = 13;
    final static int IF_BEGIN = 14;
    final static int IF_END = 15;
    final static int WHILE_BEGIN = 16;
    final static int WHILE_END=17;
    final static int DO_WHILE_BEGIN = 18;
    final static int DO_WHILE_END = 19;

    final static int STATE_DECLARE = 24;
    final static int EVENT_DECLARE = 25;
    final static int VAR_DECLARE = 26;
    final static int ENUM_DECLARE = 27;

    final static int ASSIGN = 41;
    final static int APPLY = 42;
    final static int USING_FOR = 43;
    final static int EMIT = 44;
    final static int THROW = 55;
    final static int FUNC_CALL = 56;
    final static int RETURN = 57;
    final static int BREAK = 58;
    final static int CONTINUE = 59;
    final static int REQUIRE = 60;



    final static int NUM_DIFF_TOKENS = 33;
}
