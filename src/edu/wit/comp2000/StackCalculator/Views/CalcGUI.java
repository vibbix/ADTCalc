package edu.wit.comp2000.StackCalculator.Views;/**
 * Created by dechristophera on 10/13/16.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CalcGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = null;

        try {
            root = FXMLLoader.load(getClass().getResource("CalcGUI.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(root != null) {
            Scene scene = new Scene(root, 359, 514);

            primaryStage.setTitle("Calculator");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }
}
