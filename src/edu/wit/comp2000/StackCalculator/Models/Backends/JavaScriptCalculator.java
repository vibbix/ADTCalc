package edu.wit.comp2000.StackCalculator.Models.Backends;

import edu.wit.comp2000.StackCalculator.Models.Calculator;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Created by beznosm on 10/12/2016.
 */
public class JavaScriptCalculator implements Calculator {
    ScriptEngineManager sem;
    private ScriptEngine engine;
    public JavaScriptCalculator(){
        sem = new ScriptEngineManager();
        engine = sem.getEngineByName("JavaScript");
    }
    @Override
    public int EvaluateExpression(String expression) {
        try{
            Object obj = engine.eval(expression);
            return (int)Math.floor( Double.parseDouble(obj.toString()));
        }
        catch(ScriptException screx){
            throw new IllegalArgumentException(screx);
        }
        catch (NullPointerException npe)
        {
            throw new IllegalArgumentException("Expression does not return a result");
        }
    }

    /**
     * Evaluates a complex expression
     * @param expression Expression to evaluate
     * @throws IllegalArgumentException Thrown if the expression is illegal
     * @throws NumberFormatException Throw if expression returns a non-double valie
     * @return The result of the expression
     */
    public double EvaluateComplexExpression(String expression){
        try{
            Object obj = engine.eval(expression);
            return Double.parseDouble(obj.toString());
        }
        catch(ScriptException screx){
            throw new IllegalArgumentException(screx);
        }
        catch (NullPointerException npe)
        {
            throw new IllegalArgumentException("Expression does not return a result");
        }
    }

    /**
     * Evaluates a line without getting the result;
     * @param expression Expression to evaluate
     * @throws IllegalArgumentException Thrown if expression is invalid
     */
    public Object EvaluateLine(String expression){
        try{
            return engine.eval(expression);
        }
        catch(ScriptException screx){
            throw new IllegalArgumentException(screx);
        }
    }
    /**
     * Resets the evaluation engine
     */
    public void Reset(){
        engine = sem.getEngineByName("JavaScript");
    }

}
