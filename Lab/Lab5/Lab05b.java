import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*; 
import java.lang.Object.*;
import java.util.ArrayList;
import javax.swing.text.DefaultCaret;
import java.io.*;
import javax.swing.text.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.io.*;

/*
  NAME: Edward Riley
  TEMP-PROF: Erik Golen
  COURSE: Computational Problem Solving in Domain II
  DATE: 10/11/2018
*/
 
public class Lab05b extends Application implements EventHandler<ActionEvent> {
   // Window attributes
   Stage stage;
   Scene scene;
   
   // GUI components
   private TextField tfCity = new TextField();
   private TextField tfRecord = new TextField();
   private TextField tfSize = new TextField();
   private TextArea taOutput = new TextArea();
   private TextArea taInput = new TextArea();
   private Button btnFind = new Button("Find");
   
   static ArrayList<String> cityList = new ArrayList<String>();
   static ArrayList<String> zipCityStateList = new ArrayList<String>();
   static ArrayList<Thread> processorThread = new ArrayList<Thread>();
   
   FileThread runFile;
   static long countInDat = 0;
   static long byteCount = 0;
   
   public static void main(String[] args) {
      launch(args);
      
      if(args.length != 0)
      {
        
         for(int i = 0; i < args.length; i++){
            if(cityList.contains(args[i])){
               System.out.printf("%-15s was found at position %,d%n", args[i], cityList.indexOf(args[i]));
            }
            else{
               System.out.printf("%-15s was NOT found.\n", args[i]);
            }
         }
      }
   }
   
   /** constructor */
   public void start(Stage _stage) {
      // setup window
      stage = _stage;
      stage.setTitle("Data Accumulator");
      VBox root = new VBox(8);
      
      // Textfields in the Top
      FlowPane fpTop = new FlowPane(8,8);
      fpTop.setAlignment(Pos.CENTER);
      fpTop.getChildren().addAll(new Label("Cities: "), tfCity,
            new Label("Records: "), tfRecord,
            new Label("Object Size: "), tfSize);
   
      tfCity.setEditable(false);
      tfRecord.setEditable(false);
      tfSize.setEditable(false);
      root.getChildren().add(fpTop);
      
      // Two text areas in the Center
      FlowPane fpCenter = new FlowPane(8,8);
      fpCenter.setAlignment(Pos.CENTER);
      fpCenter.getChildren().addAll(taOutput, taInput);
         
         // Use monospaced font
      Font newFont = Font.font("MONOSPACED");
      taOutput.setFont(newFont);
      taInput.setFont(newFont);
      taOutput.setPrefHeight(250);
      taOutput.setPrefWidth(400);
      taInput.setPrefHeight(250);
      taInput.setPrefWidth(400);
      root.getChildren().add(fpCenter);
      btnFind.setOnAction(this);
   
      // Find button in the Bottom
      FlowPane fpBot = new FlowPane(8,8);
      fpBot.setAlignment(Pos.CENTER);
      fpBot.getChildren().add(btnFind);
      //btnFind.setDisable(false);
      root.getChildren().add(fpBot);
      
      String nFile = "Lab5FileN.dat";
      for(int i = 1; i < 8; i++){
         String updateFile = nFile.replace("N", Integer.toString(i));
         runFile = new FileThread(updateFile);
         processorThread.add(runFile);
         runFile.start();
      }
      for(Thread thread : processorThread){
         try{
            thread.join();        
         }
         catch(InterruptedException ie){}
      }
   
      tfCity.setText(String.valueOf(cityList.size()));
      tfRecord.setText(String.valueOf(countInDat));
      tfSize.setText(String.valueOf(byteCount));
   
      scene = new Scene(root, 900, 325);
      stage.setScene(scene);
      stage.show();
   }
   
   class FileThread extends Thread{
      File file;
      int zip;
      String city, state;
      int countByte = 0;
   
      public FileThread(String processorThread){
         file = new File(processorThread);
      }
     
      public void run(){
         int count = 0;
         try
         {
            DataInputStream dis = new DataInputStream(new FileInputStream(file));
            DataOutputStream dos = new DataOutputStream(new FileOutputStream("ZipCityState.dat", true));
         
            while(dis.available() > 0){
               zip = dis.readInt();
               city = dis.readUTF();
               state = dis.readUTF();
               double lat = dis.readDouble();
               double longit = dis.readDouble();
               int time = dis.readInt();
               int dayLight = dis.readInt();
              
               synchronized(processorThread){
                  dos.writeInt(zip);
                  dos.writeUTF(city);
                  dos.writeUTF(state);
                  count++;
                  countInDat++;;
               }
              
               synchronized(processorThread){
                  addCityArray(city);
               }
              
            }
            dis.close();
            dos.close();
         }
         catch(IOException e){
            System.err.println(e);
         }
      
         taOutput.appendText(String.format("File %-13s completed, record count is %,d\n", file.getName() ,count));
       
      
         try{
            File obFile = new File("CityArrayList.ob");
            FileOutputStream fos = new FileOutputStream(obFile);
            ObjectOutputStream  byteWrite = new ObjectOutputStream(fos);
            byteWrite.writeObject(cityList);
            byteCount = obFile.length();
         }
         catch(Exception e){}
      
      }
     
      public synchronized void addCityArray(String addCity){
         try{
            cityList.add(addCity);
         }
         catch(Exception ie){} 
      }
   }
   
   /** Button handler */
   public void handle(ActionEvent ae) {
   
      Button gotClicked = (Button)ae.getSource();      
         
      switch (gotClicked.getText())
      {
         case "Find":         
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("City Look Up");
            dialog.setHeaderText("City Look Up");
           // String result = dialog.getResult();
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
               System.out.println("City Entered: " + result.get());
            
            }
         
            if(cityList.contains(result.get())){
               taInput.appendText(String.format("%-15s was found at position %,d%n", result.get(), cityList.indexOf(result.get())));
            }
            else{
               taInput.appendText(String.format("%-15s was NOT found.\n", result.get()));
                    
            }
               
            
            break;
      }
   }
   
}
