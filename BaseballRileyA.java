/*
NAME: Edward Riley
PROFESSOR: Peter Lutz
COURSE: Computation Problem Solving - Information Domain II (Java II)
DATE: 9/12/2018
*/

import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.text.*;
import java.util.Scanner;
import java.io.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.Arrays;


public class BaseballRileyA extends Application implements EventHandler<ActionEvent> {
   // Attributes are GUI components (buttons, text fields, etc.)
   // are declared here.
   private Stage stage;        // The entire window, including title bar and borders
   private Scene scene;        // Interior of window

  // Main just instantiates an instance of this GUI class
   public static void main(String[] args) {
      launch(args);
   }
   // Choose a pane ... Vbox & Gridpane used here
   private VBox root = new VBox(8);
   private FlowPane semiroot = new FlowPane();

   //GUI Components
 
   
   // Called automatically after launch sets up javaFX
   public void start(Stage _stage) throws Exception {
      stage = _stage;                        // save stage as an attribute
      stage.setTitle("GUI Starter");            // set the text in the title bar
      
      scene = new Scene(root, 300, 150);   // create scene of specified size (W X H)
      
                                             // with given layout
      stage.setScene(scene);                 // associate the scene with the stage
      stage.show();                          // display the stage (window)
   }
   
   public void handle(ActionEvent evt) 
   {
      Button gotClicked = (Button)evt.getSource();
   
      System.out.println( gotClicked.getText() + " Clicked!");
      switch (gotClicked.getText())
      {
         case "EXIT":
            System.exit(0);
            break;
      
         default:
            System.out.println("Button not detected");
            break;
      }      
   }
}

