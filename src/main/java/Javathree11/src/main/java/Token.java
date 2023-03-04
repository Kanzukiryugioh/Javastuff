package Javathree11.src.main.java;

//import java.util.Objects;

//token class
public class Token {
    public enum types {
        Number, EndOfLine,
        FOR, WHILE, DO, WORD,
        BLOCK, DEFINE, IDENTIFIER, SQUAREROOT,
        IF, ELSIF, ELSE, LEFTPARENTHESIS, RIGHTPARENTHESIS,
        MULTIPLY, EXPONENT, DIVIDE, ADD, SUBTRACT, FALSE,
        TRUE, EQUAL, LESSTHAN, GREATERTHAN, NOT, NULL, OR, CONSTANT,
        INDENT, DEDENT, CHARACTER, INTEGER, REAL, FROM, TO,
        ENDSTATEMENT, FUNCTIONCALL, THEN, STRING, SWITCH, CASE,
        ARRAYOF, BOOLEAN, VAR, AND, START, END, REPEATUNTIL, MOD, NOTEQUAL, READ, WRITE,
        LEFTSOMESTRING, RIGHTSOMESTRING, SUBSTRING, DATATYPE, ASSIGNVARIABLE, FINALLY,
        STRINGLITERAL, LESSTHANOREQUAL, GREATERTHANOREQUAL, EMPTYPARENTHESIS, CHARACTERLITERAL
    }//token enum types //REAL is FLOAT

    //identifier enum used to be word. Most are keywords

    public String text; //for token value
    public String textValue;
    types tokentypes;

    public Token(String tokenValue, types wordsAndDigits) {
        textValue = tokenValue; //private text = tokenvalue
        text = wordsAndDigits + "(" + tokenValue + ")"; //this will be displayed when printed from Lexer
        tokentypes = wordsAndDigits;
    }


    public Token(String tokenValue, types wordsAndDigits, types ENDOFLINE)//token constructor for whether it's a word or number
    {

        textValue = tokenValue; //private text = tokenvalue
        text = wordsAndDigits + "(" + tokenValue + ")" + "" + ENDOFLINE; //this will be displayed when printed from Lexer
        tokentypes = wordsAndDigits;
    }

    public Token(types ENDOFLINE) {

        text = "" + ENDOFLINE;
        tokentypes = ENDOFLINE; //rid maybe
    }

    public String toString() {
        return text; //return value of text in Token constructor
    }

    public types getAToken() {
        return tokentypes;
    }

    public String getStringValue() {
        return textValue;
    }
}

