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


public class ThreadExamGUI extends Application implements EventHandler<ActionEvent> {
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
   TextArea taOutput = new TextArea();
   private ArrayList<String> list = new ArrayList<String>();
   
   

   //GUI Components
 
   
   // Called automatically after launch sets up javaFX
   public void start(Stage _stage) throws Exception {
      stage = _stage;                        // save stage as an attribute
      stage.setTitle("GUI Starter");            // set the text in the title bar
      root.getChildren().addAll(taOutput);
      
      scene = new Scene(root, 300, 150);   // create scene of specified size (W X H)
      
      
      int count = 0;
      
      
                                             // with given layout
      stage.setScene(scene);                 // associate the scene with the stage
      stage.show();                          // display the stage (window)
      
      /*Thread t1 = new ThreadName("Thread 1");
      Thread t2 = new ThreadName("Thread 2");
      Thread t3 = new ThreadName("Thread 3");
      Thread t4 = new ThreadName("Thread 4");
      Thread t5 = new ThreadName("Thread 5");
      
      t1.start();
      t2.start();
     
      t3.start();
      //t3.yield();
      t4.start();
      //t4.yield();
      t5.start();
      //t5.yield();
          */
          
          
      Thread t1 = 
         new Thread() {
            public void run() { 
               doWork(); 
            }
         };
      Thread t2 = 
         new Thread() {
            public void run() { 
               doWork(); 
            }
         };
      Thread t3 = 
         new Thread() {
            public void run() { 
               doWork(); 
            }
         };
      Thread t4 = 
         new Thread() {
            public void run() { 
               doWork(); 
            }
         };
      Thread t5 = 
         new Thread() {
            public void run() { 
               doWork(); 
            }
         };
      
      
      t1.start();
      t2.start();
      t3.start();
      t4.start();
      t5.start();
            
   }
   
   public void doWork() { 
      
   
      for(int count = 20; count >= 0; count -= 2)
      {  
         String newName = "";
         
               //System.out.println(newName + " counter " + count);
         synchronized(list)
         {
              
               
            taOutput.appendText(newName + " counter " + count + "\n");
               
              
         } 
         
         try 
         {
            Thread.sleep(100);
         }
         catch(InterruptedException ie) {}
      } 
   
   
   }
   
   
   
   
   
   
   
   
   
   
   
   
   
   class ThreadName extends Thread {
   
      private String newName;
      
      
      // Pass _name to newName
      public ThreadName(String _name)
      {
         newName = _name;
      }
      // Run from thread
      public void run()
      {
         yield();
         //for(count = 20; count >= 0; count -=2)
         
         
         for(int count = 20; count >= 0; count -= 2)
         {  
         
               //System.out.println(newName + " counter " + count);
            synchronized("lock")
            {
               list.add(newName);
               
               taOutput.appendText(newName + " counter " + count + "\n");
               
              
            } 
         
            try 
            {
               Thread.sleep(100);
            }
            catch(InterruptedException ie) {}
         }  
         
      }
   
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


