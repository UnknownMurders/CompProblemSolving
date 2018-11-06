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


public class MenuExercise extends Application {
   // Attributes are GUI components (buttons, text fields, etc.)
   // are declared here.
   private Stage stage;        // The entire window, including title bar and borders
   private Scene scene;        // Interior of window

  // Main just instantiates an instance of this GUI class
   public static void main(String[] args) {
      launch(args);
   }

   //GUI Components
   MenuBar menuBar = new MenuBar();
   Menu menuCount = new Menu("Count");
   MenuItem itemInc = new MenuItem("Inc");
   MenuItem itemDec = new MenuItem("Dec");
   MenuItem itemReset = new MenuItem("Reset");
   MenuItem itemExit = new MenuItem("Exit");
   TextArea taField = new TextArea();
   int counter = 0;
    // Choose a pane ... Vbox & Gridpane used here
   VBox root = new VBox();
   private FlowPane semiroot = new FlowPane();
 
   
   // Called automatically after launch sets up javaFX
   public void start(Stage _stage) throws Exception {
     
      taField.setPrefHeight(400);
      taField.setEditable(false);
      menuCount.getItems().addAll(itemInc, itemDec, itemReset, itemExit);
      
      menuBar.getMenus().addAll(menuCount);
   
      root.getChildren().addAll(menuBar, taField);
      
      EventHandler<ActionEvent> action = performActions();
      
      itemInc.setOnAction(action);
      itemDec.setOnAction(action);
      itemReset.setOnAction(action);
      itemExit.setOnAction(action);
   
     
      stage = _stage;                        // save stage as an attribute
      stage.setTitle("Menu Exercise");            // set the text in the title bar
      
      scene = new Scene(root, 300, 400);   // create scene of specified size (W X H)
      
                                             // with given layout
      stage.setScene(scene);                 // associate the scene with the stage
      stage.show();                          // display the stage (window)
   }
   
   public EventHandler<ActionEvent> performActions()  {
      return 
         new EventHandler<ActionEvent>() {
            public void handle(ActionEvent evt)
            {
               String label = ((MenuItem)evt.getSource()).getText();
            
               System.out.println( label + " Clicked!");
            
               switch (label)
               {
                  case "Exit":
                     System.exit(0);
                     break;
                  case "Inc":
                  counter++;
                  String counterString = String.valueOf(counter);
                  String log1 = taField.getText();
                  taField.setText(log1 + counterString + "\n");
                  break;
                  case "Dec":
                  counter--;
                  String counterString1 = String.valueOf(counter);
                  String log2 = taField.getText();
                  taField.setText(log2 + counterString1 + "\n" );
                  break;
                  case "Reset":
                  counter = 0;
                  String counterString2 = String.valueOf(counter);
                  taField.setText(counterString2);
                  break;
               
                  default:
                     System.out.println("Button/Menu Items not detected");
                     break;
               }      
            }
         };
   
   }

}
