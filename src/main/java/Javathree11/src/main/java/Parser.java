package Javathree11.src.main.java;

import java.util.List;

public class Parser {
    List<Token> tokenlist;

    public Parser(List<Token> tokens) {
        tokenlist = tokens;
    }


    public Node parse() {
        while (tokenlist != null) {
            Node expressVarName = expression(); //call expression method
            Token expectENDLineToken = expectEndsOfLine(); //call expect end of line method

            if (expressVarName == null || expectENDLineToken == null) {
                break;
            } //kill loop if null

        }
        //return Node;
        return null;
    }

    private Node expression() { //expression is for add subtract
        Node LeftNode2 = term(); //expression calls term //LRNode2 is for add or subtract, LRNode is for multiply, divide or mod

        while (peek(0) == new Token(Token.types.ADD) || peek(0) == new Token(Token.types.SUBTRACT)) {
            if (matchAndremove(new Token(Token.types.ADD)) != null) {
                Node RightNode2 = term(); //call term again
                Token marAddSub = matchAndremove(new Token("0", Token.types.ADD, Token.types.EndOfLine)); //make a * token
                MathOpNode addNode = new MathOpNode(MathOpNode.MathOperation.Add, LeftNode2, RightNode2); //multiply the left and right Node

                return addNode; //return multiply node
            } else if (matchAndremove(new Token(Token.types.SUBTRACT)) != null) {
                Node RightNode2 = term(); //call term again
                Token marAddSub = matchAndremove(new Token("0", Token.types.SUBTRACT, Token.types.EndOfLine)); //make a * token
                MathOpNode subtractNode = new MathOpNode(MathOpNode.MathOperation.Subtract, LeftNode2, RightNode2); //multiply the left and right Node

                return subtractNode; //return multiply node
            }


        }

        return null;
    }

    private Node term() { //term is for times divide modulo * / %
        Node LeftNode = factor();

        while (peek(0) == new Token(Token.types.MULTIPLY) || peek(0) == new Token(Token.types.DIVIDE)) {
            if (matchAndremove(new Token(Token.types.DIVIDE)) != null) {
                Node RightNode = factor();

                //  Token marMulDiv = matchAndremove(new Token("0",Token.types.DIVIDE, Token.types.EndOfLine)); //make a "/" token
                MathOpNode divideNode = new MathOpNode(MathOpNode.MathOperation.Divide, LeftNode, RightNode); //divide the left and right node


                return divideNode; //return the divided node

                //    IntegerNode instanceIntNode = new IntegerNode(int intNodeVal);
                //    instanceIntNode.getIntegNodeVal();
            } else if (matchAndremove(new Token(Token.types.MULTIPLY)) != null) {
                Node RightNode = factor();

                Token marMulDiv = matchAndremove(new Token("0", Token.types.MULTIPLY, Token.types.EndOfLine)); //make a * token
                MathOpNode multiplyNode = new MathOpNode(MathOpNode.MathOperation.Multiply, LeftNode, RightNode); //multiply the left and right Node
                //  String retrieveValue2 = marMulDiv.getStringValue();
                //  System.out.println("VALUE: " +retrieveValue2);
                return multiplyNode; //return multiply node

                //    IntegerNode instanceIntNode = new IntegerNode(int intNodeVal);
                //    instanceIntNode.getIntegNodeVal();
            }

        }

        return null;
    }

    private Node factor() { //should it be String factor? //factor is just numbers and expressions
        if (peek(0).getAToken() == Token.types.Number) {
            Token getaNumber = matchAndremove(new Token("0", Token.types.Number, Token.types.EndOfLine));
            String retrieveValue = getaNumber.getStringValue();
            if (retrieveValue.contains(".")) { //if the token contains a dot
                float realValue = Float.parseFloat(retrieveValue); //it will converted to be a float/real node returned
                RealNode floatNode = new RealNode(realValue); //make a realNode instead to get float value
                return floatNode; //return
            } else {
                //return retrieveValue;
                int intValue = Integer.parseInt(retrieveValue);
                IntegerNode integerVALUE = new IntegerNode(intValue);

                ///  integerVALUE.getIntegNodeVal();
                return integerVALUE;
            }

        }
        return null;
    }


    private Token matchAndremove(Token token) { //helper function 1
        if (tokenlist.get(0).getAToken() == token.getAToken()) {

            Token tempToken = tokenlist.get(0);
            tokenlist.remove(token);
            return tempToken;
        } else {
            return null;
        }


    }

    private Token expectEndsOfLine() { //helper function 2
        if (matchAndremove(new Token(Token.types.EndOfLine)) == null) {
            return null; //make exception for later since it's void
        }
        return new Token(Token.types.EndOfLine);

        /* above if to return new will change to this I guess if need be
        try {
            if (matchAndremove(new Token(Token.types.EndOfLine)) == null) {
                throw new Exception(); //throw exception if true
            }
        }
        // else { } //continue as normal
        catch (Exception UsedToBeNullHere) { //caught exception
            System.out.println(UsedToBeNullHere + ": Whatever bye"); //display exception that was caught
            System.out.println("Exit Leave");

            System.exit(0); //exit
        }
        return new Token(Token.types.EndOfLine);
    } */
    }

    private Token peek(int peekInteger) {

        if (tokenlist.get(peekInteger) != null) {


            Token foundintToken = tokenlist.get(peekInteger);
            return foundintToken;
        } else {
            return null;
        }

    }


}
