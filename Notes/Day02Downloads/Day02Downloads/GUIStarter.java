import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import java.util.Scanner;
import java.io.*;


public class GUIStarter extends Application implements EventHandler<ActionEvent> {
   // Attributes are GUI components (buttons, text fields, etc.)
   // are declared here.
   private Stage stage;        // The entire window, including title bar and borders
   private Scene scene;        // Interior of window
   public static final String FILE_NAME = "Students.txt";

  // Main just instantiates an instance of this GUI class
   public static void main(String[] args) {
      launch(args);
   }
   // Choose a pane ... Vbox & Gridpane used here
   private VBox root = new VBox(8);
   private GridPane semiroot = new GridPane();
   
   //GUI Components
   Label lblName = new Label("Name\t");
   Label lblID = new Label("ID\t");
   Label lblMajor = new Label("Major\t");
   Label lblGPA = new Label("GPA\t");
   Label lblSpacing = new Label("\t");
   
   TextField tfName = new TextField();
   TextField tfID = new TextField();
   TextField tfMajor = new TextField();
   TextField tfGPA = new TextField();
   
   Button buttonNext = new Button("NEXT");
   Button buttonExit = new Button("EXIT");
   


   
 
   
   // Called automatically after launch sets up javaFX
   public void start(Stage _stage) throws Exception {
      stage = _stage;                        // save stage as an attribute
      stage.setTitle("Student Viewer");            // set the text in the title bar
      
      //Row 4  - - - BUTTONS ADJUSTMENT
      FlowPane fpRow4 = new FlowPane(2, 2);
      fpRow4.setAlignment(Pos.BOTTOM_RIGHT);
      fpRow4.getChildren().addAll(buttonNext, buttonExit);
         
      buttonNext.setOnAction(this);
      buttonExit.setOnAction(this);
      
      semiroot.addRow(0, lblName, tfName);
      semiroot.addRow(1, lblID, tfID);
      semiroot.addRow(2, lblMajor, tfMajor);
      semiroot.addRow(3, lblGPA, tfGPA);
            
      root.getChildren().addAll(semiroot, fpRow4);
      scene = new Scene(root, 300, 150);   // create scene of specified size 
      
                                             // with given layout
      stage.setScene(scene);                 // associate the scene with the stage
      stage.show();                          // display the stage (window)
   }
   
   public void handle(ActionEvent evt) 
   {
      Button gotClicked = (Button)evt.getSource();
   
      System.out.println( gotClicked.getText() + " Clicked!");
      switch (gotClicked.getText())
      {
         case "NEXT":
            System.out.println("Success!");
            break;
         case "EXIT":
            System.exit(0);
            break;
      
         default:
            System.out.println("Button not detected");
            break;
      }      
   }
   public void doNext()
   {
      try
      {
         Scanner scn = new Scanner(new FileReader(new File(FILE_NAME)));
         if (scn.hasNextLine())
         {
            String line = scn.nextLine();
            String[] field = line.split(":");
         //Place fields in the right TextField
               //pops up alert message and says there is no further information
         }
      }
      catch (FileNotFoundException e) 
      {
      
      
      }
   
   }
}

