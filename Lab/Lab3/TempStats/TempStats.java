/*
NAME: Edward Riley
PROFESSOR: Peter Lutz
COURSE: Computation Problem Solving - Information Domain II (Java II)
DATE: 9/17/2018
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


public class TempStats extends Application implements EventHandler<ActionEvent> {
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
   private FlowPane topRoot = new FlowPane(5, 5);
   private FlowPane bottomRoot = new FlowPane(5, 5);
   String fileName = "";
   File fileobj = null;
   FileInputStream fis = null;
   DataInputStream dis = null;
   double minimum = 0;
   double maximum = 0;
   double average = 0;
   int recNum = 0;

   //GUI Components
   Label laFileName = new Label("File Name");
   TextField tfFileName = new TextField("");
   Button buttonRead = new Button("Read");
   Button buttonAverage = new Button("Average");
   Button buttonMax = new Button("Max");
   Button buttonMin = new Button("Min");
   Button buttonExit = new Button("Exit");
   
   // Called automatically after launch sets up javaFX
   public void start(Stage _stage) throws Exception {
      stage = _stage;                        // save stage as an attribute
      stage.setTitle("Temperature Statistics");            // set the text in the title bar
      
      topRoot.setAlignment(Pos.CENTER);
      bottomRoot.setAlignment(Pos.CENTER);
   
      
      topRoot.getChildren().addAll(laFileName, tfFileName, buttonRead);
      bottomRoot.getChildren().addAll(buttonAverage, buttonMax, buttonMin, buttonExit);
      root.getChildren().addAll(topRoot, bottomRoot);
      
      
      scene = new Scene(root, 300, 75);   // create scene of specified size (W X H)
      
      buttonRead.setOnAction(this);
      buttonAverage.setOnAction(this);
      buttonMax.setOnAction(this);
      buttonMin.setOnAction(this);
      buttonExit.setOnAction(this);
   
                                             // with given layout
      stage.setScene(scene);                 // associate the scene with the stage
      stage.show();                          // display the stage (window)
   }
   File fileObj = new File("reset");
   public void handle(ActionEvent evt) 
   {
      Button gotClicked = (Button)evt.getSource();
   
      System.out.println( gotClicked.getText() + " Clicked!");
      switch (gotClicked.getText())
      {
         case "Read":
            try {      
               File fObj = new File(tfFileName.getText());
               FileInputStream fis = new FileInputStream(fObj);
               DataInputStream dis = new DataInputStream(fis);
            
               minimum = Integer.MAX_VALUE;
               maximum = Integer.MIN_VALUE;
            
               int total = 0;
               for (int recNum = 0; recNum < 366; recNum++)
                {
                  int temperature = dis.readInt();
               
                  if (temperature < minimum) 
                  {
                     minimum = temperature;
                  }
                  if (temperature > maximum) 
                  { 
                     maximum = temperature;
                  }
               
                  total += temperature;
               }
               average = (total / 366.0);
            
               dis.close();
            
               Alert alert = new Alert(Alert.AlertType.INFORMATION, "File successfully read!");
               alert.setHeaderText("Success!");
               alert.showAndWait();
            }
            catch (IOException ioe) {
               Alert alert = new Alert(Alert.AlertType.ERROR, "Exception: " + ioe + "\nRecord: " + recNum);
               alert.setHeaderText("IO Exception");
               alert.showAndWait();
            }
            break;
            
         case "Average": 
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText("Average of record high temperatures ");
            alert.setContentText(String.format("Average Temperature:  %.2f", average));
            alert.showAndWait();
            break;
            
         case "Min":
            Alert alertss = new Alert(AlertType.INFORMATION);
            alertss.setTitle("Message");
            alertss.setHeaderText("Average of record high temperatures");
            alertss.setContentText("Average Temperature: " + minimum);
            alertss.showAndWait();
            break;
            
         case "Max":
            Alert alertsss = new Alert(AlertType.INFORMATION);
            alertsss.setTitle("Message");
            alertsss.setHeaderText("Average of record high temperatures");
            alertsss.setContentText("Average Temperature: " + maximum);
            alertsss.showAndWait();
            break;
                  
         case "Exit":
            System.exit(0);
            break;
      
         default:
            System.out.println("Button not detected");
            break;
      }      
   }
}

