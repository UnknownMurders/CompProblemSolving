import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.text.*;
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
import java.util.Timer;
import java.util.Arrays;
import java.lang.*;
import java.util.*;



public class Finder extends Application implements EventHandler<ActionEvent> 
{
   // Attributes are GUI components (buttons, text fields, etc.)
   // are declared here.
   private Stage stage;        // The entire window, including title bar and borders
   private Scene scene;        // Interior of window

  // Main just instantiates an instance of this GUI class
   public static void main(String[] args) {
      launch(args);
   }
   // Choose a pane ... Vbox & Gridpane used here
   private VBox root = new VBox(8);
   private FlowPane topRoot = new FlowPane();
   private FlowPane midRoot = new FlowPane(8, 8);
   private FlowPane bottomRoot = new FlowPane(5, 10);
   // Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.red);



   //GUI Components
   TextArea taBox = new TextArea();
   Label labelFind = new Label("Find");
   TextField tfFind = new TextField();
   Button buttonFind = new Button("Find");
   Button buttonClear = new Button("Clear");
   
   // Called automatically after launch sets up javaFX
   public void start(Stage _stage) throws Exception
   {
      topRoot.getChildren().addAll(taBox);
      taBox.setWrapText(true);
      taBox.setPrefColumnCount(42);
      taBox.setPrefRowCount(22);
      topRoot.setAlignment(Pos.CENTER);
      
      midRoot.getChildren().addAll(labelFind, tfFind);
      midRoot.setAlignment(Pos.CENTER);
      
      bottomRoot.getChildren().addAll(buttonFind, buttonClear);
      bottomRoot.setAlignment(Pos.CENTER);
      
      root.getChildren().addAll(topRoot, midRoot, bottomRoot);
   
   
      stage = _stage;                        // save stage as an attribute
      stage.setTitle("Finder");            // set the text in the title bar
      
      scene = new Scene(root, 500, 450);   // create scene of specified size (W X H)
      
      buttonClear.setOnAction(this);
      buttonFind.setOnAction(this);
      
      
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
         case "Find":
            doFind();        
            break;
         case "Clear":
           // actions.doClear();
            //tfFind.setText("");
         //doClear();
         
            EventHandler<ActionEvent> eventHandler =  
               new EventHandler<ActionEvent>()
               {
                  @Override
                  public void handle(ActionEvent e)
                  {
                     tfFind.setText("");
                  }
               };
         
            break;
         default:
            System.out.println("Button not detected");
            break;
      }      
   }
   public void doFind()
   {
   
   //Grabs both text field/area
      String search = tfFind.getText();
      String found = taBox.getText();
         
            //Finds textarea finds textfield
            
      int target = found.indexOf(search);
      
      
     
         
            //int caretPosition = taBox.getCaretPosition();
         
            //Highlights the text that has been found
      taBox.selectRange(target, target + search.length());
      
      
      String selected = taBox.getSelectedText();     
      
      System.out.println(selected);
      
      
      scene.getStylesheets().add(this.getClass().getResource("FindHiLight.css").toExternalForm());
         
          /*taBox.caretPositionProperty();
            taBox.anchorProperty();  
            taBox.selectPreviousWord();*/
          
          
      taBox.requestFocus();
                  
            //System.out.println(tfFind.getLength());     
         //int index = taBox.getText().indexOf(tfFind.getText()); 
         //taBox.selectRange(tfFind.getText().charAt(0), tfFind.getLength());    
         //tfFind.selectRange(index, index + tfFind.getLength());  
   }
      
      
   
   
   
   
        
         
      
   @Override
         //It should be placed outside other bracket quotes. 
         public void stop()
   {
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Pop-up Message");
      alert.setHeaderText("Thank you for using this program!");
      alert.setContentText("Enjoy your day!");
      alert.showAndWait();
            
   }


   

}