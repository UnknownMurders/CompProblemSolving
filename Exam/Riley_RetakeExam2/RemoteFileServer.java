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

public class RemoteFileServer extends Application implements EventHandler<ActionEvent> {
	
   // GUI Attributes
   private Stage stage;
   private Scene scene;
   private VBox root = new VBox(8);
   private Button btnStart = new Button("Start");
   private TextArea taLog = new TextArea();

	// Setting up the Server Port
   private ServerSocket sSocket = null;
   public static final int SERVER_PORT = 50000;
   private PrintWriter pwt = null;
   private Scanner scn = null;

   public static void main(String[] args) {
      launch(args);
   }

   /** 
   * There is one attribute in the agrument and pass them to the GUI constructor 
   * @param stage
   * - Setting up GUI with attributes
   **/
   public void start(Stage _stage) {
   
      stage = _stage;
      stage.setTitle("Remote File Server"); // Title of the GUI
   
   	// TOP ONLY
      FlowPane fpTop = new FlowPane(8, 8);
      fpTop.getChildren().addAll(btnStart);
      fpTop.setAlignment(Pos.TOP_RIGHT);
      root.getChildren().addAll(fpTop);
   
   	// BOTTOM ONLY
      FlowPane fpBottom = new FlowPane(8, 8);
      fpBottom.getChildren().addAll(taLog);
      taLog.setWrapText(true);
      root.getChildren().addAll(fpBottom);
      
      // Start Button to get worked
      btnStart.setOnAction(this);
   
      // Setting the size of Remote File Server's GUI
      scene = new Scene(root, 550, 200);
      stage.setScene(scene);
      stage.show();
   }

	/** 
   * Button handler
   * - Start Button
   * - Stop Button
   */
   public void handle(ActionEvent evt) {
      String label = ((Button)evt.getSource()).getText();
      switch(label) {
         case "Start":
            doStart();
            break;
         case "Stop":
            doStop();
            break;
      }
   }

   // To display the LOG of the "Localhost" when it is connected from Client
   public void log(String message) {
      Platform.runLater(
         new Runnable() {
            public void run() {
               taLog.appendText("<LOCALHOST: " + SERVER_PORT + "> " + message + "\n"); // LocalHost with Server Port from Client
            }
         });
   }

	
   // Setting up a new thread for Connection  
   class Connection extends Thread {
   
      private Socket cSocket = null; // Set as an empty for Socket
   
      public void run() {
         try {
            sSocket = new ServerSocket(SERVER_PORT); // Set the Server Port  
            log("SOCKET: " + SERVER_PORT); // Display the Server Port 
         }
         
         // To catch the error if IO error is appeared
         catch(IOException ioe) {
            log("IOException: \n" + ioe.getMessage());
            ioe.printStackTrace();
            return;
         }
      
         // Set a While loop, which is listening for connection
         while(true) {
            try {
               cSocket = sSocket.accept(); // Wait and Accept Socket
            }
            
            // To catch the error if IO error is appeared
            catch(IOException ioe) {
               log("IOException: \n" + ioe.getMessage());
               ioe.printStackTrace();
               return;
            }
            Client ct = new Client(cSocket);  // Client Connection will be merged with thread
            ct.start(); // Thread Starts
         }
      }
   }

   class Client extends Thread {
   
      private Socket cSocket = null; // Set it as an empty
      
      /** 
      * There is one attribute in the agrument and pass them to the GUI constructor 
      * @param cSocket
      **/
      public Client(Socket cSocket) {
         this.cSocket = cSocket;
      }
   
      public void run() {
         log("CLIENT IS CONNECTED..."); // To display when it is connected
      
         try {
            scn = new Scanner(new ObjectInputStream(cSocket.getInputStream()));
            pwt = new PrintWriter(new ObjectOutputStream(cSocket.getOutputStream()));
         }
         
         // To catch the error if IO error is appeared
         catch(IOException ioe) {
            log("IOException: \n" + ioe.getMessage());
            ioe.printStackTrace();
            return;
         }
      
         /** 
         * While Loop - Button handler
         * - List Button
         * - Upload Button
         * - Download
         */
         while(scn.hasNextLine()) {
         
            String answer = scn.nextLine();
         
            switch(answer.toUpperCase()){
               case "LIST":
                  doList();
                  break;
               case "UPLOAD":
                  doUpload();
                  break;
               case "DOWNLOAD":
                  doDownload();
                  break;
            }
         }
         scn.close(); // Close Scanner
         pwt.close(); // Close Writer
      }
   }

   // Start Method 
   public void doStart() {
      Connection ct = new Connection();
      ct.start(); // Thread starts
   
   	// Change button to stop
      btnStart.setText("Stop"); // To display Stop Button
   }

   // Stop Method
   public void doStop() {
      try {
         sSocket.close(); // Close socket
         log("CONNECTION CLOSED"); // To display when connection is closed
      }
      
      // To catch the error if IO error is appeared
      catch(IOException ioe) {
         log("IOException: \n" + ioe.getMessage());
         ioe.printStackTrace();
         return;
      }
   
      btnStart.setText("Start"); // To display Start Button
   }

   // List Method
   public void doList(){
      log("Command: LIST");
      String currentDirectory = System.getProperty("user.dir");
      log("LIST of " + currentDirectory); // To display the list of current directory
   
      pwt.println(currentDirectory); // Path back to the Client
      pwt.flush(); // Clear
   }

   // Upload Method
   public void doUpload(){
      log("Command: UPLOAD");
   
      Platform.runLater(
         new Runnable() {
            public void run() {
               TextInputDialog dialog = new TextInputDialog(); // Open the dialog
               dialog.setHeaderText("Remote File Name?"); // Ask what file name is. 
               dialog.setContentText("Enter remote file name: "); // Enter File Name
               Optional<String> result = dialog.showAndWait(); // Words into result
            
               if(result.isPresent()) {
               
                  String newFileName = result.get(); // Received the word from input
               
                  FileChooser fileChooser = new FileChooser(); // Open the File Chooser dialog
                  fileChooser.setTitle("Choose local file for upload"); 
                  fileChooser.getExtensionFilters().addAll(
                     			new ExtensionFilter("Text Files", "*.txt"), // Any file that must be .txt
                     			new ExtensionFilter("CSV", "*.csv"), // Any file that must be .csv
                     			new ExtensionFilter("All Files", "*.*") // Any file that must be .
                     	 );
                  File selectedFile = fileChooser.showOpenDialog(stage);
               
                  if(selectedFile == null){
                     Alert alert = new Alert(AlertType.ERROR, "No local file chosen"); // To display if file is not selected.
                     alert.showAndWait();
                  }
                        
                  try {
                  			
                     log("Uploading..." + newFileName); // Uploading with File Name
                     pwt.println("Uploading..." + newFileName); // Uploading with File Name
                     pwt.flush(); // Clear
                  
                     scn = new Scanner(new FileInputStream(selectedFile)); // Selected File Name
                  
                     File newFile = new File(newFileName); // New File
                     FileOutputStream fos = new FileOutputStream(newFile);
                     PrintWriter p = new PrintWriter(fos);
                  
                     while(scn.hasNextLine()) {
                        String line = scn.nextLine(); // To read
                        p.println(line); // To Write
                     }
                  
                     p.flush(); // Close 
                     p.close(); // Close
                  
                     log("UPLOAD FILE " + System.getProperty("user.dir") + "/" + newFile); 
                     pwt.println("FILE " + System.getProperty("user.dir") + "/" + newFile);
                     pwt.flush();
                  }
                  
                  // To catch the error if File is not founded 
                  catch(FileNotFoundException fnfe){
                     Alert alert = new Alert(AlertType.ERROR, "File Not found");
                     alert.showAndWait();
                     log("FileNotFoundException: " + fnfe);
                  }
               }
            }
         });
   }

   public void doDownload(){
      log("Command: DOWNLOAD");
   
      Platform.runLater(
         new Runnable() {
            public void run() {
               TextInputDialog dialog = new TextInputDialog(); // A dialog
               dialog.setHeaderText("Remote File Name?"); // Ask what File Name is
               dialog.setContentText("Enter remote file name: "); // Enter File Name
               Optional<String> result = dialog.showAndWait(); // Answer into result
            
               if(result.isPresent()) { // Carry the answer in the input
               
                  String fileName = result.get(); // Put answer in the String fileName
               
                  FileChooser fileChooser = new FileChooser(); // FileChooser for all files
                  fileChooser.setTitle("Choose local file for Download"); // Requests user to choose local file for download
               
                  File selectedFile = fileChooser.showSaveDialog(stage); // Save the dialog
               
                  if(selectedFile == null){ // Empty
                     Alert alert = new Alert(AlertType.ERROR, "No local file chosen"); // To display if file is not chosen                     alert.showAndWait();
                  
                     pwt.println("DOWNLOAD - NO FILE SELECTED"); // Display if file is not selected 
                     pwt.flush(); // Clear
                     return;
                  }
               
                  try {
                  
                     log("Downloading..." + fileName); // Being recorded.
                     pwt.println("Downloading..." + fileName); // Display if file is being downloaded.
                     pwt.flush();
                  
                     File newFile = new File(System.getProperty("user.dir") + "/" + fileName); // New file when it is given name
                     PrintWriter filePWT = new PrintWriter(new FileOutputStream(newFile)); // New file when it is given name
                  
                     log("DOWNLOAD " + System.getProperty("user.dir") + "/" + fileName + "\n"); // Being recorded.
                     pwt.println("File " + System.getProperty("user.dir") + "/" + fileName); // To display if file is selected.
                     pwt.flush(); // Clear
                  }
                  
                  //To catch the ERROR if File is not founded.
                  catch(FileNotFoundException fnfe) {
                     Alert alert = new Alert(AlertType.ERROR, "File Not found");
                     alert.showAndWait();
                     log("FileNotFoundException: " + fnfe);
                  }
               }
               		
               else {
                  pwt.println("DOWNLOAD - CANCELLED"); // If it is cancelled, download stopped. 
                  pwt.flush(); // Clear
                  log("DOWNLOAD - CANCELLED"); // If it is cancelled, download stopped. (Displayed)
               }
            }
         });
   }
}