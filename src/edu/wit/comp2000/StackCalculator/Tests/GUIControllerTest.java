package edu.wit.comp2000.StackCalculator.Tests;

import com.sun.xml.internal.ws.api.FeatureListValidatorAnnotation;
import edu.wit.comp2000.StackCalculator.Controllers.CalcGUIController;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import org.junit.*;
import javax.swing.*;

/**
 * Created by beznosm on 10/16/2016.
 */
public class GUIControllerTest {
    private static CalcGUIController controller;
    private static CalcGUIController fallbackcontroller;
    @BeforeClass
    public static void runOnce(){
        controller = new CalcGUIController(false);
        fallbackcontroller = new CalcGUIController(true);
    }
    @After
    public void afterTest(){
        controller.clear();
        if(fallbackcontroller != null)
            fallbackcontroller.clear();
    }
    @Test
    public void EvaluateSimpleExpression(){
        EnterExpression("((9+9)-(4/2))", controller);
        controller.evaluate();
        Assert.assertEquals("SOLVED: 16", controller.getDisplay());
    }

    @Test
    public void EvaluateComplexExpression(){
        EnterExpression("((9+9)-(5/2))", controller);
        controller.evaluate();
        Assert.assertEquals("SOLVED: 16", controller.getDisplay());
    }
    @Test
    public void EvaluateSimpleFallbackExpression(){
        EnterExpression("((9.0+9.0)-(5/2))", fallbackcontroller);
        fallbackcontroller.evaluate();
        Assert.assertEquals("SOLVED: 15.5", fallbackcontroller.getDisplay());
    }
    @Test
    public void EvaluateComplexFallbackExpression(){
        EnterExpression("Math.cos(0)*((9.0+9.0)-(5/2))", fallbackcontroller);
        fallbackcontroller.evaluate();
        Assert.assertEquals("SOLVED: 15.5", fallbackcontroller.getDisplay());
    }
    @Test
    public void TestDelete(){
        EnterExpression("((9+9)-(5/2))", controller);
        controller.delete();
        Assert.assertEquals("((9+9)-(5/2)", controller.getDisplay());
    }
    @Test
    public void EvaluateBadComplexFallbackExpression(){
        EnterExpression("cos(0/0)*((9+9)-(5/2))", fallbackcontroller);
        fallbackcontroller.evaluate();
        Assert.assertEquals("ERROR: Invalid Expression!", fallbackcontroller.getDisplay());
    }
    @Test
    public void EvaluateBadComplexExpression(){
        EnterExpression("(0/0)*((9+9)-(5/2))", controller);
        controller.evaluate();
        Assert.assertEquals("ERROR: Invalid Expression!", controller.getDisplay());
    }
    @Test
    public void ClearAfterEvaluateTest(){
        EnterExpression("((9+9)-(4/2))", controller);
        controller.evaluate();
        Assert.assertEquals("SOLVED: 16", controller.getDisplay());
        EnterExpression("(", controller);
        Assert.assertEquals("(", controller.getDisplay());
    }

    private static ActionEvent GenerateEvent(String eventname){
        JButton b = new JButton(eventname);
        return new ActionEvent(b, null);
    }
    private static void EnterExpression(String expression, CalcGUIController gController){
        for(Character ch:expression.toCharArray()){
            gController.handleExpressionKeyPress(GenerateEvent(String.valueOf(ch)));
        }
    }


}
