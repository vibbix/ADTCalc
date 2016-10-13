package edu.wit.comp2000.StackCalculator;

import java.nio.charset.Charset;
import java.util.EmptyStackException;
import java.util.StringTokenizer;
enum Operators{
    OpenParentheses,
    ClosedParentheses,
    Exponent,
    Multiplication,
    Division,
    Addition,
    Subtraction,
    EOL
}

class Operation{
        private int Number1;
        private Operation Node1;
        private int Number2;
        private Operation Node2;
        private Operators Operator;
        //region Constructors
        public Operation(){
            Number1 = 0;
            Number2 = 0;
            Operator = Operators.Addition;
            Node1 = null;
            Node2 = null;
        }
        //endregion
        //region get/set accessors & mutators
        public int getNumber1() {
            return Number1;
        }

        public void setNumber1(int number1) {
            Number1 = number1;
        }

        public Operation getNode1() {
            return Node1;
        }

        public void setNode1(Operation node1) {
            Node1 = node1;
        }

        public int getNumber2() {
            return Number2;
        }

        public void setNumber2(int number2) {
            Number2 = number2;
        }

        public Operation getNode2() {
            return Node2;
        }

        public void setNode2(Operation node2) {
            Node2 = node2;
        }

        public Operators getOperator() {
            return Operator;
        }

        public void setOperator(Operators operator) {
            Operator = operator;
        }
        //endregion
}
/**
 * Created by beznosm on 10/12/2016.
 */
public class InfixCalculator implements Calculator {
    public final String OPERATIONS = "()-+/*";
    public final String NUMBERS = "0123456789";
    @Override
    public int EvaluateExpression(String expression) {
        expression = "("+expression.trim().replace(" ", "") +")";
        boolean isvalid = isValidExpression(expression);
        VectorStack<String> expr = new VectorStack<>(expression.length());
        StringTokenizer st = new StringTokenizer(expression, OPERATIONS, true);
        String currentnumber = "";
        while(st.hasMoreTokens()){
            String token = st.nextToken();
            //System.out.println(token);
            if(token.matches("[0-9]+")){
                currentnumber += token;
                continue;
            }
            if (token.matches("^[(-/]")){
                if(!currentnumber.equals("")){
                    expr.push(currentnumber);
                    currentnumber = "";
                }
                expr.push(token);
            }
            if(token.equals(")")){
                String subexpr = "";
                expr.pop();
                while(!expr.peek().equals("(")){
                    subexpr = expr.pop() + subexpr;
                }
                expr.pop(); //get's rid of '('
                expr.push(Integer.toString(EvaluateSubexpression(subexpr)));
            }
        }
        return Integer.parseInt(expr.pop());
    }

    /**
     * Evaluates a subexpression
     * @param Operation Operation to evaluate
     * @param num1 First number in subexpression
     * @param num2 Second number in subexpression
     * @throws IllegalArgumentException Thrown if the operator doesn't exist
     * @return The result of the subexpression
     */
    public static int EvaluateSubexpression(byte Operation, int num1, int num2){
        if(Operation == '-'){
            return num1-num2;
        }
        if(Operation == '+') {
            return num1 + num2;
        }
        if(Operation == '/'){
            return num1/num2;
        }
        if(Operation == '*'){
            return num1*num2;
        }
        throw new IllegalArgumentException("The operation '"+Operation+"' is not supported.");
    }

    /**
     * Evaluates a subexpression
     * @param Num1 First number in subexpression
     * @param Operation Operation to evaluate
     * @param Num2 Second number in subexpression
     * @throws IllegalArgumentException Thrown if the operator doesn't exist
     * @throws NumberFormatException Thrown if the one of the parameters is badly formatted
     * @return The result of the expression
     */
    public static int EvaluateSubexpression(String Num1, Character Operation, String Num2){

        return EvaluateSubexpression(Operation.toString().getBytes()[0], Integer.parseInt(Num1), Integer.parseInt(Num2));
    }
    public boolean isValidExpression(String expression){
        expression = expression.trim();
        if (!IsEquationBalanced(expression))
            throw new IllegalArgumentException("Expression is not balanced");
        if(expression.contains(".")){
            throw new IllegalArgumentException("Numbers with precision cannot be used");
        }
        String strippedexpr = expression;
        for(Byte replace: (OPERATIONS+NUMBERS).getBytes(Charset.defaultCharset())){
            //Character x = Character.highSurrogate(replace);
            strippedexpr = strippedexpr.replace(Character.toString((char)((int)replace)), "").trim();
        }
        if(strippedexpr.length() > 0){
            throw new IllegalArgumentException("The following characters are illegal:" + strippedexpr);
        }

        //minimum number of operations: (numbers - operations = 1)
        return true;
        //check for illegal chars
    }

    public int EvaluateSubexpression(String expression){
        expression = expression.replace(" ", "");
        int highestprecedence = 0;
        char searchfor = ' ';

        if(expression.contains("*") || expression.contains("/")){

            if(expression.contains("/") && (expression.indexOf('/') < expression.indexOf('*') ||
                    (expression.indexOf('/') > 0 && expression.indexOf('*') == -1))){
                highestprecedence = 3;
            }
            if(expression.contains("*") && ((expression.indexOf('*') < expression.indexOf('/')) ||
                    (expression.indexOf('*') > 0 && expression.indexOf('/') == -1))){
                highestprecedence=4;
            }
            if (highestprecedence == 3)
            {
                return EvaluateSubexpression(ReduceSubexpression(expression, "/".charAt(0)));
            }
            else if(highestprecedence == 4){
                return EvaluateSubexpression(ReduceSubexpression(expression, "*".charAt(0)));
            }
        }
        if(expression.contains("-") && (((expression.indexOf('-') < expression.indexOf('+')) ||
                (expression.indexOf('-') > 0 && expression.indexOf('+') == -1)))){
            highestprecedence=1;
        }
        if(expression.contains("+") && ((expression.indexOf('+') < expression.indexOf('-')) ||
                (expression.indexOf('+') > 0 && expression.indexOf('-') == -1))){
            highestprecedence = 2;
        }
        if(highestprecedence == 1){
            return EvaluateSubexpression(ReduceSubexpression(expression, "-".charAt(0)));
        }
        else if(highestprecedence == 2){
            return EvaluateSubexpression(ReduceSubexpression(expression, "+".charAt(0)));
        }
        return Integer.parseInt(expression);
    }

    private String ReduceSubexpression(String subexpression, Character operation){
        subexpression = subexpression.trim();
        int index = subexpression.indexOf(operation);
        int num1index = index;
        int num2index = index;
        String num1 = "";
        String num2 = "";
        num1index--;
        Character num1_current = subexpression.charAt(num1index);
        while(NUMBERS.contains(num1_current.toString())){
            num1 = num1_current + num1;
            num1index--;
            if(num1index == -1)
                break;
            num1_current = subexpression.charAt(num1index);
        }
        num2index++;
        Character num2_current = subexpression.charAt(num2index);
        while(NUMBERS.contains(num2_current.toString())){
            num2 = num2 + num2_current ;
            num2index++;
            if(num2index == subexpression.length())
                break;
            num2_current = subexpression.charAt(num2index);
        }
        return subexpression.replace(num1+operation+num2,
                Integer.toString(EvaluateSubexpression(num1, operation, num2)));
    }
}
