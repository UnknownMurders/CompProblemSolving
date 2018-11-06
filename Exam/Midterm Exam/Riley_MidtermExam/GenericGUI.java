import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import java.io.*;

public class GenericGUI extends Application {
   // Window attributes
   Stage stage;
   Scene scene;
   VBox root;
   
   // GUI Components
   private TextArea taOutput = new TextArea();

   // Main program
   public static void main(String[] args) {
      launch(args);
   }
   
   // Callback to start the GUI
   public void start(Stage _stage) {
      // Setup window
      stage = _stage;
      stage.setTitle("Practical 1");
      stage.setOnCloseRequest(
         new EventHandler<WindowEvent>() {
            public void handle(WindowEvent evt) {
               System.exit(0);
            }
         } );
      root = new VBox();
      
      // Format the text area
      root.getChildren().add(taOutput);
      taOutput.setFont(Font.font("Monospaced", 14));
      
      // Visible and do work
      scene = new Scene(root, 600, 200);
      stage.setScene(scene);
      stage.show();
      
      Thread t = 
         new Thread() {
            public void run() { 
               doWork(); 
            }
         };
      
      t.start();
   }
   
   // Do the work of the program
   public void doWork() {
   
   }
}