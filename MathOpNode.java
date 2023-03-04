package Javathree11;



public class MathOpNode extends Node { //derived from node class


    /* maybe used method "evaluate()" and you return an output based on your operands maybe*/

    public enum MathOperation {Multiply, Divide, Add, Subtract, Modulo}  //enum values for the class

    private MathOperation mathOperation; //Multiply Divide Add Subtract
    private Node operandLeft, operandRight; //Nodes for left and right

    //constructor and parameters
    public MathOpNode(MathOperation operationM, Node leftN, Node rightN) {
        mathOperation = operationM;
        operandLeft = leftN;
        operandRight = rightN;
    }

    public String getMathOperate()
    {

       String gotMathOperate = mathOperation.toString();
        return gotMathOperate;
    }

    public Node getOperandLeft()
    {
        return this.operandLeft;
    }

    public Node getOperandRight()
    {
        return this.operandRight;
    }


    @Override
    public String toString() {
        return "(" + operandLeft + "" + mathOperation + "" + operandRight + ")";
    }


        public void Multiply(Node operandLeft, Node operandRight) {
            //getIntegNodeVal(operandLeft,operandRight);
    }


        public int Divide(Node operandLeft, Node operandRight) {
     /*  IntegerNode nodeInteger = new IntegerNode(int intNodeVal);
        //nodeInteger.getIntegNodeVal(intNodeVal);
        int nodeVal = nodeInteger.getIntegNodeVal();
            return intNodeVal; */
            return 0;
        }



        public void Add (Node operandLeft, Node operandRight) {
        }



        public void Subtract(Node operandLeft, Node operandRight) {
    }
}



