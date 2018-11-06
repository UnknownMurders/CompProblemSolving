/*
NAME: Edward Riley 
PARTNER: Trent Jacobson
PROFESSOR: Erik Golen
COURSE: Computational Problem Solving in Domain II
DATE: 11/5/2018
*/

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import javafx.scene.control.Dialog;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.util.*;
import java.net.*;
import java.io.*;
import java.io.Serializable; 

public class RemoteFileBrowser extends Application implements EventHandler<ActionEvent> {
   
   // GUI Attributes
   private Stage stage;
   private Scene scene;
   private VBox root = new VBox(8);

   // A TextArea
   private TextArea taLog = new TextArea();

   // A Server Label
   private Label lblServer = new Label("Server: ");

   // A textfield
   private TextField tfServer = new TextField();

   // All buttons
   private Button btnConnect = new Button("Connect");
   private Button btnList = new Button("List");
   private Button btnUpload = new Button("Upload");
   private Button btnDownload = new Button("Download");
   
   // Setting up to connect the Server
   private PrintWriter pwt = null;
   private Scanner scn = null;
   public static final int SERVER_PORT = 50000;
   private Socket socket = null;
   private String currentDir = "";

   public static void main(String[] args){
      launch(args);
   }

   /** 
   * There is one attribute in the agrument and pass them to the GUI constructor 
   * @param stage
   * - Setting up GUI with attributes
   **/
   public void start(Stage _stage){
      stage = _stage;
      stage.setTitle("Remote File Browser");
   
      // Top FLowPane
      FlowPane fpTop = new FlowPane(8,8);
      fpTop.setAlignment(Pos.CENTER);
      fpTop.getChildren().addAll(lblServer,tfServer,btnConnect);
      root.getChildren().add(fpTop);
   
      // Middle FlowPane
      FlowPane fpBottom = new FlowPane(8,8);
      fpBottom.setAlignment(Pos.CENTER);
      fpBottom.getChildren().addAll(btnList,btnUpload,btnDownload);
      root.getChildren().add(fpBottom);
   
      taLog.setWrapText(true);
      root.getChildren().add(taLog);
   
      // Setting Connect Button to have "Connect" OR "Disconnect" ability
      btnConnect.setOnAction(
         new EventHandler<ActionEvent>(){
            public void handle(ActionEvent evt){
               switch(btnConnect.getText()){
                  case "Connect":
                     doConnect();
                     break;
                  case "Disconnect":
                     doDisconnect();
                     break;
               }
            }
         });
   
      // To get all buttons working
      btnList.setOnAction(this);
      btnUpload.setOnAction(this);
      btnDownload.setOnAction(this);

      // To disable all buttons
      btnList.setDisable(true);
      btnDownload.setDisable(true);
      btnUpload.setDisable(true);
      btnDownload.setDisable(true);
   
      // Setting the size of Remote File Browser's GUI
      scene = new Scene(root, 600,250);
      stage.setScene(scene);
      stage.setX(100);
      stage.setY(100);
      stage.show();
   }
   
   /** 
   * Button handler
   * - List Button
   * - Upload Button
   * - Download
   */
   public void handle(ActionEvent ae) {
    
      Button gotClicked = (Button)ae.getSource();      
         
      switch (gotClicked.getText()) {
         case "List": 
            doList();
            break;
         case "Upload": 
            doUpload();
            break;
         case "Download": 
            doDownload();
            break;
      } 
   }
   
   
      public void doConnect(){
      try {
         // Connect to server
         // Open Scanner and a PrintWriter for output to the server
         socket = new Socket(tfServer.getText(), SERVER_PORT);
         scn = new Scanner(new InputStreamReader(socket.getInputStream()));
         pwt = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
      }
      catch(IOException ioe) {
         taLog.appendText("IO Exception: " + ioe + "\n");
         return;
      }
   
      // log information
      taLog.appendText("Connected!\n");
   
   
      // change button to "disconnect"
      btnConnect.setText("Disconnect");
   
   
      // Enable text field and Send button
      btnList.setDisable(false);
      btnDownload.setDisable(false);
      btnUpload.setDisable(false);
      btnDownload.setDisable(false);
   }// end of doConnect


   public void doDisconnect(){
      try {
         // Close the socket and streams
         socket.close();
         scn.close();
         pwt.close();
      }
      catch(IOException ioe) {
         taLog.appendText("IO Exception: " + ioe + "\n");
         return;
      }
   
      // Change button back to 'Connect'
      btnConnect.setText("Connect");
   
      // disable all the buttons
      btnList.setDisable(true);
      btnDownload.setDisable(true);
      btnUpload.setDisable(true);
      btnDownload.setDisable(true);
   }// end of doDisconnect
   
   
   // List Method
   public void doList() {
      
      pwt.println("LIST"); // List
      pwt.flush(); // Clear
            
      String path = scn.nextLine(); // Each line
            
      taLog.appendText("Listing of: " + path + "\n"); // To log the line
            
      File dir = new File(path); // Set the path into File
      File[] files = dir.listFiles(); // To list the files
      for(int i = 0; i < files.length; i++){
         if(files[i].isFile()){
            taLog.appendText("F - " + files[i].getName() + "\n"); // To display "F" (File)
         }
         else if(files[i].isDirectory()){
            taLog.appendText("D - " + files[i].getName() + "\n"); // To display "D" (Directory)
         }
      }
   }
   
   // Upload Method
   public void doUpload() {
      
      pwt.println("UPLOAD"); // Upload
      pwt.flush(); // Clear
            
      String upload = scn.nextLine(); // Each line, which is readed by Remote File Server
            
      taLog.appendText(upload + "\n"); // To display the Upload
      
   }
   
   // Download Method
   public void doDownload() {
   
      pwt.println("DOWNLOAD"); // Download
      pwt.flush(); // Clear
      
      String download = scn.nextLine(); // Each line, which is readed by Remote File Server
      
      taLog.appendText(download + "\n"); // To display the Download
   }
}