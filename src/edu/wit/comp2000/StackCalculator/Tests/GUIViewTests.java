package edu.wit.comp2000.StackCalculator.Tests;

import edu.wit.comp2000.StackCalculator.Views.CalcGUI;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by beznosm on 10/16/2016.
 */
public class GUIViewTests {
    @Test
    public void StartCalcTest() throws InterruptedException{
        //Stage s = null;
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                new JFXPanel(); // Initializes the JavaFx Platform
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        CalcGUI cgui = new CalcGUI();
                        Stage s = new Stage();
                        cgui.start(s);
                        Assert.assertEquals("Calculator", s.getTitle());
                    }
                });
            }
        });
        thread.start();
        Thread.sleep(100);

    }
}
