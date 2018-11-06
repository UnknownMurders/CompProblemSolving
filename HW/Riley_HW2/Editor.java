/*
NAME: Edward Riley
PROFESSOR: Peter Lutz
COURSE: Computational Problem Solving in Domain II
DATE: 9/17/2018
*/

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.application.Application;

public class Editor extends Application
{
   //Layouts
   MenuBar menubar = new MenuBar();
   VBox root = new VBox(); 

   //GUI Components
   Stage stage;
   Scene scene;
   MenuItem itemNew = new MenuItem("New");
   MenuItem itemOpen = new MenuItem("Open");
   MenuItem itemSave = new MenuItem("Save");
   MenuItem itemExit = new MenuItem("Exit");
   MenuItem itemSaveAs = new MenuItem("Save As");
   MenuItem itemWCount = new MenuItem("Word Count");
   MenuItem itemHEditor = new MenuItem("Help Editor");
   
   
   private TextArea taScript = new TextArea();    

   public Editor() { }
   
   public static void main(String[] args)
   {
      launch(args);
   }
   
   public void start(Stage _stage)
   {
      stage = _stage;
      stage.setTitle("Editor");
      stage.setOnCloseRequest(
         new EventHandler<WindowEvent>() 
         {
            public void handle(WindowEvent evt)
            {
               System.exit(0);
            }
         });
      
      
      //Set "File"
      Menu menuItemFile = new Menu("File"); 
      menuItemFile.getItems().add(itemNew); 
      menuItemFile.getItems().add(itemOpen); 
      menuItemFile.getItems().add(itemSave); 
      menuItemFile.getItems().add(itemSaveAs); 
      menuItemFile.getItems().add(itemExit); 
      menubar.getMenus().add(menuItemFile); 
      
      
      Menu menuItemHelp = new Menu("Help"); 
      menuItemHelp.getItems().add(itemHEditor); 
      menubar.getMenus().add(menuItemHelp); 
      
      Menu menuItemTools = new Menu("Tools"); 
      menuItemTools.getItems().add(itemWCount);
      menubar.getMenus().add(menuItemTools); 
        
      
   
      taScript.setWrapText(true); 
      taScript.setFont(javafx.scene.text.Font.font("Courier", 16));
      taScript.setPrefHeight(500);
      root.getChildren().addAll(menubar, taScript); 
   
      FileListener flistener = new FileListener(stage, taScript); 
      itemNew.setOnAction(flistener);
      itemOpen.setOnAction(flistener);
      itemSave.setOnAction(flistener);
      itemSaveAs.setOnAction(flistener);
      itemExit.setOnAction(flistener);
      
      // To count the words 
      itemWCount.setOnAction(
         new EventHandler<ActionEvent>() 
         {
            public void handle(ActionEvent ae) 
            {
               String[] words = taScript.getText().split("[\t\n ]");
               if (words.length == 1)
               {
                  Alert alert = new Alert(Alert.AlertType.INFORMATION, "" + words.length + " word.");
                  alert.setHeaderText("Word Count");
                  alert.showAndWait();
               }
               else
               {
                  Alert alert = new Alert(Alert.AlertType.INFORMATION, "" + words.length + " words.");
                  alert.setHeaderText("Word Count");
                  alert.showAndWait();
               }
            }
         });
      
      // Displays information regarding the program 
      itemHEditor.setOnAction(
         new EventHandler<ActionEvent>() 
         {
            public void handle(ActionEvent ae)
            {
               Alert alert = new Alert(AlertType.INFORMATION);
               alert.setTitle("Pop-up Message");
               alert.setHeaderText("Program Info");
               alert.setContentText("Author: Edward Riley\nDate: 9/17/2018");
               alert.showAndWait();
            }
         });
      
      //Size of the QUI and visable
      scene = new Scene(root, 500, 500); 
      stage.setScene(scene);
      stage.show();
   }
}