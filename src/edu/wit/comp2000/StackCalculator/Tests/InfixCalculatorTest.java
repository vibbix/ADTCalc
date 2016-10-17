package edu.wit.comp2000.StackCalculator.Tests;

import edu.wit.comp2000.StackCalculator.Models.Backends.InfixCalculator;
import edu.wit.comp2000.StackCalculator.Models.Calculator;
import edu.wit.comp2000.StackCalculator.Views.CalcGUI;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by beznosm on 10/12/2016.
 */
public class InfixCalculatorTest {
    private static Calculator calc;
    @BeforeClass
    public static void runOnce(){
        calc = new InfixCalculator();
    }
    @Test(expected = IllegalArgumentException.class)
    public void divideByZeroTest() throws Exception {
        String expr = "0/0";
        calc.EvaluateExpression(expr);
    }
    @Test
    public void evaluateValidExpression() throws Exception {
        String expr = "((5*5*2*2)-(2*5)-(5*2*(2+3+4)))";
        Assert.assertEquals(true, calc.IsEquationBalanced(expr));
    }
    @Test
    public void balanceInvalidExpression() throws Exception {
        String expr = "((5*5*2*2)-(2*5)-(5*2*(2+3+4))";
        Assert.assertEquals(false, calc.IsEquationBalanced(expr));
    }
    @Test
    public void balanceInvalidStackExpression() throws Exception {
        String expr = "((5*5*2*2))))-(2*5)-(5*2*(2+3+4))";
        Assert.assertEquals(false, calc.IsEquationBalanced(expr));
    }
    @Test
    public void balanceInvalidParenthesesExpression() throws Exception {
        String expr = "((5*5*2*2)-(2*5)-(5*2*(2+3+4))";
        Assert.assertEquals(false, calc.IsEquationBalanced(expr));
    }
    @Test
    public void evaluateExpression(){
        int result = calc.EvaluateExpression("((5*5*2*2)-(2*5)-(5*2*(2+3+4)))");
        Assert.assertEquals(0, result);
    }
    @Test
    public void evaluateValidSubexpression(){

        int result = ((InfixCalculator)calc).EvaluateSubexpression("5*8 + 1 - 9/3 + 1");
        Assert.assertEquals(39, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void UnbalancedExpressionTest(){
        String expr = "((5*5*2*2))))-(2*5)-(5*2*(2+3+4))";
        ((InfixCalculator)calc).isValidExpression(expr);
    }
    @Test(expected = IllegalArgumentException.class)
    public void IllegalCharactersTest(){
        String expr = "(5^2*5*2*2)-(2*5)-(5*2*(2+3+4))";
        ((InfixCalculator)calc).isValidExpression(expr);
    }
    @Test(expected = IllegalArgumentException.class)
    public void DecimalTest(){
        String expr = "(5.2*5*2*2)-(2*5)-(5*2*(2+3+4))";
        ((InfixCalculator)calc).isValidExpression(expr);
    }


}