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

public class ItemOrderCalculator extends Application implements EventHandler<ActionEvent>
{
   public static void main(String[] args) 
   {
      launch(args);
   }//end main
   
   private Stage stage;    // The entire window, including title bar and borders
   private Scene scene;    // Interior of window

   //Layouts
   private VBox root = new VBox(10);
   private GridPane semiroot = new GridPane();
   //GUI Components
   Label lblItem = new Label("Item Name:  ");
   Label lblNumber = new Label("Number:  ");
   Label lblCost = new Label("Cost:  ");
   Label lblAmount = new Label("Amount owed:  ");
   Label lblBlank1 = new Label(null);
   Label lblBlank2 = new Label(null);

   
   TextField tfItem = new TextField();
   TextField tfNumber = new TextField();
   TextField tfCost = new TextField();
   TextField tfAmount = new TextField();

   public void start(Stage _stage) throws Exception 
   {
      stage = _stage;
      stage.setTitle("{A. Student} Item Orders Calculator");
      
      //Adding Label and Textboxes
      semiroot.addRow(1, lblItem, tfItem);
      semiroot.addRow(2, lblNumber, tfNumber);
      semiroot.addRow(3, lblCost, tfCost);
      semiroot.addRow(4, lblAmount, tfAmount);
      tfAmount.setEditable(false);
   
      //Right-alignment text label
      semiroot.setHalignment(lblItem, HPos.RIGHT);
      semiroot.setHalignment(lblNumber, HPos.RIGHT);
      semiroot.setHalignment(lblCost, HPos.RIGHT);
      semiroot.setHalignment(lblAmount, HPos.RIGHT);
      semiroot.setAlignment(Pos.CENTER);
   
      //Preparing Flow for Buttons
      FlowPane fpRowOneButtons = new FlowPane(5,2);
      FlowPane fpRowTwoButtons = new FlowPane(3,1);
   
      
      //Buttons
      Button btnCalculate = new Button("CALCULATE");
      Button btnSave = new Button("SAVE");
      Button btnClear = new Button("CLEAR");
      Button btnExit = new Button("EXIT");

      
      
      //Adding Buttons to Rows
         //ROW ONE
      fpRowOneButtons.getChildren().addAll(btnCalculate, btnSave, btnClear, btnExit);
      fpRowOneButtons.setAlignment(Pos.CENTER);
         
   
      
      //Preparing Actions
      btnCalculate.setOnAction(this);
      btnSave.setOnAction(this);
      btnClear.setOnAction(this);
      btnExit.setOnAction(this);
      /*btnLoad.setOnAction(this);
      btnPrev.setOnAction(this);
      btnNext.setOnAction(this);*/
   
    
      
      //root of all evil - adding all components to main vbox GUI
      root.getChildren().addAll(semiroot, fpRowOneButtons, fpRowTwoButtons);
      
      
      
      scene = new Scene(root, 250, 150);   // create scene of specified size 
      stage.setScene(scene);                 // associate the scene with the stage
      stage.show();
   }//end stage
   
   public void handle(ActionEvent evt)
   {
      Button gotClicked = (Button)evt.getSource(); //grabs source and relays to next
      System.out.println( gotClicked.getText() + " Clicked!");//confirms message which button was clicked
         
      switch (gotClicked.getText())
      {
         case "CALCULATE":
            doCalculate();      
            break;
         case "CLEAR":
            doClear();
            break;
         case "SAVE":
            try
            {
               doSave();  
            }
            catch (Exception e)
            {
               System.out.println("ERROR");
            }          
            break;
         case "EXIT":
            System.exit(0);
            break;
         default:
            System.out.println("Button not detected");
         break;
      }
   
   
   }//end handle
   
   public void doCalculate()
   {
      String strNumber = tfNumber.getText();
      String strCost = tfCost.getText();
            
      int number = Integer.parseInt(strNumber);
      double cost = Double.parseDouble(strCost);
            
      double amount = number * cost;
            
      String strAmount = String.valueOf(amount);
            
      tfAmount.setText("$" + strAmount );
   }//end calculate 
   public void doClear()
   {
      tfItem.setText(null);
      tfCost.setText(null);
      tfNumber.setText(null);
      tfAmount.setText(null);
   }
   
   public void doSave() throws IOException
   {
    
      String string =( tfItem.getText() + "," + tfNumber.getText() + "," + tfCost.getText() + "," + tfAmount.getText());
      BufferedWriter writer = new BufferedWriter(new FileWriter("121Lab1.csv"));
      writer.write(string);
     
      writer.close();
   }
   
   public void doLoad() throws IOException
   {

      
   }
   
   
   
   
}//end file
