package Javathree11.src.main.java;

public class RealNode extends Node {
    private final float floatNodeValue; //derived from node class to hold integer values


    public RealNode(float floatNodeVal)//constructor with String param intNodeVal
    {
        floatNodeValue = floatNodeVal; //make it become a integer node parser
    }


    public float getFloatNodeValue() {//get integer value
        return floatNodeValue;  //return private integerNodeValue
    }

    @Override
    public String toString() {
        return "Real " + floatNodeValue;

    }
}