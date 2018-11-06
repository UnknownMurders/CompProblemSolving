/*
NAME: Edward Riley
PROFESSOR: Peter Lutz
COURSE: Computation Problem Solving - Information Domain II (Java II)
DATE: 9/12/2018
*/

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


public class BaseballRileyA extends Application implements EventHandler<ActionEvent> {
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
   private FlowPane topPane = new FlowPane(5,5);
   private FlowPane midPane = new FlowPane(5,5);
   private FlowPane bottomPane = new FlowPane(5,5);

   //GUI Components
   Label laFile = new Label("File");
   TextField tfFileName = new TextField();
   Button btnOpen = new Button("Open");
   Label laRecordsIn = new Label("Records In:");
   TextField tfRecordsIn = new TextField();
   Label laRecordsOut = new Label("Records Out:");
   TextField tfRecordsOut = new TextField();
   TextArea taData = new TextArea();
   
   // Called automatically after launch sets up javaFX
   public void start(Stage _stage) throws Exception {
      topPane.getChildren().addAll(laFile, tfFileName, btnOpen);
      midPane.getChildren().addAll(laRecordsIn, tfRecordsIn, laRecordsOut, tfRecordsOut);
      bottomPane.getChildren().addAll(taData);
     
     //Adjusts the column count. 
      tfFileName.setPrefColumnCount(30);
      tfRecordsIn.setPrefColumnCount(5);
      tfRecordsOut.setPrefColumnCount(5);
      taData.setPrefColumnCount(59);
      taData.setPrefRowCount(23);
      taData.setWrapText(true);
      taData.setFont(javafx.scene.text.Font.font("MONOSPACED", 12));
     
     //sets them to center position
      topPane.setAlignment(Pos.CENTER);
      midPane.setAlignment(Pos.CENTER);
      bottomPane.setAlignment(Pos.CENTER);
   
     //adds panes to the layout
      root.getChildren().addAll(topPane, midPane, bottomPane);
     
     
      stage = _stage;                        // save stage as an attribute
      stage.setTitle("Baseball - E. Riley");            // set the text in the title bar
      
      scene = new Scene(root, 450, 400);   // create scene of specified size (W X H)
   
      btnOpen.setOnAction(this);
      
                                             // with given layout
      stage.setScene(scene);                 // associate the scene with the stage
      stage.show();                          // display the stage (window)
   }
   
   
   //Buttons
   public void handle(ActionEvent evt) 
   {
      //Button got clicked
      Button gotClicked = (Button)evt.getSource();
   
      System.out.println( gotClicked.getText() + " Clicked!");
      switch (gotClicked.getText())
      {
         case "Open":
            FileChooser chooser = new FileChooser();
            chooser.setInitialDirectory(new File("."));
            chooser.setTitle("CSV");
            
            chooser.getExtensionFilters().addAll(
               new FileChooser.ExtensionFilter[] { new FileChooser.ExtensionFilter("CSV files", new String[] { "*.csv" }),
               new FileChooser.ExtensionFilter("All Files", new String[] { "*.*" }) });
         
         
            File csvFile = chooser.showOpenDialog(stage);
            if (csvFile == null) 
            { 
               return; 
            }
            
            tfFileName.setText(csvFile.getName());
         
         
            File datFile = null;
            int index = csvFile.getName().lastIndexOf('.');
            if ((index > 0) && (index <= csvFile.getName().length() - 2))
            {
               datFile = new File(csvFile.getName().substring(0, index) + ".dat");
            }
            
            taData.setText("");
            BaseballRileyA.Handshake results = convertCSVtoDAT(csvFile, datFile);
         
         
            tfRecordsIn.setText("" + results.getRecordsIn());
            tfRecordsOut.setText("" + results.getRecordsOut());
         
            readDat(datFile);           
            break;
      
         //occurs when the button is not registered on setOnAction
         default:
            System.out.println("Button not detected");
            break;
      }    
   }

      //A handshake process between 
   private BaseballRileyA.Handshake convertCSVtoDAT(File csvFile, File datFile)
   {
      
      DataOutputStream dos = null;
      BufferedReader br = null;
      int inCount = 0;
      int outCount = 0;
    
      String errorMsg = "ERROR MESSAGE";
      String errorItem = "ERROR ITEM";
    
   
      try
      {
         br = new BufferedReader(new java.io.FileReader(csvFile));
      
         dos = new DataOutputStream(new java.io.BufferedOutputStream(new java.io.FileOutputStream(datFile)));
      
      
         br.readLine();
         inCount++;
      
         String line;
      
         while ((line = br.readLine()) != null) {
            inCount++;
            errorMsg = "Error found in: " + line + "\n";
         
            if (line.trim().length() != 0)
            {
            
               String[] ary = line.split(",");
               try
               {
                  String nameF = ary[0].trim();
                  String nameL = ary[1].trim();
               
                  errorItem = "Birthday";
                  int bDay = Integer.parseInt(ary[2]);
               
                  errorItem = "Birthmonth";
                  int bMon = Integer.parseInt(ary[3]);
               
                  errorItem = "Birthyear";
                  int bYear = Integer.parseInt(ary[4]);
               
                  errorItem = "Weight";
                  int weight = Integer.parseInt(ary[5]);
               
                  errorItem = "Height";
                  double height = Double.parseDouble(ary[6]);
               }
               catch (NumberFormatException nfe)
               {
                  double height;
                  taData.appendText(errorMsg + "/nERROR:" + errorItem);
               }
            
               double height = 0;
               int weight = 0;
               int bYear = 0;
               int bMon = 0 ; 
               int bDay = 0; 
               String nameL = "";
               String nameF = "";
               dos.writeUTF(nameF);
               dos.writeUTF(nameL);
               dos.writeInt(bDay);
               dos.writeInt(bMon);
               dos.writeInt(bYear);
               dos.writeInt(weight);
               dos.writeDouble(height);
            
               outCount++;
            }
         }
      
      
      
      
         dos.flush();
         dos.close();
         br.close();
      
         return new BaseballRileyA.Handshake(inCount, outCount);
      }
      catch (FileNotFoundException fnfe) {
         Alert alert = new Alert(javafx.scene.control.Alert.AlertType.ERROR, "File " + csvFile.getName() + " does not exist. Exiting.", new ButtonType[0]);
         alert.setHeaderText("No File");
         alert.showAndWait();
         System.exit(2);
      }
      catch (IOException ioe) {
         Alert alert = new Alert(javafx.scene.control.Alert.AlertType.ERROR, "" + ioe, new ButtonType[0]);
         alert.setHeaderText("IO Exception");
         alert.showAndWait();
         System.exit(3);
      }
      return new BaseballRileyA.Handshake(0, 0);
   }
   class Handshake {
      private int _in;
      private int _out;
    
      public Handshake(int in, int out) 
      {
         _in = in; _out = out; 
      }
      public int getRecordsIn() 
      { 
         return _in; 
      }
      public int getRecordsOut() 
      { 
         return _out;
      }
   }
   private void readDat(File datFile)
   {
      try {
         DataInputStream dis = new DataInputStream(new java.io.BufferedInputStream(new java.io.FileInputStream(datFile)));
      
      
      
         taData.appendText(String.format("\n\n%-20s %10s %7s %7s\n\n", new Object[] { "First & Last name", "Birthdate", "Weight", "Height" }));
      
         try
         {
            for (;;)
            {
               String nameF = dis.readUTF();
               String nameL = dis.readUTF();
               int bDay = dis.readInt();
               int bMon = dis.readInt();
               int bYear = dis.readInt();
               int weight = dis.readInt();
               double height = dis.readDouble();
               taData.appendText(String.format("%-20s %10s %7s %7s\n", new Object[] { nameF + " " + nameL, bMon + "/" + bDay + "/" + bYear, 
                  Integer.valueOf(weight), Double.valueOf(height) }));
            }
         }
         catch (java.io.EOFException eof)
         {
            dis.close();
         }
      
         return;
      }
      catch (FileNotFoundException fnfe)
      {
         System.out.println("File " + datFile.getName() + " does not exist. Exiting.");
         System.exit(2);
      }
      catch (IOException ioe) {
         System.out.println(ioe.toString());
         ioe.printStackTrace();
      }
   }
}

   

   


