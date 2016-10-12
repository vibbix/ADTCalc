package edu.wit.comp2000.StackCalculator;

/**
 * Created by beznosm on 10/12/2016.
 */
public interface Calculator {

    /**
     * @param expression The expression to evaluate
     * @return The result of the expression
     * @throws IllegalArgumentException If the expression is badly formatted
     */
    int EvaluateExpression(String expression);

    /**
     * @param expression The expression to check
     * @return True is the equation is balanced
     */
    boolean IsEquationBalanced(String expression);
}
