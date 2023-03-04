package Javathree11;

public class IntegerNode extends Node {
    private final int intNodeValue;


    public IntegerNode(int intNodeVal)
    {
        intNodeValue = intNodeVal;
    }



    public int getIntegNodeVal() {//get integer value
        return intNodeValue;
    }

    @Override
    public String toString() {
        return "Integer " + intNodeValue;

    }
}