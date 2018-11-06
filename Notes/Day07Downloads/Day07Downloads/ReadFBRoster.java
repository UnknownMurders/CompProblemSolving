import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
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

/**
 * Class ReadFBRoster
 * A class to read a binary roster of baseball players and display
 * it to the user
 * @author Pete Lutz
 * @version 7-8-2018
 */
public class ReadFBRoster extends Application implements EventHandler<ActionEvent> {
   // Window attributes
   private Stage stage;
   private Scene scene;
   
   // Text fields
   private TextField tfJersey = new TextField();
   private TextField tfName = new TextField();
   private TextField tfPosition = new TextField();
   private TextField tfDOB = new TextField();
   private TextField tfWeight = new TextField();
   private TextField tfAge = new TextField();
   private TextField tfExperience = new TextField();
   private TextField tfCollege = new TextField();
   private TextField[] tfAll = {tfJersey, tfName, tfPosition, tfDOB, tfWeight, 
                                 tfAge, tfExperience, tfCollege};
   private String[] tfLabels = {"Jersey  ", "Name  ", "Position  ", "Birthday  ", "Weight  ", 
                                 "Age  ", "Experience  ", "College  "};
   private int[] tfWidths = {3, 10, 10, 10, 3, 3, 3, 5};
   
   // Buttons
   private Button btnRewind = new Button("Rewind");
   private Button btnNext = new Button("Next");
   private Button btnQuit = new Button("Quit");
   
   //File IO Attributes
   public static final String FILE_NAME = "FBRosterBINOut.dat";
   private File fileobj = null;
   private FileInputStream fis = null;
   private DataInputStream dis = null;
   
   /** main program */
   public static void main(String[] args) {
      launch(args);
   }
   
   /** Constructor */
   public void start(Stage _stage) {
      // Set up window
      stage = _stage;
      stage.setTitle("Football Roster");
      VBox root = new VBox(8);
            
      // Make the Top a GridPane that is 2x whatever
      // Col 1 is a label and Col 2 is a text field on each row
      GridPane gpTop = new GridPane();
      for(int i = 0; i < tfAll.length; i++) {
         Label label = new Label(tfLabels[i]);
         gpTop.setHalignment(label, HPos.RIGHT);
         gpTop.setFillWidth(tfAll[i], false);
         tfAll[i].setPrefColumnCount(tfWidths[i]);
         tfAll[i].setEditable(false);
         gpTop.addRow(i, label, tfAll[i]);
      }
      root.getChildren().add(gpTop);
      
      // The SOUTH will have the navigation buttons
      FlowPane fpBot = new FlowPane(8,8);
      fpBot.setAlignment(Pos.CENTER);
      fpBot.getChildren().addAll(btnRewind, btnNext, btnQuit);
      root.getChildren().add(fpBot);
      
     //Activate the buttons
      btnRewind.setOnAction(this);
      btnNext.setOnAction(this);
      btnQuit.setOnAction(this); 
      
      //Do the work of the program
      doOpen();
      
      // Set scene and show window
      scene = new Scene(root, 250, 250);
      stage.setScene(scene);
      stage.show();
      
      //Read the next (1st) record and display it
      doNext();
      
   
   }
   
   
   //Opens the File
   public void doOpen()
   {
      try {
         File fileObj = new File(FILE_NAME);
         fis = new FileInputStream(fileObj);
         dis = new DataInputStream(fis);
      }
      catch(IOException ioe) {
         Alert alert = new Alert(AlertType.ERROR, "Cannot read file " + FILE_NAME + ": " + ioe);
         alert.showAndWait();
         System.exit(0);
      }
   }//end doOpen()
   
   //doClose - close the file - - now rewinds
   public void doClose()
   {
      try 
      {
         dis.close();
         File fileObj = new File(FILE_NAME);
         fis = new FileInputStream(fileObj);
         dis = new DataInputStream(fis);
         doNext(); //Resets back to the start of the file as intentional
      
      }
      catch (Exception e)
      {
         Alert alert = new Alert(AlertType.ERROR, "Cannot rewind file " + FILE_NAME + ": " + e);
         alert.showAndWait();
         System.exit(0);
      
      }
   }
   
   //Read the next player in and display the data
   public void doNext()
   {//read in one record
      try {
         int jersey = dis.readInt(); //converts binary to int
         String name = dis.readUTF(); //converts binary to String
         String pos = dis.readUTF(); //converts binary to String
         String bday = dis.readUTF(); //converts binary to String
         double weight = dis.readDouble(); //converts binary to Double
         int age = dis.readInt(); //converts binary to int
         String experience = dis.readUTF(); //converts binary to String
         String college = dis.readUTF();  //converts binary to String
         
         //Display in the GUI
         tfJersey.setText("" + jersey);
         tfName.setText(name);
         tfPosition.setText(pos);
         tfDOB.setText(bday);
         tfWeight.setText(String.format("%.2f", weight));
         tfAge.setText("" + age);
         tfExperience.setText(experience);
         tfCollege.setText(college);
      
         
         
      }
      catch (Exception e)
      {
         Alert alert = new Alert(AlertType.ERROR, "Cannot read file . . ." + e);
         alert.setHeaderText("Exception");
         alert.showAndWait();
         //no need for system exit - it is not a severe error
      }
   }
   
   
   /** ActionEvent handler */
   public void handle(ActionEvent ae) {
      String command = ((Button) ae.getSource()).getText();
      System.out.println(command + " clicked!");
      
      switch(command) {
         case "Rewind":
            doClose();
            break;
         case "Next":
            doNext();
            break;
         case "Quit":
            System.exit(0);
         default: 
            System.out.println("Unrecognized Button Clicked!");
            break;
      }
   }
}