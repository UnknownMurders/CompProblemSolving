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


public class CounterPt1 extends Application implements EventHandler<ActionEvent> {
   // Attributes are GUI components (buttons, text fields, etc.)
   // are declared here.
   private Stage stage;        // The entire window, including title bar and borders
   private Scene scene;        // Interior of window

  // Main just instantiates an instance of this GUI class
   public static void main(String[] args) {
      launch(args);
   }
   // Choose a pane ... VBox & Gridpane used here
   private VBox root = new VBox(8);
   private FlowPane fpTop = new FlowPane(5, 5);
   private FlowPane fpBottom = new FlowPane(10, 5);
   
   int counter = 0;
   
   //GUI Components
   Label labelValue = new Label("Current Value");
   TextField tfValue = new TextField("0");
   Button buttonPositive = new Button("+");
   Button buttonNegative = new Button("-");
   Button buttonReset = new Button("Reset");
   Button buttonQuit = new Button("Quit");

 
   
   // Called automatically after launch sets up javaFX
   public void start(Stage _stage) throws Exception {
      stage = _stage;                        // save stage as an attribute
      stage.setTitle("Riley - Counter");            // set the text in the title bar
      
      scene = new Scene(root, 325, 75);   // create scene of specified size (W X H) with given layout

      //Setting on action for buttons
      buttonPositive.setOnAction(this);
      buttonNegative.setOnAction(this);
      buttonReset.setOnAction(this);
      buttonQuit.setOnAction(this);

      //Reducing size of textfield
      tfValue.setPrefWidth(80);
         
      //Setting alignments
      fpTop.setAlignment(Pos.CENTER);
      fpBottom.setAlignment(Pos.CENTER);
      
      //Adding to layout
      fpTop.getChildren().addAll(labelValue, tfValue);
      fpBottom.getChildren().addAll(buttonPositive, buttonNegative, buttonReset, buttonQuit);
      root.getChildren().addAll(fpTop, fpBottom);
      
      stage.setScene(scene);                 // associate the scene with the stage
      stage.show();                          // display the stage (window)
   }
   
   public void handle(ActionEvent evt) 
   {
      Button gotClicked = (Button)evt.getSource();
   
      System.out.println( gotClicked.getText() + " Clicked!");
      switch (gotClicked.getText())
      {
         case "Reset":
         counter = 0;
         tfValue.setText("0");

         break;
         
         case "Quit":
            System.exit(0);
            break;
         case "+":
         counter++;
         String stringCounter = Integer.toString(counter);
         tfValue.setText(stringCounter);
         break;
         case "-":
         counter--;
         String stringsCounter = Integer.toString(counter);
         tfValue.setText(stringsCounter);
         break;
      
         default:
            System.out.println("Button not detected");
            break;
      }      
   }
}

