package edu.wit.comp2000.StackCalculator;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Created by beznosm on 10/12/2016.
 */
public class JavaScriptCalculator implements Calculator{
    private ScriptEngine engine;
    public JavaScriptCalculator(){
        ScriptEngineManager sem = new ScriptEngineManager();
        engine = sem.getEngineByName("JavaScript");
    }
    @Override
    public int EvaluateExpression(String expression) {
        try{
            Object obj = engine.eval(expression);
            int result = Integer.parseInt(obj.toString());
            return result;
        }
        catch(ScriptException screx){
            throw new IllegalArgumentException(screx);
        }

    }

}
