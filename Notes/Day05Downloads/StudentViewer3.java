import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.stage.FileChooser.*;
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
import java.util.Arrays;

public class StudentViewer3 extends Application implements EventHandler<ActionEvent> 
{
   // Attributes are GUI components (buttons, text fields, etc.)
   // are declared here.
   private Stage stage;        // The entire window, including title bar and borders
   private Scene scene;        // Interior of window
   // Choose a pane ... BorderPane used here
   private VBox root = new VBox(8);
   private HBox semirootOne = new HBox();
   private GridPane semirootTwo = new GridPane();
   private GridPane semirootThree = new GridPane();
   public ArrayList<String[]> entries = new ArrayList<String[]>();

   
   //GUI Components
   Label lblName = new Label("Name");
   Label lblID = new Label("ID");
   Label lblGPA = new Label("GPA ");
   
   TextField tfName = new TextField();
   TextField tfID = new TextField();
   TextField tfGPA = new TextField();
   TextField tfFile = new TextField();
   
   Button btnOpen = new Button("OPEN");
   Button btnNext = new Button("NEXT");
   Button btnExit = new Button("EXIT");
   
   TextArea taArea = new TextArea();
   
   int ptr = 0;
   FileChooser fc = new FileChooser();



   // Main just instantiates an instance of this GUI class
   public static void main(String[] args) 
   {
      launch(args);
   }
   
   // Called automatically after launch sets up javaFX
   public void start(Stage _stage) throws Exception 
   {
      stage = _stage;                        // save stage as an attribute
      stage.setResizable(false);
      stage.setTitle("Student Viewer 3 - RILEY");            // set the text in the title bar
      tfID.setPrefColumnCount(6);
      tfGPA.setPrefColumnCount(4);
      
      semirootOne.getChildren().addAll(btnOpen, tfFile);
      semirootOne.setAlignment(Pos.CENTER);
      
      
      semirootTwo.addRow(0, taArea);
      semirootTwo.setAlignment(Pos.CENTER);
      
      
      semirootThree.addRow(0, btnExit);
      semirootThree.setAlignment(Pos.CENTER);
      
      
      
      root.getChildren().addAll(semirootOne, semirootTwo, semirootThree);
               
      btnOpen.setOnAction(this);
      btnNext.setOnAction(this);
      btnExit.setOnAction(this);
   
            
            
            
      scene = new Scene(root, 300, 155);   // create scene of specified size 
                                             // with given layout
      stage.setScene(scene);                 // associate the scene with the stage
      stage.show();                          // display the stage (window)
   }
   
   
   
   public void handle(ActionEvent evt)
   {
      Button gotClicked = (Button)evt.getSource(); //grabs source and relays to next
      System.out.println( gotClicked.getText() + " Clicked!");//confirms message which button was clicked
         
      switch (gotClicked.getText())
      {
         case "OPEN":
            doOpen();
            break;
         
         case "NEXT": 
         //doNext();
         
            break;
         case "EXIT":
            System.exit(0);
            break;
            
         default:
            System.out.println("Button not detected");
            break;
      
         
      }
      
   }//end handle
   
   public void doOpen() 
   {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Open Student File");
      fileChooser.getExtensionFilters().addAll(
               new ExtensionFilter("Text Files", "*.txt"),
               new ExtensionFilter("All Files", "*.*"));
               
      File selectedFile = fileChooser.showOpenDialog(stage);
      if (selectedFile == null) 
      {
         // Canceled
         return;
      }
      
      // File selected. Display file name in text field
      tfFile.setText(selectedFile.getAbsolutePath());
      
      // Open the file
      try 
      {
         Scanner scn = new Scanner(new FileInputStream(selectedFile));
         doNext();
      }
      catch(Exception e)
      {
         Alert alert = new Alert(AlertType.ERROR,"Exception (" + e + ") opening file ... fatal.");
         alert.showAndWait();
         System.exit(1);
      
      }
   }   
   
   private void navigate(int index)
   {
      String[] data = entries.get(index);
      tfName.setText(data[0]);
      tfID.setText(data[1]);
      tfGPA.setText(data[2]);
   }

   
   public void doNext() throws IOException
   {
      try 
      {    
         ptr++;
         navigate(ptr);
      }
      
      catch (Exception e)
      {
         System.out.println("ERROR - GENERAL EXCEPTION");
         ptr--;
      }
         
   }
}	// end file
