package Javathree11;

import java.io.IOException;
//import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.List;

public class Shank
{
    public static void main(String[] args) throws IOException
    {
                Path myPath = Paths.get("shankcontent.txt"); //file will be shankcontent.txt
        ArrayList<Token> arrayOfTokens = new ArrayList<>(); //???
        List < String > lines = Files.readAllLines(myPath); //read all lines from the shankcontent file
        Lexer lexerInstance = new Lexer(); //make instance for Lexer class with lexerInstance
      lexerInstance.Lex(lines); //call Lex(lines) method from Lexer
System.out.println("fails after here");
  Parser callParse = new Parser(arrayOfTokens);
     callParse.parse();

    }
}


