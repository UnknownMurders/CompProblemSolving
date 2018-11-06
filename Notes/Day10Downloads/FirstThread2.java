import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;

/**
 * Simple thread ... update a time counter while the user
 * types in data to store in a text file
 * @author Pete Lutz
 * @version 10-23-2017
 */
 
 //This is second way to be able to run a thread.
public class FirstThread2 extends Application 
      implements EventHandler<ActionEvent>, Runnable {

   // Window attributes
   private Stage stage;
   private Scene scene;
   
   // GUI Components
   private TextField tfTimer = new TextField("00:00");
   private TextField tfCounter = new TextField();
   private Button btnInc = new Button("+");
   
   // Counter attribute
   private int count = 0;
   private int time = 0;
   
   /** main program */
   public static void main(String[] args) {
      launch(args);
   }
   
   /** constructor*/
   public void start(Stage _stage) {
      // Setup window
      stage = _stage;
      stage.setTitle("Counter");
      stage.setOnCloseRequest(new EventHandler<WindowEvent>() { public void handle(WindowEvent evt) { System.exit(0); } } );
      
      VBox root = new VBox(8);
      
      // Timer in Top
      FlowPane fpTop = new FlowPane(8,8);
      fpTop.setAlignment(Pos.CENTER_RIGHT);
      tfTimer.setPrefColumnCount(5);
      fpTop.getChildren().add(tfTimer);
      tfTimer.setEditable(false);
      root.getChildren().add(fpTop);
      
      // Button and counter in Bottom
      FlowPane fpBot = new FlowPane(8,8);
      fpBot.setAlignment(Pos.CENTER);
      fpBot.getChildren().add(tfCounter);
      tfCounter.setText("" + count);
      tfCounter.setEditable(false);
      fpBot.getChildren().add(btnInc);
      root.getChildren().add(fpBot);
      
      // Setup handler for button
      btnInc.setOnAction(this);
      
      // Set the scene and show the window
      scene = new Scene(root, 300, 75);
      stage.setScene(scene);
      stage.show();
      
      //First (failed) attempt at getting timer to work - it's never false so thereforth it never shows up
      /*while (true) 
      {
         try
         {
            Thread.currentThread().sleep(1000);
         }
         catch (Exception e)
         {
            System.out.println("Error: " + e);
         }
         time++;
         tfTimer.setText(String.format("%02d:%02d", time/60, time%60));
         
      
      }*/
      
      // Start a thread to update the timer
      Thread timerThread = new Thread(this);
      timerThread.start();
      // timerThread.run(); - Don't do this - it will only run the thread without updating.
      
      
   }
   
   /** Button handler */
   public void handle(ActionEvent ae) 
   {
      count++;
      tfCounter.setText("" + count);
   }
   
   //run method for Runnable interface
    public void run()
   {
      int time = 0;  // Count seconds
      
      while (true) 
      
         try 
         {
            Thread.currentThread().sleep(1000); // wait for 1 second
         // Update the timer
            time++;
            tfTimer.setText(String.format("%02d:%02d", (time / 60), (time % 60)));
         }
         catch(InterruptedException ie) 
         {
         
         }
         
        
   }
}