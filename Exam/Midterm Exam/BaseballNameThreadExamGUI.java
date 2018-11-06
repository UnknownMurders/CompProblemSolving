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


public class BaseballNameThreadExamGUI extends Application implements EventHandler<ActionEvent> {
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
   private GridPane semiroot = new GridPane();
   private TextArea taOutput = new TextArea();

   private String firstName = "";
   private String lastName = "";
   private int dayBorn = 0;
   private int monthBorn = 0;
   private int yearBorn = 0;
   private int pounds = 0;
   private double height = 0.0;
   private String finalName = "";
   private double tallestPlayer = 0;
   private double shortestPlayer = 0;
   private double heaviestPlayer = 0.0;
   private String finalName1 = "";
   private String finalName2 = "";
   private String finalName3 = "";
   private int  earlyYear = 0;
   private int earlyMonth = 0;
   private int earlyDay = 0;

   //GUI Components
 
   
   // Called automatically after launch sets up javaFX
   public void start(Stage _stage) throws Exception {
      stage = _stage;                        // save stage as an attribute
      stage.setTitle("GUI Starter");            // set the text in the title bar
      
      root.getChildren().addAll(taOutput); 
     
      scene = new Scene(root, 300, 150);   // create scene of specified size (W X H)
      try {
      
         FileInputStream fis = new FileInputStream("BaseballNames.bin");
         DataInputStream dis = new DataInputStream(fis);
         while(dis.available() > 0) {
         
            firstName = dis.readUTF();
            lastName = dis.readUTF();
            dayBorn = dis.readInt();
            monthBorn = dis.readInt();
            yearBorn = dis.readInt();
            pounds = dis.readInt();
            height = dis.readDouble();
            finalName = dis.readUTF();
            
            
            if (height < shortestPlayer)
            {
               finalName2 = firstName + " " + lastName;
               shortestPlayer = height;
            
            }
            
            if(height > tallestPlayer) {
            
               finalName = firstName + " " + lastName;
               tallestPlayer = height;
            }
            
            if (earlyYear > yearBorn)
            {
               if (earlyMonth > monthBorn)
               {
                  if (earlyDay > dayBorn)
                  {
                     earlyYear = yearBorn;
                     earlyMonth = monthBorn;
                     earlyDay = dayBorn;
                     finalName3 = firstName + " " + lastName;
                  
                  }
               }
            }
            
            if (pounds > heaviestPlayer) {
               
               finalName1 = firstName + " " + lastName;
               heaviestPlayer = pounds;
            }
            dis.close();
         
         }         
         
      }
      catch(Exception e) {
      
      }
      
      
      System.out.println("Tallest player was: " + finalName + " at " + tallestPlayer + " inches.");
      System.out.println("Heaviest player was: " + finalName1 + " at " + (int)heaviestPlayer + " pounds.");
      taOutput.appendText("Tallest player was: " + finalName + " at " + tallestPlayer + " inches.\n");
      taOutput.appendText("Heaviest player was: " + finalName1 + " at " + (int)heaviestPlayer + " pounds.\n");
   
      
   
   
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

