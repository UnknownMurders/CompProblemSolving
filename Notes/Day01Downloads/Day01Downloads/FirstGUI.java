import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;

/*
Name: Edward Riley
Professor: Peter Lutz
Course: CompProblemSolvingDomain II
Date: 8/27/2018
*/

public class FirstGUI extends Application implements EventHandler<ActionEvent> 
{
   // Attributes are GUI components (buttons, text fields, etc.)
   // are declared here.
   private Stage stage;        // The entire window, including title bar and borders
   private Scene scene;        // Interior of window
   private BorderPane layout;  // Rules for how components are layed out in the window - many different choices of how they're laid out on the layout 
     
   private TextField tfN1 = new TextField(); 
   private TextField tfN2 = new TextField(); 
   private TextField tfN3 = new TextField();
   
   // Main just instantiates an instance of this GUI class
   public static void main(String[] args) 
   {
      launch(args);
   }
   
   // Called automatically after launch sets up javaFX
   public void start(Stage _stage) throws Exception {
      // get the window (stage) and set the title and layout
      stage = _stage; //Saves stage as an attribute
      stage.setTitle("FirstGUI"); // Sets the text in title
      layout = new BorderPane(); //Establishes layout
      
     // Create a FlowPane layout for the TOP and BOTTOM   
     // The 10, 10 gives horizontal and vertical spacing so components are not crammed together
      FlowPane loTop = new FlowPane(10, 10);
      FlowPane loBot = new FlowPane(10, 10);
      loTop.setAlignment(Pos.CENTER);    
      loBot.setAlignment(Pos.CENTER);    
     // Apply the layouts to the main layout’s TOP and BOTTOM areas
      layout.setTop(loTop);
      layout.setBottom(loBot);
      
     //Place GUI Components
      Label lblN1 = new Label("Number 1");
      // loTop.getChildren().add(lblN1);
     // TextField tfN1 = new TextField();
      Label lblN2 = new Label("Number 2"); 
      //TextField tfN2 = new TextField();  
      Label lblN3 = new Label("Result");   
      //TextField tfN3 = new TextField();
      
      loTop.getChildren().addAll(lblN1, tfN1, lblN2, tfN2, lblN3, tfN3);
      
      //Place GUI components in the Bottom
      Button btnAdd = new Button("ADD");
      Button btnClear = new Button("CLEAR");
      Button btnExit = new Button("EXIT");
      
      loBot.getChildren().addAll(btnAdd, btnClear, btnExit);
      
      // Hooking up the button events
      btnAdd.setOnAction(this);
      btnClear.setOnAction(this);
      btnExit.setOnAction(this);
      
      // Make a scene (window interior)
      scene = new Scene(layout, 675, 100); //Size of the window
      // Make the window visible
      stage.setScene(scene);
      stage.show();
   }
   
   public void handle(ActionEvent evt) 
   {
   Button gotClicked = (Button)evt.getSource();
   
   System.out.println( gotClicked.getText() + " Clicked!");
      switch (gotClicked.getText())
      {
      case "ADD":
      doAdd();
      break;
      case "CLEAR":
      doClear();
      break;
      case "EXIT":
      System.exit(0);
      break;
      
      default:
      System.out.println("Button not detected");
      break;
      }
    }
   
   public void doAdd()
   {
      String sN1 = tfN1.getText();
      int n1 = Integer.parseInt(sN1);
      
      String sN2 = tfN2.getText();
      int n2 = Integer.parseInt(sN2);
      
      int res = n1 + n2; //Adds numbers together from different textboxes 
      tfN3.setText(" " + res);
      
   }
   public void doClear()
   {
     tfN1.setText(" "); 
     tfN2.setText(" "); 
     tfN3.setText(" "); 
   } 
}	

