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
import java.util.ArrayList;
import java.util.Arrays;

/*
Name: Edward Riley
Date: 9/05/2018
Professor: Peter Lutz
Course: Computational Solving Domain II
Purpose: The purpose of this order system is to expand GUI by utilziing GUI  
*/

public class OrderSystem extends Application implements EventHandler<ActionEvent>
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
    //Add arrays for textfields
   String[] textLine = new String[3];
   public ArrayList<String[]> entries = new ArrayList<String[]>();

   
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
   FileReader fr = null;
   final String OUTFILE = "121Labl.csv";
   private int ptr = 0;
   public int index = 0;
   
   
   File file = new File(OUTFILE);
   BufferedReader br = null;

   public void start(Stage _stage) throws Exception 
   {
      stage = _stage;
      stage.setTitle("{E. Riley} Item Orders Calculator");
      
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
      btnCalculate.setTooltip(new Tooltip("This button does the calculation"));
      Button btnSave = new Button("SAVE");
      btnSave.setTooltip(new Tooltip("This button saves everything in your text messages to a file for later use."));
      Button btnClear = new Button("CLEAR");
      btnClear.setTooltip(new Tooltip("This button will clear your current text messages and your cache"));
      Button btnExit = new Button("EXIT");
      btnExit.setTooltip(new Tooltip("This button will terminate the program"));
      Button btnLoad = new Button("LOAD");
      btnLoad.setTooltip(new Tooltip("This button will restore prior all saved calculation (except for cleared)"));
      Button btnPrev = new Button("<PREV");
      btnPrev.setTooltip(new Tooltip("This button will move to the previous saved calculation"));
      Button btnNext = new Button(">NEXT");
      btnNext.setTooltip(new Tooltip("This button will move to the next saved calculation"));

      
      
      //Adding Buttons to Rows
         //ROW ONE
      fpRowOneButtons.getChildren().addAll(btnCalculate, btnSave, btnClear, btnExit, btnLoad);
      fpRowOneButtons.setAlignment(Pos.CENTER);
         //ROW TWO
      fpRowTwoButtons.getChildren().addAll(btnPrev, btnNext);
      fpRowTwoButtons.setAlignment(Pos.CENTER);
   
      
      //Preparing Actions
      btnCalculate.setOnAction(this);
      btnSave.setOnAction(this);
      btnClear.setOnAction(this);
      btnExit.setOnAction(this);
      btnLoad.setOnAction(this);
      btnPrev.setOnAction(this);
      btnNext.setOnAction(this);
   
    
      
      //adding all components to main vbox GUI
      root.getChildren().addAll(semiroot, fpRowOneButtons, fpRowTwoButtons);
      
      
      
      scene = new Scene(root, 350, 200);   // create scene of specified size 
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
            catch (FileNotFoundException ex) 
            {
               System.out.println("File not found");
               Alert alert = new Alert(AlertType.WARNING);
               alert.setTitle("Warning Dialog");
               alert.setHeaderText("Missing file or no data to read.");
               alert.setContentText(null);
               alert.showAndWait();
            } 
            catch (IOException ioe) 
            {
               System.out.println("ERROR - IO Exception");
            } 
            catch (Exception e)
            {
               System.out.println("ERROR");
            }          
            break;
         case "EXIT":
            System.exit(0);
            break;
         case "LOAD":
            try
            { 
               doLoad(); 
            }
            catch (FileNotFoundException ex) 
            {
               System.out.println("File not found");
               Alert alert = new Alert(AlertType.WARNING);
               alert.setTitle("Warning Dialog");
               alert.setHeaderText("Missing file or no data to read.");
               alert.setContentText(null);
               alert.showAndWait();
            } 
            catch (IOException ioe) 
            {
               System.out.println("ERROR - IO Exception");
            }
            catch (Exception e)
            {
               System.out.println("ERROR - GENERAL EXCEPTION");
            }
            break;
         case ">NEXT":
            try
            {
               doNext();
            }
            catch (Exception e)
            {
               System.out.println("ERROR");
            }
            break;
         case "<PREV":
            try
            {
               doPrevious();
            }
            catch (Exception e)
            {
               System.out.println("ERROR");
            }
            break;
         default:
            System.out.println("Button not detected");
            break;
         
      }
   
   }//end handle
   
   
   //-------------------------
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
      entries.clear();
   }
   
   public void doSave() throws IOException
   {
    
      PrintWriter pV = new PrintWriter(new BufferedWriter(new FileWriter("121Lab1.csv", true)));
      pV.println("' " + tfItem.getText() + "', " + tfNumber.getText() + ", " + tfCost.getText() + ", " + tfAmount.getText() + "\n");
      pV.flush();
      pV.close();
   }
   
   public void doLoad() throws IOException
   {
      String line = ""; 
     
     //BufferedReader reader = new BufferedReader(new FileReader("121Lab1.csv"), ',');
     
      FileReader reader = new FileReader("121Lab1.csv");
      br = new BufferedReader(reader);
      int counter = 0;
      
      
            //A prototype logic that I was working on which ended up too much work
      /*while ((line = br.readLine()) != null) //Suppose to print out each lines after comma. 
      {
         String [] delimiter = line.split(",");
         
         String name = delimiter[0];
         
         String number = delimiter[1];
         
         String cost = delimiter[2];
         
         String amountOwned = delimiter[3];
         counter++;
         
         
         //Test to see if Load button is accepting proper instance variables
         /*System.out.println(name);
         System.out.println(number);
         System.out.println(cost);
         System.out.println(amountOwned);
      }*/

   //Second attempt at prototype logic in which succeeded.  
      String splitLine;   
      while ((splitLine = br.readLine())  !=  null)   
      {
         textLine = splitLine.split(",");
         entries.add(new String[]{textLine[0], textLine[1], textLine[2], textLine[3]});
      }
      tfItem.setText(entries.get(0)[0]);
      tfNumber.setText(entries.get(0)[1]);
      tfCost.setText(entries.get(0)[2]);
      tfAmount.setText(entries.get(0)[3]);
   
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Message");
      alert.setHeaderText("File Read");
      alert.setContentText(entries.size() + " Records Read");
         
      alert.showAndWait();
      br.close();
   }
   
   private void navigate(int index)
   {
      String[] data = entries.get(index);
      tfItem.setText(data[0]);
      tfNumber.setText(data[1]);
      tfCost.setText(data[2]);
      tfAmount.setText(data[3]);
   
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
   
   private void doPrevious()
   { 
      try 
      {    
         ptr--;
         navigate(ptr);
      }
      catch (Exception e) 
      {
         System.out.println("ERROR - GENERAL EXCEPTION");
      
         ptr++;
      } 
   }
   
   
   
   
}//end file
