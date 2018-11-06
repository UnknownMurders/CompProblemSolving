/*
NAME: Edward Riley
PROFESSOR: Peter Lutz
COURSE: Computation Problem Solving - Information Domain II (Java II)
DATE: 9/12/2018
*/


import javafx.application.*;
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


public class StopWatch extends Application implements EventHandler<ActionEvent> {
   // Attributes are GUI components (buttons, text fields, etc.)
   // are declared here.
   private Stage stage;        // The entire window, including title bar and borders
   private Scene scene;        // Interior of window
   // Choose a pane ... VBox used here
   private VBox root = new VBox(8);
   
   int counter = 0;
   
   // GUI Components
   private TextField tfWatch = new TextField();
   private Button btnStart = new Button("Start");
   private Button btnLap = new Button("Lap");
   private Button btnStop = new Button("Stop");
   private TextArea taLaps = new TextArea();
   
   //Timer for stopwatch
   private java.util.Timer timer = null;
   private long currentTime = 0L;
   
   // Main just instantiates an instance of this GUI class
   public static void main(String[] args) {
      launch(args);
   }
   
   // Called automatically after launch sets up javaFX
   public void start(Stage _stage) throws Exception {
      stage = _stage;                        // save stage as an attribute
      stage.setTitle("StopWatch - Riley");            // set the text in the title bar
   
      //Draw the GUI   
      FlowPane fpBottom = new FlowPane(8, 3);
      FlowPane fpTop = new FlowPane();
      FlowPane fpCenter = new FlowPane();
      fpBottom.setAlignment(Pos.CENTER);
      fpTop.getChildren().addAll(tfWatch);
      fpCenter.getChildren().addAll(taLaps);
      fpBottom.getChildren().addAll(btnLap, btnStart);
      
      
      btnLap.setVisible(false);
      
      //Change font for tfWatch
      // Font currentFont = tfWatch.getFont();
      tfWatch.setFont(
         Font.font("MONOSPACED", FontWeight.BOLD, 48));
      tfWatch.setText("00:00.0");
      
      //Adjusting the width of GUI components
      tfWatch.setPrefWidth(275);
      taLaps.setPrefWidth(275);
      fpBottom.setPrefWidth(275);
      
      //Preventing users to edit the textfield
      tfWatch.setEditable(false);
         
         //Centering GUI components
      tfWatch.setAlignment(Pos.CENTER);
      fpTop.setAlignment(Pos.CENTER);
      fpCenter.setAlignment(Pos.CENTER);
      fpBottom.setAlignment(Pos.BOTTOM_RIGHT);
         
      btnStart.setOnAction(this);
      btnLap.setOnAction(this);
   
   
      root.getChildren().addAll(fpTop, fpCenter, fpBottom);
      
      //Set the scene and show stage
      scene = new Scene(root, 300, 350);   // create scene of specified size 
                                             // with given layout
      stage.setScene(scene);                 // associate the scene with the stage
      stage.show();          
           
      
                // display the stage (window)
   }
   
   
   
   public void handle(ActionEvent evt) {
      // Get the button that was clicked
      Button btn = (Button)evt.getSource();
      System.out.println(evt.getSource());
      
      // Switch on its name
      switch(btn.getText()) 
      {
         case "Start": 
            doStart();
            btnLap.setVisible(true);
            break;
         case "Stop":
         doStop();
         btnLap.setVisible(false);
         break;
         
         case "Lap": 
         doLap();
         break;
         
         default:
         System.out.println("BUTTON NOT FOUND");
         break;
      
      }
      
   }  
   
   private void doLap()
   {
   String log = taLaps.getText();
   String time = tfWatch.getText();
   taLaps.clear();
   counter++;
   
   taLaps.setText(log + " LAP " + counter + " " + time + "\n"); 
   
   }
   
   private void doStart()
   {
      counter = 0;
      currentTime = 0L;
      timer = new java.util.Timer();
      timer.scheduleAtFixedRate(new MyTimerTask(), 0, 100);
      
      btnStart.setText("Stop");  
   }
   
   private void doStop()
   {
   timer.cancel();
   
   btnStart.setText("Start");
   }
   
    
    
    //MyTimerTask inner class
   class MyTimerTask extends java.util.TimerTask
   {
      public void run()
      {
         currentTime += 100;
         long seconds = currentTime / 1000 % 60;
         long minutes = seconds / 1000 / 60 % 60;
         long tenths = currentTime / 100 % 10;
         /*double dSeconds = ((double) currentTime) / 1000.0;
         long tenSeconds = (long) (dSeconds * 10);
         long tenths = tenSeconds % 10;
           */ 
         Platform.runLater(new Runnable() {
            public void run() { 
               tfWatch.setText(
                  String.format("%02d:%02d.%d", minutes, seconds, tenths));
             }
          } );
      }
    
    
    
   }
}	
