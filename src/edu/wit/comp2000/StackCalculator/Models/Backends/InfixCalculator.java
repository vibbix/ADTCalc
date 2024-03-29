package edu.wit.comp2000.StackCalculator.Models.Backends;

import edu.wit.comp2000.StackCalculator.Models.Calculator;
import edu.wit.comp2000.StackCalculator.Models.VectorStack;

import java.nio.charset.Charset;
import java.util.StringTokenizer;

/**
 * Created by beznosm on 10/12/2016.
 */
public class InfixCalculator implements Calculator {
    private final String OPERATIONS = "()-+/*";
    private final String NUMBERS = "0123456789";
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
    private static int EvaluateSubexpression(byte Operation, int num1, int num2){
        try{
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
        }catch(ArithmeticException ae){
            throw new IllegalArgumentException("Divide by zero error");
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
    private static int EvaluateSubexpression(String Num1, Character Operation, String Num2){

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
            throw new IllegalArgumentException("The following characters are illegal:" + strippedexpr.replaceAll("(.)(?=\\1)", ""));
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
