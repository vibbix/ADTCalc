package edu.wit.comp2000.StackCalculator;

import java.nio.charset.Charset;
import java.util.EmptyStackException;

/**
 * Created by beznosm on 10/12/2016.
 */
public interface Calculator {

    /**
     * Evaluates an expression and returns it's result
     * @param expression The expression to evaluate
     * @return The result of the expression
     * @throws IllegalArgumentException If the expression is badly formatted
     */
    int EvaluateExpression(String expression);

    /**
     * Checks if the equation is balanced.
     * @param expression The expression to check
     * @return True is the equation is balanced
     */
    default boolean IsEquationBalanced(String expression) {
        VectorStack<Character> balanced = new VectorStack<>();
        for (Byte operator: expression.getBytes(Charset.defaultCharset())) {
            if (operator == '('){
                balanced.push('(');
            }else if(operator == ')')
            {
                try{
                    if (balanced.peek() == '('){
                        balanced.pop();
                    }
                }catch(EmptyStackException ese){
                    return false;
                }
            }
        }
        return balanced.isEmpty();
    }
}
