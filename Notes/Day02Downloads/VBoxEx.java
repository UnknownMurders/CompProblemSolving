import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;

public class VBoxEx extends Application implements EventHandler<ActionEvent> 
{
   // Attributes are GUI components (buttons, text fields, etc.)
   // are declared here.
   private Stage stage;        // The entire window, including title bar and borders
   private Scene scene;        // Interior of window
   // Choose a pane ... BorderPane used here
   private VBox root = new VBox(8);
   
   //GUI Components
   Label lblName = new Label("Name");
   Label lblID = new Label("ID");
   Label lblGPA = new Label("GPA");
   
   TextField tfName = new TextField();
   TextField tfID = new TextField();
   TextField tfGPA = new TextField();



   // Main just instantiates an instance of this GUI class
   public static void main(String[] args) 
   {
      launch(args);
   }
   
   // Called automatically after launch sets up javaFX
   public void start(Stage _stage) throws Exception 
   {
      stage = _stage;                        // save stage as an attribute
      stage.setTitle("VBox Example");            // set the text in the title bar
      tfID.setPrefColumnCount(6);
      tfGPA.setPrefColumnCount(4);
      
      root.getChildren().addAll(lblName, tfName, lblID, tfID, lblGPA, tfGPA);
      
      scene = new Scene(root, 600, 50);   // create scene of specified size 
                                             // with given layout
      stage.setScene(scene);                 // associate the scene with the stage
      stage.show();                          // display the stage (window)
   }
   
   public void handle(ActionEvent evt) 
   {
      // Get the button that was clicked
      Button btn = (Button)evt.getSource();
      
      // Switch on its name
      switch(btn.getText()) 
      {
      //          case "ButtonName":
      //             do what the button requires
      //             break;
      }
   }   
}	
