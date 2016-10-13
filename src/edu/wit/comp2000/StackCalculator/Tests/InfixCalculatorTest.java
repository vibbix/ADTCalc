package edu.wit.comp2000.StackCalculator.Tests;

import edu.wit.comp2000.StackCalculator.Calculator;
import edu.wit.comp2000.StackCalculator.InfixCalculator;
import edu.wit.comp2000.StackCalculator.JavaScriptCalculator;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by beznosm on 10/12/2016.
 */
public class InfixCalculatorTest {
    @Test
    public void evaluateValidExpression() throws Exception {
        String expr = "((5*5*2*2)-(2*5)-(5*2*(2+3+4)))";
        Calculator calc = new InfixCalculator();
        Assert.assertEquals(true, calc.IsEquationBalanced(expr));
    }
    @Test
    public void balanceInvalidExpression() throws Exception {
        String expr = "((5*5*2*2)-(2*5)-(5*2*(2+3+4))";
        Calculator calc = new InfixCalculator();
        Assert.assertEquals(false, calc.IsEquationBalanced(expr));
    }
    @Test
    public void balanceInvalidStackExpression() throws Exception {
        String expr = "((5*5*2*2))))-(2*5)-(5*2*(2+3+4))";
        Calculator calc = new InfixCalculator();
        Assert.assertEquals(false, calc.IsEquationBalanced(expr));
    }
    @Test
    public void balanceInvalidParenthesesExpression() throws Exception {
        String expr = "((5*5*2*2)-(2*5)-(5*2*(2+3+4))";
        Calculator calc = new InfixCalculator();
        Assert.assertEquals(false, calc.IsEquationBalanced(expr));
    }
    @Test
    public void evaluateExpression(){
        Calculator calc = new InfixCalculator();
        int result = calc.EvaluateExpression("((5*5*2*2)-(2*5)-(5*2*(2+3+4)))");
        Assert.assertEquals(0, result);
    }
    @Test
    public void evaluateValidSubexpression(){
        InfixCalculator calc = new InfixCalculator();
        int result = calc.EvaluateSubexpression("5*8 + 1 - 9/3 + 1");
        Assert.assertEquals(39, result);
    }
    @Test
    public void evaluateValidExpressionJS(){
        Calculator calc = new JavaScriptCalculator();
        int result = calc.EvaluateExpression("5*8 + 1 - 9/3 + 1");
        Assert.assertEquals(39, result);
    }


}