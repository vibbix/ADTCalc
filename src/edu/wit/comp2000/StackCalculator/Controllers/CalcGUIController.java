package edu.wit.comp2000.StackCalculator.Controllers;

import edu.wit.comp2000.StackCalculator.Models.Backends.InfixCalculator;
import edu.wit.comp2000.StackCalculator.Models.Backends.JavaScriptCalculator;
import edu.wit.comp2000.StackCalculator.Models.Calculator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.swing.*;

/**
 * Created by dechristophera on 10/13/16.
 */
public class CalcGUIController {
    @FXML
    private TextField display;
    private JTextField jdisplay;
    private boolean useJSBackendFallback = false;
    private Calculator jscalc;
    private Calculator ifCalc;
    private boolean resetAfterSolve = false;

    /**
     * Creates the controller for the Calculator GUI
     */
    public CalcGUIController(){this(false);}
    /**
     * Creates the controller for the Calculator GUI
     * @param UseJavaScriptBackendAsFallback Specifies if the program can use the JS backend as a fallback.
     */
    public CalcGUIController(boolean UseJavaScriptBackendAsFallback){
        ifCalc = new InfixCalculator();
        jdisplay = new JTextField();
        useJSBackendFallback = UseJavaScriptBackendAsFallback;
    }
    /**
     * Called when an operator or operand key is pressed to type it on screen
     * @param event info about calling object
     */
    public void handleExpressionKeyPress(ActionEvent event) {
        checkResetAfterSolve();
        String text = (event.getSource() instanceof Button?((Button)event.getSource()).getText():((JButton)event.getSource()).getText());
        System.out.println("Pressed \"" + text + "\"");
        setDisplay(getDisplay() + text);
    }

    /**
     * Solves the currently displayed expression
     */
    public void evaluate(){
        if(resetAfterSolve){
            return;
        }
        try {
            int solution = ifCalc.EvaluateExpression(getDisplay());
            System.out.println("SOLVING: " + getDisplay());
            setDisplay("SOLVED: " + solution);
            System.out.println("SOLVED: " + solution);
            resetAfterSolve = true;
        }catch(NumberFormatException nfe){
            System.out.println("NumberFormatException:" + nfe.toString());
            if(useJSBackendFallback){
                evaluateFallback();
            }else{
                setDisplay("ERROR: Invalid Expression!");
            }

        }catch(IllegalArgumentException iae){
            if(useJSBackendFallback){
                evaluateFallback();
            }else{
                setDisplay("ERROR: Invalid Expression!");
            }
        }
    }
    private void evaluateFallback(){
            if(jscalc == null)
                jscalc = new JavaScriptCalculator();
            try{
                double solution = ((JavaScriptCalculator)jscalc).EvaluateComplexExpression(getDisplay());
                System.out.println("SOLVING(using JSCalc): " + getDisplay());
                setDisplay("SOLVED: " + solution);
                System.out.println("SOLVED(using JSCalc): " + solution);
            }catch(Exception ex){
                setDisplay("ERROR: Invalid Expression!");
            }
    }

    /**
     * Clears the display of all characters
     */
    public void clear(){
        setDisplay("");
        System.out.println("Pressed CLEAR");
    }

    /**
     * Deletes most recently typed character
     */
    public void delete(){
        checkResetAfterSolve();
        if(getDisplay().length() >= 1) {
            setDisplay(getDisplay().substring(0, getDisplay().length() - 1));
        }
        System.out.println("Pressed DELETE");
    }

    /**
     * Quits the application
     */
    public void quit(){
        System.exit(0);
    }

    /**
     * Resets the display when a new key is pressed after an expression is solved
     */
    private void checkResetAfterSolve(){
        if(resetAfterSolve){
            setDisplay("");
            resetAfterSolve = false;
        }
    }

    /**
     * Returns text currently on display
     * @return Text currently on display
     */
    public String getDisplay(){

        return (display == null? jdisplay.getText():display.getText());
    }
    private void setDisplay(String text){
        try{
            display.setText(text);
        }
        catch(NullPointerException npe){
           jdisplay.setText(text);
        }
    }
}
