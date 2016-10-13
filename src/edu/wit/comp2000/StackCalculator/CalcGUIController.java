package edu.wit.comp2000.StackCalculator;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import jdk.nashorn.internal.runtime.ParserException;

import javax.swing.text.html.parser.Parser;

/**
 * Created by dechristophera on 10/13/16.
 */
public class CalcGUIController {

    @FXML
    private TextField display;

    private JavaScriptCalculator calc = new JavaScriptCalculator();

    private boolean resetAfterSolve = false;

    /**
     * Called when an operator or operand key is pressed to type it on screen
     * @param event info about calling object
     */
    public void handleExpressionKeyPress(ActionEvent event) {
        checkResetAfterSolve();
        Button pressed = (Button)event.getSource();
        System.out.println("Pressed \"" + pressed.getText() + "\"");
        display.setText(display.getText() + pressed.getText());
    }

    /**
     * Solves the currently displayed expression
     */
    public void evaluate(){
        if(resetAfterSolve){
            return;
        }
        try {
            int solution = calc.EvaluateExpression(display.getText());
            System.out.println("SOLVING: " + display.getText());
            display.setText("SOLVED: " + solution);
            System.out.println("SOLVED: " + solution);
            resetAfterSolve = true;
        }catch(ParserException pex) {
            display.setText("ERROR: Invalid Expression!");
        }
    }

    /**
     * Clears the display of all characters
     */
    public void clear(){
        display.setText("");
        System.out.println("Pressed CLEAR");
    }

    /**
     * Deletes most recently typed character
     */
    public void delete(){
        display.setText(display.getText().substring(0, display.getText().length() - 1));
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
            display.setText("");
            resetAfterSolve = false;
        }
    }
}
