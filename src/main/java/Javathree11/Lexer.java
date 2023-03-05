package Javathree11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Lexer {


    List<Token> arrayOfTokens = new ArrayList<>();

    public List<Token> getAllTokens() {
        return arrayOfTokens;
    }

    public List<Token> Lex(List<String> lines) {
        HashMap<String, Token.types> knownWords = createKnownWordsMap();


        int leftparensCurrent = 0; //grow per leftparenthesis if exists
        int rightparensCurrent = 0; //grow if exists until value equals left current

        //make save state for when you are currently in boolean state for if else while
        //and when in defining float or number where number is in either an integer or float and go back to other state with if done

        int lineNumber = 0; //finding line on
        int nestedComments = 0; //check if nested comments are in the comments state

        StringBuilder rebuildWord = new StringBuilder(); //used for appending string
        int i = 0; //initial value for lines size

        char halfDoubleQuote = '"';//half double quote for string literal

        int totalDoubleQuotes = 0;
        int beforeResetState = 0; //used for checking current state before returning to the while loop start

        char halfsinglequote = '\''; //half single quote for character literal
        int totalSinglequotes = 0; //should equal 2 when finished and reset to initial
        int totalCharacterLiterals = 0; //should be only 2 when finished reset to initial
        //totalstring and charliterals shouldn't intersect

        int decimalsInNumberState = 0; //use as a counter for numbers tokens with decimals

        int lineOn = i; //because line 1 actually begins on line 0

        if (i == lines.size()) { //if blank file
            //  String zero ="NULL1";
            Token token0 = new Token(Token.types.EndOfLine); //this will be used so letters appended will become a token type Number()
            // token0.toString(); //call to string from token class
            // System.out.println(token0 + " line: " + lineNumber); //On line 0
            System.out.println(token0); //it will display Word with words from the build end

            /** add token to array **/
            arrayOfTokens.add(token0);


            decimalsInNumberState = 0; //set to 0 since end of line reached
            rebuildWord.setLength(0); //set stringbuilder back to 0


        }

        while (i < lines.size()) {

            String line;
            line = lines.get(i); //line will get lines at current element i

            char letter; //used for letters and numbers
            String emptyState = "Start"; //initial state
            int k = 0;

            if (k == line.length()) { //if empty line but something exists after then make this token.
                //  String zero ="NULL2";
                Token token0 = new Token(Token.types.EndOfLine); //make end of line token
                // token0.toString(); //call to string from token class

                /** add a completed token to an array list**/
                arrayOfTokens.add(token0);//add each token


                lineNumber++;
                System.out.println(token0); //it will display Word with words from the build end
                System.out.println(token0 + " line: " + lineNumber); //display again with current line finished Token was it's on
                //should be lineNumber+1.

                System.out.println();

                decimalsInNumberState = 0; //set to 0 since end of line reached
                rebuildWord.setLength(0); //set stringbuilder back to 0

            }


            while (k < line.length()) { //end loop when k is greater than the string line length of the file


                letter = line.charAt(k); //letter will be the current element that k is on in the line
//make a state for word that accepts plus minus multiply divide > < = : := || so it can reach the hash due to it appending
                if (emptyState.equals("Start"))  //Start state
                {
                    if (Character.isDigit(letter)) {//check if character for letter variable current is a number
                        System.out.println("Continuing Number/Word/Identifier State");
                        System.out.println();
                        //otherwise continue as normal
                        emptyState = "Number"; //make it move to number state
                        System.out.println("number state"); //display number state
                        System.out.println();

                        System.out.println("was last state comment?");
                        System.out.println("was last state character literal?");
                        System.out.println("was last state string literal?");


                        if (beforeResetState == 3) //force comment state upon if true
                        {
                            emptyState = "Comment";
                            System.out.println("Comment");
                        } else if (beforeResetState == 9002) //make state goto string literal
                        {
                            emptyState = "StringLiteralState";
                            System.out.println("String Literal");
                        } else if (beforeResetState == 9001) //make state go to character literal
                        {
                            emptyState = "CharLiteralState";
                            System.out.println("Character Literal");
                            totalCharacterLiterals++; //increase and later this is used to check if total characters are above 1

                        }


                    }
                    else if (Character.isAlphabetic(letter)) { //check if current is a Alphabetic letter


                        System.out.println("Continuing Word/Identifier State");


                        emptyState = "Word"; //make it move to word state
                        System.out.println("word state"); //display word state
                        System.out.println();


                        System.out.println("was last state comment?");
                        System.out.println("was last state character literal or string literal?");
                        if (beforeResetState == 3) //force comment state upon if true
                        {
                            // emptyState = "Comment";
                            System.out.println("Comment");
                        } else if (beforeResetState == 9002) //make state goto string literal
                        {
                            emptyState = "StringLiteralState";
                            System.out.println("String Literal");
                        } else if (beforeResetState == 9001) //make state go to character literal
                        {
                            emptyState = "CharLiteralState";
                            System.out.println("Character Literal");
                            totalCharacterLiterals++; //increase and later this is used to check to see if total characters for this state are above 1
                        }


                    }
                    /*Symbols to use with word state first occurence*/
                    else if ("><=:!&()^*/%+-|?".contains(letter + "")) {
                        emptyState = "Word";
                        System.out.println("go to word state");
                    }
                    else if (letter == '.') { //if a period
                        System.out.println("was last state comment?");

/*if one of the beforeResetStates are true
decimal counter should not grow
 */
                        if (beforeResetState == 3) //force comment state upon if true
                        {
                            emptyState = "Comment";
                            System.out.println("Comment");
                        } else if (beforeResetState == 9002) //make state go to string literal
                        {
                            emptyState = "StringLiteralState";
                            System.out.println("String Literal");

                        } else if (beforeResetState == 9001) //make state go to character literal
                        {
                            emptyState = "CharLiteralState";
                            System.out.println("Character Literal");
                            totalCharacterLiterals++; //increase and later this is used to check if total characters are above 1

                        } else { //do like normal
                            System.out.println("Continuing Checking For Decimals");

                            decimalsInNumberState++; //increase if period/decimal exists
                            try { //try to see if decimals while in number state is greater than 1
                                if (decimalsInNumberState > 1) {
                                    throw new Exception(); //throw exception if true
                                }
                                // else { } //continue as normal
                            } catch (Exception notANumber) { //caught exception
                                lineNumber++;
                                System.out.println(notANumber + ": Not an Integer or Float on line: " + lineNumber); //display exception that was caught
                                System.out.println("Exit 0");
                                System.exit(0); //exit
                            }
                            //if above uncommented
                            emptyState = "Number"; //it will be for a number if no exception found
                            System.out.print("Number State");
                            System.out.println();
                        }

                    }
                    else if (letter == '{') { //trigger comment state
                        System.out.print("comment state");
                        emptyState = "Comment";
                        nestedComments++;
                        try {
                            if (nestedComments > 1) {
                                throw new Exception();
                            }

                        } catch (Exception SyntaxErrorException) {
                            lineNumber++; //line where syntax curly brace error will be
                            System.out.println(SyntaxErrorException + " left curly brace has been nested " + " line " + lineNumber);
                            System.out.println("Exiting 9");
                            System.exit(0);
                        }

                    }
                    else if (letter == '}') { //trigger comment state
                        emptyState = "Comment";
                        nestedComments--;
                        try { //throw exception is left brace is less than 0
                            if (nestedComments < 0) {
                                throw new Exception();
                            }

                        } catch (Exception SyntaxErrorException) {
                            lineNumber++; //used to show where syntax line error is
                            System.out.println(SyntaxErrorException + " no left curly brace present before right curly brace" + " line " + lineNumber);
                            System.out.println("Exiting");
                            System.exit(0);
                        }
                        System.out.println("ending comment state");
                        beforeResetState = 0;
                    }
                    else if (letter == halfsinglequote) { //start as halfquote for charliteralstate
                        System.out.println("CHARLITERALSTATE");
                        emptyState = "CharLiteralState";
                        System.out.println("CHARACTER LITERAL");
                        System.out.println();
                        totalSinglequotes++;
                        System.out.println("Single Quotes: ");
                        System.out.println(totalSinglequotes);
                        System.out.println();

                    }
                    else if (letter == halfDoubleQuote) { //start as doublequote for stringliteralstate
                        System.out.println("STRINGLITERALSTATE");
                        emptyState = "StringLiteralState";
                        System.out.println("STRING LITERAL");
                        System.out.println();
                        totalDoubleQuotes++;
                        System.out.println("Double Quotes: ");
                        System.out.println(totalDoubleQuotes);
                        System.out.println();

                    }


             /*   else{ //if current is neither blank
                    emptyState = "Start"; //stay in start state
                    System.out.println("start state");
                }*/

                } else if (emptyState.equals("Number"))  //if current state is Number
                {
                    if (Character.isAlphabetic(letter)) //if the current value in text is a Alphabet letter
                    {
                        emptyState = "Word"; //change state to word

                    } else if (Character.isDigit(letter))  //check if current character is a letter
                    {
                        emptyState = "Number"; //remain in state

                    } else if (letter == '.')
                    {
                        decimalsInNumberState++; //increase value
                        try { //try to see if decimals while in number state is greater than 1
                            if (decimalsInNumberState > 1) {
                                throw new Exception(); //throw exception if true
                            }
                            // else { } //continue as normal
                        } catch (Exception notAIntOrFloat) { //caught exception
                            lineNumber++; //line where the error displays
                            System.out.println(notAIntOrFloat + ": Not an Integer or Float."); //display exception that was caught
                            System.out.println("Exit 1");
                            System.exit(0); //exit
                        }

                    } else
                    {
                        emptyState = "Start"; //return state back to start

                    }
                }
                else if (emptyState.equals("Word")) { //current state is word still, it will remain until blank or line ends

                    if (Character.isDigit(letter)) {
                        //  System.out.println("Stay as word");
                        //    emptyState = "Word";//do nothing it will remain word if number

                    } else if (Character.isAlphabetic(letter)) {
                        //    emptyState = "Word";//do nothing it will remain word


                    }
                    /*Symbols to use with word state occurence 2. This should ensure Token will hashmap key find >= <= etc. for symbols*/
                    else if ((letter == '>') || (letter == '<') || (letter == '=') || (letter == ':')
                            || (letter == '!') || (letter == '&') || (letter == '(') || (letter == ')')
                            || (letter == '^') || (letter == '*') || (letter == '/') || (letter == '%')
                            || (letter == '+') || (letter == '-') || (letter == '|') || (letter == '?')) {
                        emptyState = "Word";
                        System.out.println("go to word state");
                    } else {
                        emptyState = "Start"; //if blank space return to start state

                        System.out.println("start state"); //display start state
                    }
                }
                else if (emptyState.equals("Comment")) {
                    if (letter == '{') {
                        nestedComments++; //increase
                    } else if (letter == '}') {
                        nestedComments--; //decrease
                    } else {
                        //no nothing otherwise
                        System.out.println("still in comment state");
                        System.out.println();
                    }

                    try { // for catch comments being nested on left
                        if (nestedComments > 1) {
                            throw new Exception();
                        }

                    } catch (Exception SyntaxErrorException) {
                        lineNumber++; //used for line the syntax error is caght
                        System.out.println(SyntaxErrorException + " left curly brace are nested" + " line " + lineNumber);
                        System.exit(0);
                    }

                    try {
                        if (nestedComments < 0) {
                            throw new Exception();
                        }

                    } catch (Exception SyntaxErrorException) {
                        lineNumber++; //for line where syntax error is caught
                        System.out.println(SyntaxErrorException + " right curly braces nested or started before left curly brace" + " line " + lineNumber);
                        System.out.println("exit 15");
                        System.exit(0);
                    }

                }
                else if (emptyState.equals("CharLiteralState")) {

                    //in char literal state currently

                    if (Character.isAlphabetic(letter)) {
                        totalCharacterLiterals++;
                    } else if (Character.isDigit(letter)) {
                        totalCharacterLiterals++;
                    } else if (letter == '.') {
                        totalCharacterLiterals++;
                    }

                    //else


                }
                else if (emptyState.equals("StringLiteralState")) { //make this check if single quote exists in String literal state


                    //otherwise check below
                    //   if (Character.isAlphabetic(letter)){
                    //     emptyState = "Word";
                    //    }
                    //   else if(Character.isDigit(letter)){
                    //     emptyState = "Number";
                    //    }
                    //    else if(letter == '.' ){
                    //      emptyState = "Word";
                    //    }
                    //    else if(letter == halfDoubleQuote){
                    //       emptyState = "StringLiteralState";
                    // }


                    //in string literal state currently

                    //else
                }


                //     else{
                //         emptyState = "Start"; //if blankspace return to
                //     }


                if (emptyState.equals("Number")) {

                    //  System.out.println("number state"); //number state still
                    String aDigit;
                    aDigit = "" + letter; //adds char letter to be a string for aDigit


                    //   System.out.println(token2); //it will display Number(number value in output)
                    System.out.println();
                    String e = String.valueOf(rebuildWord.append(aDigit)); //append a digit

                    System.out.println("building: " + e); //display building current digits to append e = rebuildWord.append(aDigit)
                    if (k + 1 == line.length())  //make appending stop when it k + 1 = end of line
                    {

                        try { //try to see if decimals while in what was appended when line ends is only "."
                            if (e.equals(".")) {
                                throw new Exception(); //throw exception if true
                            }
                            // else { } //continue as normal
                        } catch (Exception missingNumber) { //caught exception
                            lineNumber++; //increase line number if successful token on end of line
                            System.out.println(missingNumber + ": Not an Integer or Float. line " + lineNumber); //display exception that was caught
                            System.out.println("Exit 2");
                            System.exit(0); //exit
                        }


                        try { //try to see if decimals while in number state > 1 when end of line is reached
                            if (decimalsInNumberState > 1) {
                                throw new Exception(); //throw exception if true
                            }
                            // else { } //continue as normal
                        } catch (Exception notDecimalDigit) { //caught exception
                            lineNumber++; //increase line number if successful token on end of line
                            System.out.println(notDecimalDigit + ": Not an Integer or Float." + " lineNumber on: " + lineNumber); //display exception that was caught
                            System.out.println("Exit 3");
                            System.exit(0); //exit
                        }


                        System.out.println("building end: " + e); //display what has been finished appended
                        Token token3 = new Token(e, Token.types.Number, Token.types.EndOfLine); //this will be used so letters appended will become a token type Number()

                        /**add token to array**/
                        arrayOfTokens.add(token3);


                        token3.toString(); //call to string from token class
                        System.out.println(token3); //it will display Word with words from the build end

                        System.out.println(token3 + " Number token on end of line: " + lineNumber); //display current token again with line number

                        decimalsInNumberState = 0; //set to 0 since end of line reached
                        rebuildWord.setLength(0); //set stringbuilder back to 0

                        System.out.println();
                    }
                    beforeResetState = 1; //keep track of number state 2


                }
                else if (emptyState.equals("CharLiteralState")) {

                    /*try to see if true and catch the Exception*/
                    try { //try to see if character is more than one exist in this state
                        if (totalCharacterLiterals > 1) {
                            throw new Exception(); //throw exception if true
                        }
                        // else { } //continue as normal

                    } catch (Exception SyntaxErrorException) { //caught exception
                        lineNumber++; //for displaying where the line error is
                        System.out.println(SyntaxErrorException + ": More than 1 character in character literal state on line " + lineNumber); //display exception that was caught
                        System.out.println("Exit 25");
                        System.exit(0); //exit
                    }


                    //identifier word state
                    // System.out.println("word state"); //word state still
                    String aWord;

                    aWord = "" + letter; //adds char letter to be a string for aWord

                    //Token token2 = new Token(aWord, types.Word);
                    // token2.toString(); //wip building single
                    // System.out.println(token2); //it will display Word(letters in output)

                    System.out.println();
                    // String e = String.valueOf(rebuildWord.append(token2));
                    String wordAppend = String.valueOf(rebuildWord.append(aWord));


                    System.out.println("building: " + wordAppend); //build current letters to append
                    if (k + 1 > line.length() - 1)  //make appending stop when line length exceeded
                    {

                        try { //try to if there is only halfsingle quote "."
                            if (totalSinglequotes != 2) {
                                throw new Exception(); //throw exception if true
                            }
                            // else { } //continue as normal
                        } catch (Exception SyntaxErrorException) { //caught exception
                            lineNumber++; //for displaying where line error is
                            System.out.println(SyntaxErrorException + ": Not an Integer or Float. Line " + lineNumber); //display exception that was caught
                            System.out.println("Exit 33");
                            System.exit(0); //exit
                        }
                        lineNumber++; //increase line number if successful token on end of line

                        System.out.println("building end: " + wordAppend); //display what has been appended

                        Token token2 = new Token(wordAppend, Token.types.CHARACTERLITERAL, Token.types.EndOfLine); //this will be used so letters appended will become a token of word()

                        /**add to array**/
                        arrayOfTokens.add(token2);

                        token2.toString(); //call to string from token class
                        System.out.println(token2); //it will display type Word(token)
                        System.out.println(token2 + "Character Literal Token Ended On Line: " + lineNumber); //display again with line number

                        rebuildWord.setLength(0);
                        System.out.println();
                        totalCharacterLiterals = 0;
                        totalSinglequotes = 0;
                        totalDoubleQuotes = 0;


                    }
                    //else
                    emptyState = "Start"; //to prevent  CharacterLiteral('' "")
                    beforeResetState = 9001; //keep track of char literal state


                }
                else if (emptyState.equals("StringLiteralState")) {//like character literal above but no need for totalstringliteralamountchecking

/*
                    try to see if true and catch the Exception
                    (since it's StringLiteral State here
                    no need to do this)

                    try { //try to see if character is more than one exist in this state
                        if(totalCharacterLiterals > 1)
                        {
                            throw new Exception(); //throw exception if true
                        }
                        // else { } //continue as normal

                    }
                    catch (Exception SyntaxErrorException){ //caught exception
                        System.out.println(SyntaxErrorException + ": More than 1 character in character literal state " + lineOn); //display exception that was caught
                        System.out.println("Exit 25b unused");
                        System.exit(0); //exit
                    }


*/


                    //identifier word state
                    // System.out.println("word state"); //word state still


                    String aWord;

                    aWord = "" + letter; //adds char letter to be a string for aWord

                    //Token token2 = new Token(aWord, types.Word);
                    // token2.toString(); //wip building single
                    // System.out.println(token2); //it will display Word(letters in output)

                    System.out.println();
                    // String e = String.valueOf(rebuildWord.append(token2));
                    String wordAppend = String.valueOf(rebuildWord.append(aWord));


                    System.out.println("building: " + wordAppend); //build current letters to append
                    if (k + 1 > line.length() - 1)  //make appending stop when line length exceeded
                    {
                        lineNumber++; //increase line number if successful token on end of line

                        System.out.println("building end: " + wordAppend); //display what has been appended

                        Token token2 = new Token(wordAppend, Token.types.STRINGLITERAL, Token.types.EndOfLine); //this will be used so letters appended will become a token of word()
                        /** add token to array**/
                        arrayOfTokens.add(token2);

                        token2.toString(); //call to string from token class

                        System.out.println(token2); //it will display type Word(token)
                        System.out.println(token2 + "String Literal Token Ended On Line: " + lineNumber); //display again with line number

                        rebuildWord.setLength(0);
                        System.out.println();
                        totalCharacterLiterals = 0;
                        totalSinglequotes = 0;
                        totalDoubleQuotes = 0;


                    }
                    //else
                    emptyState = "Start"; //this is here to try to prevent string literal from doing  StringLiteral(""     a)
                    beforeResetState = 9002; //keep track of string literal state


                }

                else if (emptyState.equals("Word")) {  //identifier word state

                    totalCharacterLiterals++;
                    // System.out.println("word state"); //word state still


                    String aWord;

                    aWord = "" + letter; //adds char letter to be a string for aWord

                    //Token token2 = new Token(aWord, types.Word);
                    // token2.toString(); //wip building single
                    // System.out.println(token2); //it will display Word(letters in output)

                    System.out.println();
                    // String e = String.valueOf(rebuildWord.append(token2));
                    String wordAppend = String.valueOf(rebuildWord.append(aWord));

                    System.out.println("building: " + wordAppend); //build current letters to append
                    if (k + 1 > line.length() - 1)  //make appending stop when line length exceeded
                    {

                        System.out.println("building end: " + wordAppend); //display what has been appended

//for that if statement use a try and catch for if reserved word on end of file catch because it shouldn't be
                        if (knownWords.containsKey(wordAppend)) { /*if knownsWords of hashmap containsKey (wordAppend)
                           where wordAppend is equal to the key value of the knownWords
                           make a token to get the hashmapkey
                           */
                            lineNumber++; //increase line number if successful token on end of line

                            knownWords.get(wordAppend);
                            Token tokenIsReservedWord = new Token(knownWords.get(wordAppend));

                            Token identifierWord = new Token(Token.types.IDENTIFIER);


                            //make an end of line and identifier token and print that

                            Token tokenEndOfLineASSIGNM2 = new Token(Token.types.EndOfLine);
                            System.out.println(identifierWord + "(" + tokenIsReservedWord + ")" + tokenEndOfLineASSIGNM2);

                            /** add tokens to array**/
                            arrayOfTokens.add(identifierWord);
                            arrayOfTokens.add(tokenIsReservedWord);
                            arrayOfTokens.add(tokenEndOfLineASSIGNM2);

                            System.out.println(identifierWord + "(" + tokenIsReservedWord + ")" + tokenEndOfLineASSIGNM2 + " Identifier Token at the end of line: " + lineNumber); //display again with line number

                            System.out.println();
                            System.out.println();
                            System.out.println();
                         /*   if(i + 1 > lines.size() -1 ){

                            System.out.println("Vanish");
                            System.out.print("STATEMENT INCOMPLETE");
                            System.out.println();
                            } */

                            rebuildWord.setLength(0); //set length to 0 when done

                        } else {
                            //else function like normal with word
                            lineNumber++; //increase line count
                            Token token2 = new Token(wordAppend, Token.types.WORD, Token.types.EndOfLine); //this will be used so letters appended will become a token of word()
                            /**add token to array**/
                            arrayOfTokens.add(token2);


                            token2.toString(); //call to string from token class
                            System.out.println(token2); //it will display type Word(token)
                            System.out.println(token2 + "word token at the end of line: " + lineNumber); //display word token again with line number
                            rebuildWord.setLength(0);
                            System.out.println();

                        }
                    }
                    beforeResetState = 2; //keep track of word state 2

                }

/*
 in comment state
 */
                else if (emptyState.equals("Comment")) {
                    // System.out.println("comment state"); //comment state still

                    System.out.println(); //need to fix
                /*        try{

                            if(i + 1 > lines.size() -1 )
                             {
                                throw new Exception();
                             }

                           }
                        catch(Exception commentNeverClosed){


                        System.out.print(commentNeverClosed + " comment never closed at file end");
                        System.out.println();
                        System.out.println(" exit 10");
                        System.exit(0);


                    }*/
                    if (letter != '}') { //if not closed
                        beforeResetState = 3; //forcing state to be comment
                        System.out.println("comments not closed yet");

                    } else {
                        System.out.println("closing comment");
                        System.out.println();
                        beforeResetState = 0; //set state back to initial
                        //force state to start anyway
                        emptyState = "Start";

                    }


//do nothing otherwise

                }
                else if (beforeResetState == 9001) //to get charliteral token from last when blank space is hit on current state for one of the lines
                {

                    try { //try to if there is only halfsingle quote "."
                        if (totalSinglequotes != 2) {
                            throw new Exception(); //throw exception if true
                        }
                        // else { } //continue as normal
                    } catch (Exception SyntaxErrorException) { //caught exception
                        lineNumber++; //for displaying where the error line is
                        System.out.println(SyntaxErrorException + ": missing closing quote " + lineNumber); //display exception that was caught
                        System.out.println("Exit 34");
                        System.exit(0); //exit
                    }

                    // System.out.println("word state"); //word state still
                    String aWord;

                    aWord = ""; //the token would look like Word(2e ) if it was aWord ="" letter

                    //Token token2 = new Token(aWord, types.Word);
                    // token2.toString(); //wip building single
                    // System.out.println(token2); //it will display Word(letters in output)

                    System.out.println();
                    // String e = String.valueOf(rebuildWord.append(token2));
                    String wordAppend = String.valueOf(rebuildWord.append(aWord));

                    System.out.println("build previous state: " + wordAppend); //build current letters to append


                    //for that if statement use a try and catch for if reserved word on end of file catch because it shouldn't be
                    if (knownWords.containsKey(wordAppend)) { /*if knownsWords of hashmap containsKey (wordAppend)
                           where wordAppend is equal to the key value of the knownWords
                           make a token to get the hashmapkey
                           */
                        knownWords.get(wordAppend);
                        Token tokenIsReservedWord = new Token(knownWords.get(wordAppend));

                        Token identifierWord = new Token(Token.types.IDENTIFIER);

                        //make an end of line and identifier token and print that

                        // Token tokenEndOfLineASSIGNM2 = new Token(types.EndOfLine);
                        System.out.println(identifierWord + "(" + tokenIsReservedWord + ")");
                        /**add tokens to array**/
                        arrayOfTokens.add(identifierWord);
                        arrayOfTokens.add(tokenIsReservedWord);

                        System.out.println();
                        System.out.println("size line " + lines.size()); //seeing how many lines exist in the file
                        System.out.println();


                        System.out.println("line on " + lineNumber);//seeing how many lines file has
                        System.out.println();

                         /* ignore  if(i + 1 > lines.size() -1 ){
                            System.out.println("Vanish");
                            System.out.print("STATEMENT INCOMPLETE");
                            System.out.println();
                            } */
                        //remove the hashkey find and key the do like normal and because it's a characterliteral state it shouldn't reach
                        totalCharacterLiterals = 0;
                        totalSinglequotes = 0;
                        totalDoubleQuotes = 0;


                        rebuildWord.setLength(0); //set length to 0 when done
                    } else { //do like normal

                        Token previousWord = new Token(Token.types.CHARACTERLITERAL);
                        //old //   Token token4 = new Token(wordAppend, types.WORD, types.EndOfLine); //this will be used so letters appended will become a token type Word()

                        /**add token to array**/
                        arrayOfTokens.add(previousWord);

                        System.out.println(previousWord + "(" + wordAppend + ")"); //it will display Word with words from the build end
                        rebuildWord.setLength(0); //set string building back to 0
                        beforeResetState = 0; //set it back to initial state
                        System.out.println();
                        System.out.println("Return To Start State");
                        System.out.println();
                        emptyState = ("Start");
                        totalCharacterLiterals = 0;
                        totalSinglequotes = 0;
                        totalDoubleQuotes = 0;

                    }

                } else if (beforeResetState == 9002) //to get stringliteral token from last when blank space is hit on current state for one of the lines
                {

                    // System.out.println("word state"); //word state still
                    String aWord;

                    aWord = ""; //the token would look like Word(2e ) if it was aWord ="" letter

                    //Token token2 = new Token(aWord, types.Word);
                    // token2.toString(); //wip building single
                    // System.out.println(token2); //it will display Word(letters in output)

                    System.out.println();
                    // String e = String.valueOf(rebuildWord.append(token2));
                    String wordAppend = String.valueOf(rebuildWord.append(aWord));

                    System.out.println("build previous state: " + wordAppend); //build current letters to append


                    //for that if statement use a try and catch for if reserved word on end of file catch because it shouldn't be
                    if (knownWords.containsKey(wordAppend)) { /*if knownsWords of hashmap containsKey (wordAppend)
                           where wordAppend is equal to the key value of the knownWords
                           make a token to get the hashmapkey
                           */
                        knownWords.get(wordAppend);
                        Token tokenIsReservedWord = new Token(knownWords.get(wordAppend));

                        Token identifierWord = new Token(Token.types.IDENTIFIER);

                        /**add tokens to list*/
                        arrayOfTokens.add(identifierWord);
                        arrayOfTokens.add(tokenIsReservedWord);


                        //make an end of line and identifier token and print that

                        // Token tokenEndOfLineASSIGNM2 = new Token(types.EndOfLine);
                        System.out.println(identifierWord + "(" + tokenIsReservedWord + ")");

                        System.out.println();
                        System.out.println("size line " + lines.size()); //seeing how many lines exist in the file
                        System.out.println();


                        //System.out.println("line on " + lineNumber);//seeing how many lines file has //ignore  no endofline here

                        System.out.println();

                         /* ignore  if(i + 1 > lines.size() -1 ){
                            System.out.println("Vanish");
                            System.out.print("STATEMENT INCOMPLETE");
                            System.out.println();
                            } */
//remove this find hashkey and keep the do normal since it's a string literal

                        beforeResetState = 0; //set it back to initial state
                        System.out.println();
                        System.out.println("Return To Start State");
                        System.out.println();
                        emptyState = "Start";
                        totalCharacterLiterals = 0;
                        totalSinglequotes = 0;
                        totalDoubleQuotes = 0;


                        rebuildWord.setLength(0); //set length to 0 when done
                    } else { //do like normal

                        Token previousWord = new Token(Token.types.STRINGLITERAL);
                        //old //   Token token4 = new Token(wordAppend, types.WORD, types.EndOfLine); //this will be used so letters appended will become a token type Word()

                        /**add token to list**/
                        arrayOfTokens.add(previousWord);

                        System.out.println(previousWord + "(" + wordAppend + ")"); //it will display Word with words from the build end
                        rebuildWord.setLength(0); //set string building back to 0
                        beforeResetState = 0; //set it back to initial state
                        System.out.println();
                        System.out.println("Return To Start State");
                        System.out.println();
                        emptyState = "Start";
                        totalCharacterLiterals = 0;
                        totalSinglequotes = 0;
                        totalDoubleQuotes = 0;

                    }

                } else if (beforeResetState == 2) //to get word token from last when blank space is hit on current state for one of the lines
                {

                    // System.out.println("word state"); //word state still
                    String aWord;

                    aWord = ""; //the token would look like Word(2e ) if it was aWord ="" letter

                    //Token token2 = new Token(aWord, types.Word);
                    // token2.toString(); //wip building single
                    // System.out.println(token2); //it will display Word(letters in output)

                    System.out.println();
                    // String e = String.valueOf(rebuildWord.append(token2));
                    String wordAppend = String.valueOf(rebuildWord.append(aWord));

                    System.out.println("build previous state: " + wordAppend); //build current letters to append


                    //for that if statement use a try and catch for if reserved word on end of file catch because it shouldn't be
                    if (knownWords.containsKey(wordAppend)) { /*if knownsWords of hashmap containsKey (wordAppend)
                           where wordAppend is equal to the key value of the knownWords
                           make a token to get the hashmapkey
                           */
                        knownWords.get(wordAppend);
                        Token tokenIsReservedWord = new Token(knownWords.get(wordAppend));

                        Token identifierWord = new Token(Token.types.IDENTIFIER);

                        /**add tokens to list**/
                        arrayOfTokens.add(identifierWord);
                        arrayOfTokens.add(tokenIsReservedWord);

                        //make an end of line and identifier token and print that

                        // Token tokenEndOfLineASSIGNM2 = new Token(types.EndOfLine);
                        System.out.println(identifierWord + "(" + tokenIsReservedWord + ")");

                        System.out.println();
                        System.out.println("size line " + lines.size()); //seeing how many lines exist in the file
                        System.out.println();


                        System.out.println();

                         /* ignore  if(i + 1 > lines.size() -1 ){
                            System.out.println("Vanish");
                            System.out.print("STATEMENT INCOMPLETE");
                            System.out.println();
                            } */

                        rebuildWord.setLength(0); //set length to 0 when done
                    } else { //do like normal

                        Token previousWord = new Token(Token.types.WORD);
                        //old //   Token token4 = new Token(wordAppend, types.WORD, types.EndOfLine); //this will be used so letters appended will become a token type Word()

                        /** add tokens to list **/
                        arrayOfTokens.add(previousWord);


                        System.out.println(previousWord + "(" + wordAppend + ")"); //it will display Word with words from the build end
                        rebuildWord.setLength(0); //set string building back to 0
                        beforeResetState = 0; //set it back to initial state
                        emptyState = "Start";
                        System.out.println();
                        System.out.println("Return To Start State");
                        System.out.println();
                    }

                } else if (beforeResetState == 1) //to get number token from last state when blank space is hit on current state for one of the lines
                {


                    // System.out.println("word state"); //word state still
                    String aDigit;

                    aDigit = ""; //the token would look like Number(23 ) if it was aWord ="" letter

                    //Token token2 = new Token(aWord, types.Word);
                    // token2.toString(); //wip building single
                    // System.out.println(token2); //it will display Word(letters in output)


                    System.out.println();
                    // String e = String.valueOf(rebuildWord.append(token2));
                    String numberAppend = String.valueOf(rebuildWord.append(aDigit));

                    try { //try to see if previous only had "."
                        if (numberAppend.equals(".")) {
                            throw new Exception(); //throw exception if true
                        }
                        // else { } //continue as normal
                    } catch (Exception notDecimalDigit) { //caught exception
                        lineNumber++; //for displaying where the line the error is on
                        System.out.println(notDecimalDigit + ": Not an Integer or Float. on line: " + lineNumber); //display exception that was caught
                        System.out.println("Exit 5");
                        System.exit(0); //exit
                    }

                    System.out.println("building previous state: " + numberAppend); //build current letters to append

                    Token token4 = new Token(numberAppend, Token.types.Number); //this will be used so letters appended will become a token type Word()
                    //  token4.toString(); //call to string from token class

                    /**add token to list**/
                    arrayOfTokens.add(token4);

                    System.out.println(token4 + "(" + numberAppend + ")"); //it will display Word with words from the build end
                    rebuildWord.setLength(0); //set string building back to 0
                    beforeResetState = 0; //set it back to initial state
                    decimalsInNumberState = 0; //
                    System.out.println();
                    System.out.println("Return To Start State");
                    System.out.println();
                }

                System.out.println("Current List of Tokens in array list" + arrayOfTokens);
                k++;

            }


            i++;

        }

        System.out.println("Final List of Tokens in array list" + arrayOfTokens);

        return arrayOfTokens; //return array list of added tokens if everything was read


    }

    private HashMap<String, Token.types> createKnownWordsMap() {
        /*key value store String is the key and enum Types is the value*/
        HashMap<String, Token.types> knownWords = new HashMap<String, Token.types>(); //types is the enum types variable in token
        /*looking up key and value
         * String "while", types is from Token*/


        System.out.println("Initial ArrayList size " + arrayOfTokens);
        System.out.println();

        knownWords.put("define", Token.types.DEFINE);
        knownWords.put("constant", Token.types.CONSTANT);

        /*for control loops*/
        knownWords.put("while", Token.types.WHILE);
        knownWords.put("do", Token.types.DO);
        int thisWhileOrDoEND = 0; //grow until all blocks
        knownWords.put("for", Token.types.FOR);
        int thisForEnd = 0;  //grow until all blocks

        /*for if statements*/
        knownWords.put("if", Token.types.IF);
        knownWords.put("elseif", Token.types.ELSIF); //else if
        knownWords.put("else", Token.types.ELSE);

        /*for switch cases*/
        knownWords.put("switch", Token.types.SWITCH);//for switch case
        knownWords.put("case", Token.types.CASE); //for switch case

        knownWords.put("then", Token.types.THEN); //place after the boolean expression of an if or if else before block statement
        knownWords.put("from", Token.types.FROM); //type limit start
        knownWords.put("to", Token.types.TO); // type limit end
        knownWords.put("repeatuntil", Token.types.REPEATUNTIL);  //use for stopping a loop


        knownWords.put("arrayof", Token.types.ARRAYOF); //array
        knownWords.put("start", Token.types.START); //array function find start array
        knownWords.put("end", Token.types.END); //array function find end of array

        knownWords.put(":=", Token.types.ASSIGNVARIABLE); // for assign to a variable like a := 0

        knownWords.put("character", Token.types.CHARACTER); //for char single character

        knownWords.put("integer", Token.types.INTEGER); //for 32-bit signed number

        knownWords.put("real", Token.types.REAL);//floating point type

        knownWords.put("string", Token.types.STRING); //for multicharacter

        knownWords.put("boolean", Token.types.BOOLEAN); //for true and false

        knownWords.put("var", Token.types.VAR);
        /*The var keyword must be used before each variable declaration that is alterable.
        for functions and to
         */

        knownWords.put("variables", Token.types.DATATYPE); //keyword to define variables
        knownWords.put(":", Token.types.DATATYPE); //to use after list of variables and after ":" the name data datatype will be given

        /*IO functions*/
        knownWords.put("read", Token.types.READ);//reading value
        knownWords.put("write", Token.types.WRITE);//writing value

        /*String Functions*/
        knownWords.put("left", Token.types.LEFTSOMESTRING); //for getting first length characters
        knownWords.put("right", Token.types.RIGHTSOMESTRING); //for getting last length characters
        knownWords.put("substring", Token.types.SUBSTRING); //for getting substring from index of




        /*for boolean compares*/
        knownWords.put("<>", Token.types.NOTEQUAL); //should be higher than and and or
        knownWords.put("&&", Token.types.AND); //should be higher than or
        knownWords.put("||", Token.types.OR);
        knownWords.put("!", Token.types.NOT);
        /*compares*/
        knownWords.put("null", Token.types.NULL);
        knownWords.put("=", Token.types.EQUAL);
        knownWords.put("<", Token.types.LESSTHAN);
        knownWords.put(">", Token.types.GREATERTHAN);
        knownWords.put("<=", Token.types.LESSTHANOREQUAL);
        knownWords.put(">=", Token.types.GREATERTHANOREQUAL);

        knownWords.put("ident", Token.types.INDENT);//block start
        int identcount = 0; //count how many to make ident
        knownWords.put("dedent", Token.types.DEDENT); //block end
        int dedent = 0; //count how many align to ident to call dedent

        /*make higher priority than exponent*/
        knownWords.put("(", Token.types.LEFTPARENTHESIS);
        knownWords.put(")", Token.types.RIGHTPARENTHESIS);


        knownWords.put("^", Token.types.EXPONENT); //need to set priority higher than multiply and divide
        // Multiply and Divide should be higher than Add and Subtract
        //   highest where left most is higher than right to it
        knownWords.put("*", Token.types.MULTIPLY);
        knownWords.put("/", Token.types.DIVIDE);
        knownWords.put("%", Token.types.MOD); //modulo

                /* add and subtract
                higher priority is when is left
                 */
        knownWords.put("+", Token.types.ADD);
        knownWords.put("-", Token.types.SUBTRACT);

        knownWords.put("SQRT", Token.types.SQUAREROOT);

        knownWords.put("^", Token.types.EXPONENT); //need to set priority higher than multiply and divide
        // Multiply and Divide should be higher than Add and Subtract
        //   highest where left most is higher than right to it
        knownWords.put("*", Token.types.MULTIPLY);
        knownWords.put("/", Token.types.DIVIDE);
        knownWords.put("%", Token.types.MOD); //modulo

                /* add and subtract
                higher priority is when is left
                 */
        knownWords.put("+", Token.types.ADD);
        knownWords.put("-", Token.types.SUBTRACT);

        knownWords.put("SQRT", Token.types.SQUAREROOT);

        return knownWords;
    }


}


