import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;

/**
 * Class to animate a circle of JLabels
 * @author Pete Lutz
 * @date 10-30-2017
 */
public class Animate1 extends Application implements EventHandler<ActionEvent> {
   // Window attributes
   Stage stage;
   Scene scene;
   
   // Other attributes
   public static final int NUM = 4;
   public static final String[] colors = { "#ff0000", "#0000ff", "#00ff00", "#ffff00"};
   private ButtonRunnable[] runnables = new ButtonRunnable[NUM];
   private String[] names = { "Grumpy", "Dopey", "Bashful", "Sleepy"};
   private Thread[] threads = new Thread[NUM];
   private int[][] locations = { {1, 0}, {2, 1}, {1, 2}, {0, 1} };
   private Button btnStartStop = new Button("Start");
   
   /** main program */
   public static void main(String[] args) {
      launch(args);
   }
   
   /** constructor */
   public void start(Stage _stage) {
      // set up window
      stage = _stage;
      stage.setTitle("Animate Buttons");
      GridPane root = new GridPane();
      
      // GUI setup
      GridPane.setHalignment(btnStartStop, HPos.CENTER);
      root.add(btnStartStop, 1, 1);
      btnStartStop.setOnAction(this);
      
      // Place Buttons in position with initial colors
      for(int i = 0; i < NUM; i++) {
         ButtonRunnable br = new ButtonRunnable(names[i], i);
         br.setPrefSize(100, 100);
         root.add(br, locations[i][0], locations[i][1]);
         runnables[i] = br;
      } 
      
      // Program dies when window closes
      stage.setOnCloseRequest(
         new EventHandler<WindowEvent>() {
            public void handle(WindowEvent evt) {
               System.exit(0);
            }
         } );           
   
      // Set the scene and show the window
      scene = new Scene(root, 300, 75);
      stage.setScene(scene);
      stage.show();
   }
   
   /** button handler */
   public void handle(ActionEvent ae) {
      String label = ((Button)ae.getSource()).getText();
      
      switch (label) 
      {
         case "Start":
            btnStartStop.setText("Stop");
            int counter = 0;
            // Start threads
           
               for(int i = 0; i < NUM; i++)
               {
                  threads[i] = new Thread(runnables[i]);
                  threads[i].start();
               }
        
            
            break;
         case "Stop":
            btnStartStop.setText("Start");
            
            // Kill the threads
            for(int i = 0; i < NUM; i++)
               runnables[i].kill();
            break;
      }
   }
}