import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import java.util.*;

/**
 *NAME: Edward Riley
 *TEMP PROFESSOR: Erik Golen
 *COURSE: Computation Problem Solving in Domain II
 *DATE: 9/28/2018
 */
public class Lab04Pt2 extends Application implements EventHandler<ActionEvent> {
   // Window attributes
   private Stage stage;
   private Scene scene;
   
   // GUI Controls
   private Button btnClickMe = new Button("Start");
   private Label lblProgress01 = new Label("Progress: ");
   private Label lblProgress02 = new Label("Progress: ");
   private Label lblProgress03 = new Label("Progress: ");
   private Label lblValue01 = new Label("0");
   private Label lblValue02 = new Label("0");
   private Label lblValue03 = new Label("0");
   private ProgressBar pbBar01 = new ProgressBar(0);
   private ProgressBar pbBar02 = new ProgressBar(0);
   private ProgressBar pbBar03 = new ProgressBar(0);
   
//    private ProgressBar pbBar = new ProgressBar(ProgressBar.INDETERMINATE_PROGRESS);
   
   // Timer for the ClickMe button
   java.util.Timer timer01 = null;
   java.util.Timer timer02 = null;
   java.util.Timer timer03 = null;

   
   
   //Main
   public static void main(String[] args) {
      launch(args);
   }
   
   public void start(Stage _stage) {
      // Setup window
      stage = _stage;
      stage.setTitle("Lab04Pt2");
      
      VBox root = new VBox(8);
      
      // Button in Top
      FlowPane fpTop = new FlowPane(8,8);
      fpTop.setAlignment(Pos.CENTER);
      fpTop.getChildren().add(btnClickMe);
      root.getChildren().add(fpTop);
      
      
      // Label and progress bar in south
      FlowPane fpMid1 = new FlowPane(8,8);
      fpMid1.setAlignment(Pos.CENTER);
      
      FlowPane fpMid2 = new FlowPane(8,8);
      fpMid2.setAlignment(Pos.CENTER);
      
      FlowPane fpMid3 = new FlowPane(8,8);
      fpMid3.setAlignment(Pos.CENTER);
   
      StackPane spBar01 = new StackPane();
      spBar01.getChildren().addAll(pbBar01, lblValue01);
      fpMid1.getChildren().addAll(lblProgress01, spBar01);
      
      StackPane spBar02 = new StackPane();
      spBar02.getChildren().addAll(pbBar02, lblValue02);
      fpMid2.getChildren().addAll(lblProgress02, spBar02);
      
      StackPane spBar03 = new StackPane();
      spBar03.getChildren().addAll(pbBar03, lblValue03);
      fpMid3.getChildren().addAll(lblProgress03, spBar03);
      
      root.getChildren().addAll(fpMid1, fpMid2, fpMid3);
      
      // ActionListener for button
      btnClickMe.setOnAction(this);
      //fail - pbBar.setIndeterminate(true);
   
      // Best efforts to kill threads if they are running when the windows is closing
      stage.setOnCloseRequest(
         new EventHandler<WindowEvent>() {
            public void handle(WindowEvent evt) {
               if(timer01 != null)
               {
                  timer01.cancel();
                  timer02.cancel();
                  timer03.cancel();
                  System.exit(0);
               }
               else if (timer02 != null)
               {
                  timer01.cancel();
                  timer02.cancel();
                  timer03.cancel();
                  System.exit(0);
               }
               else if (timer03 != null)
               {
                  timer01.cancel();
                  timer02.cancel();
                  timer03.cancel();
                  System.exit(0);
               }
            }
         } );
      
      // set scene and show window
      scene = new Scene(root, 175, 125);
      stage.setScene(scene);
      stage.show();
   }
   
   // Button handler (Click Me)
   public void handle(ActionEvent ae) {
      String label = ((Button)ae.getSource()).getText();
      
      switch(label) {
         case "Start":
            lblValue01.setText("0");
            lblValue02.setText("0");
            lblValue03.setText("0");
            pbBar01.setProgress(0);
            pbBar02.setProgress(0);
            pbBar03.setProgress(0);
         
            btnClickMe.setDisable(true);         
            
            
            // Create a timers
            timer01 = new java.util.Timer();
            timer02 = new java.util.Timer();
            timer03 = new java.util.Timer();
            
         
            
            TimerTask ttask01 = 
               new TimerTask() {
                  int value01 = 0;
                  
                  int seconds = (int)(Math.random() * 1000);
                  
                  public void run() 
                  {
                     try {Thread.sleep(seconds);} 
                     catch (Exception e) {e.toString();}
                     // Platform.runLater causes the inner run method to run on the JavaFX thread.
                     // This is necessary if the code touches the UI ... otherwise you might get a
                     // runtime error.
                     Platform.runLater(
                        new Runnable() 
                        {
                           public void run() 
                           {
                              pbBar01.setProgress(value01/100.0);    // display the value01
                              lblValue01.setText("" + value01);
                           
                              if(value01 == 100)
                              {         // if the value01 is 100 we are done
                              // Change back to Click Me and stop the timer01
                                 Platform.runLater(
                                    new Runnable() {
                                       public void run() 
                                       {
                                          System.out.println("Timer 1 end");
                                          timer01.cancel();
                                          timer02.cancel();
                                          timer03.cancel();
                                          btnClickMe.setDisable(false);         
                                       
                                       }
                                    } );
                                 
                              }
                              value01 += 4;   // Increment the value01 bu 10
                           }
                        } );
                  } //end run
                  
                  
               };//end timer01 task
         
            TimerTask ttask02 = 
               new TimerTask() {
                  int value02 = 0;
                  int seconds = (int)(Math.random() * 1000);
               
                  public void run() 
                  {
                     try {Thread.sleep(seconds);} 
                     catch (Exception e) {e.toString();}
                     // Platform.runLater causes the inner run method to run on the JavaFX thread.
                     // This is necessary if the code touches the UI ... otherwise you might get a
                     // runtime error.
                     Platform.runLater(
                        new Runnable() 
                        {
                           public void run() {
                              pbBar02.setProgress(value02/100.0);    // display the value01
                              lblValue02.setText("" + value02);
                              
                              
                              if(value02 == 100) 
                              {         // if the value01 is 100 we are done
                              // Change back to Click Me and stop the timer01
                                 Platform.runLater(
                                    new Runnable() {
                                       public void run() {
                                       
                                          //btnClickMe.setText("Start");
                                          System.out.println("Timer 2 end");
                                          timer01.cancel();
                                          timer02.cancel();
                                          timer03.cancel();
                                          btnClickMe.setDisable(false);         
                                       
                                       }
                                    } );
                              
                              }
                              
                              value02 += 4;   // Increment the value02 by 4
                           }
                        } );
                        
                  }//end run
                  
                  
               };//end timer02 task
         
         
            TimerTask ttask03 = 
               new TimerTask() {
                  int value03 = 0;
                  int seconds = (int)(Math.random() * 1000);
               
                  public void run() 
                  {
                     try {Thread.sleep(seconds);} 
                     catch (Exception e) {e.toString();}
                  
                     // Platform.runLater causes the inner run method to run on the JavaFX thread.
                     // This is necessary if the code touches the UI ... otherwise you might get a
                     // runtime error.
                     Platform.runLater(
                        new Runnable() 
                        {
                           public void run() {
                              pbBar03.setProgress(value03/100.0);    // display the value01
                              lblValue03.setText("" + value03);
                           
                              if(value03 == 100) {         // if the value01 is 100 we are done
                              // Change back to Click Me and stop the timer01
                                 Platform.runLater(
                                    new Runnable() {
                                       public void run() {
                                          btnClickMe.setDisable(false);
                                          btnClickMe.setText("Start");
                                          timer01.cancel();
                                          timer02.cancel();
                                          timer03.cancel();
                                          System.out.println("Timer 3 end");
                                          btnClickMe.setDisable(false);         
                                       
                                       
                                       }
                                    } );
                              
                              }
                              value03 += 2;            
                           }
                              
                        
                        
                        
                        } );
                  }//end run
                  
                  
               };//end timer03 task               
            // Start the timer03
            int timer01sec = (int)(Math.random() * 1000);
            int timer02sec = (int)(Math.random() * 1000);
            int timer03sec = (int)(Math.random() * 1000);
         
            timer01.scheduleAtFixedRate(ttask01, 0, timer01sec);
            timer02.scheduleAtFixedRate(ttask02, 0, timer02sec);
            timer03.scheduleAtFixedRate(ttask03, 0, timer03sec);
         
            break;
      }
   }
}