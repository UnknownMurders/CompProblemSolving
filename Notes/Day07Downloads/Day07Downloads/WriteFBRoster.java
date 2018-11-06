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
 * Class WriteFBRoster
 * A class to read a binary roster of baseball players and display
 * it to the user
 * @author Pete Lutz
 * @version 7-8-2018
 */
public class WriteFBRoster extends Application implements EventHandler<ActionEvent> {
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
   private int[] tfWidths = {3, 10, 3, 5, 5, 2, 2, 10};
   
   // Buttons
   private Button btnWrite = new Button("Write");
   private Button btnQuit = new Button("Quit");
   
   //File IO attributes
   public static final String FILE_NAME = "FBRosterBINOut.dat";
   private File fileObj = null;
   private FileOutputStream fos = null;
   private DataOutputStream dos = null;
   
   /** main program */
   public static void main(String[] args) {
      launch(args);
   }
   
   /** Constructor */
   public void start(Stage _stage) {
      // Set up window
      stage = _stage;
      stage.setTitle("Football Roster Writer");
      VBox root = new VBox(8);
            
      // Make the Top a GridPane that is 2x whatever
      // Col 1 is a label and Col 2 is a text field on each row
      GridPane gpTop = new GridPane();
      for(int i = 0; i < tfAll.length; i++) {
         Label label = new Label(tfLabels[i]);
         gpTop.setHalignment(label, HPos.RIGHT);
         gpTop.setFillWidth(tfAll[i], false);
         tfAll[i].setPrefColumnCount(tfWidths[i]);
         gpTop.addRow(i, label, tfAll[i]);
      }
      root.getChildren().add(gpTop);
      
      // The Bottom will have the navigation buttons
      FlowPane fpBot = new FlowPane(8,8);
      fpBot.setAlignment(Pos.CENTER);
      fpBot.getChildren().addAll(btnWrite, btnQuit);
      root.getChildren().add(fpBot);
   
      //Open file and prepares for output
      doOpen();
      
      btnWrite.setOnAction(this);
      btnQuit.setOnAction(this);
   
      // Set scene and show window
      scene = new Scene(root, 250, 250);
      stage.setScene(scene);
      stage.show();      
   }
   
   //Write a record out
   public void doWrite()
   {
      try
      {
         int jersey = Integer.parseInt(tfJersey.getText());
         String name = tfName.getText();
         String pos = tfPosition.getText();
         String bday = tfDOB.getText();
         double weight = Double.parseDouble(tfWeight.getText());
         int age = Integer.parseInt(tfAge.getText());
         String experience = tfExperience.getText();
         String college = tfCollege.getText();
         
         //Write information to file
         dos.writeInt(jersey);
         dos.writeUTF(name);
         dos.writeUTF(pos);
         dos.writeUTF(bday);
         dos.writeDouble(weight);
         dos.writeInt(age);
         dos.writeUTF(experience);
         dos.writeUTF(college);
         
         //CLEAR THE GUI TEXT
         tfJersey.setText("");
         tfName.setText("");
         tfPosition.setText("");
         tfDOB.setText("");
         tfWeight.setText("");
         tfAge.setText("");
         tfExperience.setText("");
         tfCollege.setText("");

      }   
      catch (Exception e)
      {
         Alert alert = new Alert(AlertType.ERROR, "Cannot open file " + FILE_NAME + ": " + e);
         alert.showAndWait();
         System.exit(0);
      
      }
   
   }
   
   /** ActionEvent handler */
   public void handle(ActionEvent ae) {
      String label = ((Button)ae.getSource()).getText();
     
      System.out.println(label + " clicked!");
      
      switch(label) {
         case "Write":
            doWrite();
            break;
         case "Quit":
            try 
            {
               dos.close();
               System.exit(0);
            }
            catch (Exception e)
            {
               Alert alert = new Alert(AlertType.ERROR, "Cannot open file " + FILE_NAME + ": " + e);
               alert.showAndWait();
               System.exit(0);      
            }
            break;
      }
   }
   
   public void doOpen()
   {
      try {
         File fileObj = new File(FILE_NAME);
         fos = new FileOutputStream(fileObj);
         dos = new DataOutputStream(fos);
      }
      catch(IOException ioe) {
         Alert alert = new Alert(AlertType.ERROR, "Cannot open file " + FILE_NAME + ": " + ioe);
         alert.showAndWait();
         System.exit(0);
      }
   }//end doOpen()
}