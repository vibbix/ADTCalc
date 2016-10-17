package edu.wit.comp2000.StackCalculator.Tests;
import edu.wit.comp2000.StackCalculator.Models.Backends.JavaScriptCalculator;
import edu.wit.comp2000.StackCalculator.Models.Calculator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by beznosm on 10/16/2016.
 */
public class JavascriptCalculatorTest {
    private static Calculator jscalc;
    @BeforeClass
    public static void runOnceBeforeClass() {
        jscalc = new JavaScriptCalculator();
    }
    @Test
    public void EvaluateSimpleExpression(){
        int result = jscalc.EvaluateExpression("5+5");
        Assert.assertEquals(10, result);
    }
    @Test
    public void EvaluateComplexExpression(){
        double result = ((JavaScriptCalculator)jscalc).EvaluateComplexExpression("(9/3)+((Math.cos(0)* Math.cos(3.1415/3)) + 2.1)");
        Assert.assertEquals(5.6, result, .001);
    }
    @Test
    public void TestBalancedEquation(){
        Assert.assertEquals(true, jscalc.IsEquationBalanced("((9/9))"));
    }
    @Test
    public void TestUnbalancedEquation(){
        Assert.assertEquals(false, jscalc.IsEquationBalanced("((9/9)))"));
    }
    @Test(expected = IllegalArgumentException.class)
    public void EvaluateIllegalSimpleExpression(){
        int test = jscalc.EvaluateExpression("((9/9)");
    }
    @Test(expected = IllegalArgumentException.class)
    public void EvaluateIllegalComplexExpression(){
        double test = ((JavaScriptCalculator)jscalc).EvaluateComplexExpression("(cos(0.5)*(9/9)");
    }
    @Test
    public void EvaluateValidExpression(){
        Calculator calc = new JavaScriptCalculator();
        int result = calc.EvaluateExpression("5*8 + 1 - 9/3 + 1");
        Assert.assertEquals(39, result);
    }
    @Test(expected = IllegalArgumentException.class)
    public void EvaluateExpressionBadParse(){
        int i =  jscalc.EvaluateExpression("var x = 1;");
    }
    @Test(expected = IllegalArgumentException.class)
    public void EvaluateComplexExpressionBadParse(){
        JavaScriptCalculator calc = ((JavaScriptCalculator)jscalc);
        double result = calc.EvaluateComplexExpression("var x = 1;");
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestEngineReset(){
        ((JavaScriptCalculator)jscalc).EvaluateLine("var x = 1.0;");
        int result = jscalc.EvaluateExpression("x*5");
        Assert.assertEquals(5, result);
        ((JavaScriptCalculator)jscalc).Reset();
        result = jscalc.EvaluateExpression("x*5");
    }
}
