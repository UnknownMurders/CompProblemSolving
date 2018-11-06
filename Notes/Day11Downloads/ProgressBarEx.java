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
 * ProgressBarEx.java
 * Simple example of how ProgressBar works.
 * @author Pete Lutz
 * @version 10-24-2017
 */
public class ProgressBarEx extends Application implements EventHandler<ActionEvent> {
   // Window attributes
   private Stage stage;
   private Scene scene;
   
   // GUI Controls
   private Button btnClickMe = new Button("Click Me");
   private Label lblProgress = new Label("Progress: ");
    private Label lblValue = new Label("0");
  private ProgressBar pbBar = new ProgressBar(0);
//    private ProgressBar pbBar = new ProgressBar(ProgressBar.INDETERMINATE_PROGRESS);
   
   // Timer for the ClickMe button
   java.util.Timer timer = null;
   
   /** main */
   public static void main(String[] args) {
      launch(args);
   }
   
   /** constructor */
   public void start(Stage _stage) {
      // Setup window
      stage = _stage;
      stage.setTitle("ProgressBar Example");
      
      VBox root = new VBox(8);
      
      // Button in Top
      FlowPane fpTop = new FlowPane(8,8);
      fpTop.setAlignment(Pos.CENTER);
      fpTop.getChildren().add(btnClickMe);
      root.getChildren().add(fpTop);
      
      // Label and progress bar in south
      FlowPane fpBot = new FlowPane(8,8);
      fpBot.setAlignment(Pos.CENTER);
//      fpBot.getChildren().addAll(lblProgress, pbBar);

       StackPane spBar = new StackPane();
       spBar.getChildren().addAll(pbBar, lblValue);
       fpBot.getChildren().addAll(lblProgress, spBar);
      
      root.getChildren().add(fpBot);
      
      // ActionListener for button
      btnClickMe.setOnAction(this);
//       pbBar.setIndeterminate(true);

      // Catch window closing
      stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
         public void handle(WindowEvent evt) {
            if(timer != null) timer.cancel();
            System.exit(0);
         }
      } );
      
      // set scene and show window
      scene = new Scene(root, 300, 100);
      stage.setScene(scene);
      stage.show();
   }
   
   // Button handler (Click Me)
   public void handle(ActionEvent ae) {
      String label = ((Button)ae.getSource()).getText();
      
      switch(label) {
         case "Click Me":
            
            // Create a timer
            timer = new java.util.Timer();
            
            TimerTask ttask = 
               new TimerTask() {
                  int value = 0;
                  
                  public void run() {
                     // Platform.runLater causes the inner run method to run on the JavaFX thread.
                     // This is necessary if the code touches the UI ... otherwise you might get a
                     // runtime error.
                     Platform.runLater(new Runnable() {
                        public void run() {
                           pbBar.setProgress(value/100.0);    // display the value
                           lblValue.setText("" + value);
                           
                           if(value == 100) {         // if the value is 100 we are done
                              // Change back to Click Me and stop the timer
                              Platform.runLater(new Runnable() {
                                 public void run() {
                                    btnClickMe.setText("Click Me");
                                 }
                              } );
                              
                              timer.cancel();
                           }
                           value += 10;   // Increment the value bu 10
                        }
                     } );
                  }
               };
               
            // Start the timer
            timer.scheduleAtFixedRate(ttask, 1000, 1000);
            break;
         case "Cancel":
            // Change back to Click Me and stop the timer
            btnClickMe.setText("Click Me");
            timer.cancel();
            break;
      }
   }
}