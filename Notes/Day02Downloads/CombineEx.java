import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;

public class CombineEx extends Application implements EventHandler<ActionEvent> 
{
   // Attributes are GUI components (buttons, text fields, etc.)
   // are declared here.
   private Stage stage;        // The entire window, including title bar and borders
   private Scene scene;        // Interior of window
   // Choose a pane ... BorderPane used here
   private VBox root = new VBox(8);
   
   //GUI Components
      //First Row
      Label lblServer = new Label("Server");
      TextField tfServer = new TextField();
      Button btnServer = new Button("CONNECT");
      //Second Row
      Label lblToSend = new Label("To Send");
      TextField tfToSend = new TextField();
      Button btnToSend = new Button("SEND");
      //Third Row
      TextArea taLog = new TextArea();
      

   // Main just instantiates an instance of this GUI class
   public static void main(String[] args) 
   {
      launch(args);
   }
   
   // Called automatically after launch sets up javaFX
   public void start(Stage _stage) throws Exception 
   {
      stage = _stage;                        // save stage as an attribute
      stage.setTitle("Combined Example");            // set the text in the title bar
      //Row 1  - - - SERVER INFO
      FlowPane fpRow1 = new FlowPane(8, 8);
      fpRow1.getChildren().addAll(lblServer, tfServer, btnServer);
      
      //Row 2  - - - SENDING INFO
      FlowPane fpRow2 = new FlowPane(8, 8);
      fpRow2.getChildren().addAll(lblToSend, tfToSend, btnToSend);
      
      //Row 3  - - - AREA INFO
      FlowPane fpRow3 = new FlowPane(8, 8);
      fpRow3.getChildren().addAll(taLog);
      root.getChildren().addAll(fpRow1, fpRow2, fpRow3);
      
      scene = new Scene(root, 450, 225);   // create scene of specified size 
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
