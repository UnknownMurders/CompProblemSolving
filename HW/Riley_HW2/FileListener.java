/*
NAME: Edward Riley
PROFESSOR: Peter Lutz
COURSE: Computational Problem Solving in Domain II
DATE: 9/13/2018
*/


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

public class FileListener implements EventHandler<ActionEvent> {
   
      
   private Stage stage;    // The entire window, including title bar and borders
   private Scene scene;    // Interior of window

   //Layouts
   private VBox root = new VBox();
   private GridPane semiroot = new GridPane();
   
   //GUI Components
   TextArea taScript = new TextArea();
   MenuItem itemNew = new MenuItem("New");
   MenuItem itemOpen = new MenuItem("Open");
   MenuItem itemSave = new MenuItem("Save");
   MenuItem itemExit = new MenuItem("Exit");
   MenuItem itemSaveAs = new MenuItem("Save As");
   MenuItem itemWCount = new MenuItem("Word Count");
   MenuItem itemHEditor = new MenuItem("Help Editor");
   String fileName = "";

   public FileListener(Stage _stage, TextArea _taScript)
   {
      stage = _stage;
      taScript = _taScript;
   }
   
 
// public EventHandler<ActionEvent> performActions()  {
//       return 
//          new EventHandler<ActionEvent>() {
            public void handle(ActionEvent evt)
            {
            
               MenuItem gotClicked = (MenuItem)evt.getSource(); //grabs source and relays to next with Button concept
               System.out.println( gotClicked.getText() + " Clicked!"); //confirms message which button was clicked
            
               switch (gotClicked.getText())
               {
                  case "New": 
                     doNew();
                     break;
                  case "Open":
                     doOpen();
                     break;
                  case "Save": 
                     doSave();
                     break;
                  case "Save As":
                     doSaveAs();
                     break;
                  case "Exit":
                     System.exit(0);
                     break;
                  case "Help Editor":
                     Alert alert = new Alert(AlertType.INFORMATION);
                     alert.setTitle("Message");
                     alert.setHeaderText("Author");
                     alert.setContentText("Name: Edward Riley\nDate: 9/17/2018");
                     alert.showAndWait();
                     break;
                  case "Word Count":
                     doWordCount();
                     break;
                  
                  
               
                  default:
                     System.out.println("BUTTON NOT FOUND");
                     break;
               }//end switch statement
            
            }
//          
//          };
//    }//end action event handler
   
      //method for program clearing for new file
   public void doNew()
   {
      //Clears space and cleans the file trace
      stage.setTitle("Editor");
      taScript.setText("");   
      fileName = null;      
   }
   
   //method for program opening
   public void doOpen()
   {
      //Initializes fileChooser to be able to choose files in a new window
      FileChooser fileChooser = new FileChooser();
      fileChooser.setInitialDirectory(new File("."));
      fileChooser.setTitle("Open Text File");
      fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", new String[] { "*.txt" }));
    
    //occurs when a new file is selected
      File selectedFile = fileChooser.showOpenDialog(stage);
      //cancels and returns an "actual" file absolute path if none is given 
      if (selectedFile == null) 
      { 
         return;
      }
      fileName = selectedFile.getAbsolutePath();
    
      //appends to the taScript (textarea)
      BufferedReader rdr = null;
      try 
      {
         rdr = new BufferedReader(new java.io.FileReader(selectedFile));
      }
      catch (FileNotFoundException e) {
         Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot open file '" + fileName + "'\n" + e.getMessage(), new ButtonType[0]);
         alert.setHeaderText("Cannot open");
         alert.showAndWait();
         fileName = "";
         stage.setTitle("Editor");
         return;
      }
   }
   
   //method in preparation to save the file
   public void doSave()
   {
      if (fileName.equals("")) 
      {
         Alert alert = new Alert(Alert.AlertType.ERROR, "You don't have a file given.");
         alert.setHeaderText("No File Name Detected");
         alert.showAndWait();
         return;
      }
    
      //retrieves the file
      File fileObj = new File(fileName);
      PrintWriter prt = null;
      try 
      {
         prt = new PrintWriter(new java.io.FileWriter(fileObj));
      }
      catch (IOException ioe) 
      {
         Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot open file '" + fileName + "'\n" + ioe.getMessage());
         alert.setHeaderText("Cannot open");
         alert.showAndWait();
         fileName = "";
         stage.setTitle("Editor");
         return;
      }
    
      //Prints the file
      prt.print(taScript.getText());
      prt.close();
      
      //incase if it's not already set
      stage.setTitle("Editor <" + fileName + ">");
   }
   public void doSaveAs()
   {
      //Allows a user to be able to open up new window to be able to save to a new file
      FileChooser fileChooser = new FileChooser();
      fileChooser.setInitialDirectory(new File("."));
      fileChooser.setTitle("Save Text File");
      
      //ONLY .txt files can be saved
      fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", new String[] { "*.txt" }));
    
      File selectedFile = fileChooser.showSaveDialog(stage);
    
      if (selectedFile == null) 
      {
         return;
      }
    
      fileName = selectedFile.getAbsolutePath();
    
      //dejavu doSave()
      if (fileName.equals("")) 
      {
         Alert alert = new Alert(Alert.AlertType.ERROR, "You don't have a file");
         alert.setHeaderText("No File Name Detected");
         alert.showAndWait();
         return;
      }
    
      //retrieves the file
      File fileObj = new File(fileName);
      PrintWriter prt = null;
      try 
      {
         prt = new PrintWriter(new java.io.FileWriter(fileObj));
      }
      catch (IOException ioe) 
      {
         Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot open file '" + fileName + "'\n" + ioe.getMessage(), new ButtonType[0]);
         alert.setHeaderText("Cannot open");
         alert.showAndWait();
         fileName = "";
         stage.setTitle("Editor");
         return;
      }
    
      //Prints the file
      prt.print(taScript.getText());
      prt.close();
      
      //incase if it's not already set
      stage.setTitle("Editor <" + fileName + ">");  
   }
   
   //This is a program where user can use tool to count each words
   public void doWordCount()
   {  
      
      String[] words = taScript.getText().split("[\t\n ]+");
      Alert alert = new Alert(Alert.AlertType.INFORMATION, "" + words.length + " words");
      alert.setHeaderText("Word Count");
      alert.showAndWait();
      
      
      
      /*if (taScript == null)
      {
         Alert alert = new Alert(Alert.AlertType.INFORMATION, "0 words", new ButtonType[0]);
         alert.setHeaderText("Word Count");
         alert.showAndWait();
      }
      else
      {
         Alert alert = new Alert(Alert.AlertType.INFORMATION, "" + words.length + " words", new ButtonType[0]);
         alert.setHeaderText("Word Count");
         alert.showAndWait();
      }*/
   }


   

   
}//end file
   
