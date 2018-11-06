import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Class TCPServer ...
 * Stub of a TCP Server ... goes with TCP Client ... connect only
 * @author Pete Lutz
 * @version 9-16-2017
 */
public class TCPServerObject extends Application {
   // Window attributes
   private Stage stage;
   private Scene scene;
   private VBox root;
   
   // GUI Components
   public Label lblLog = new Label("Log:");
   public TextArea taLog = new TextArea();
   
   // Socket stuff
   private ServerSocket sSocket = null;
   public static final int SERVER_PORT = 49153;
   
   /**
    * main program
    */
   public static void main(String[] args) {
      launch(args);
   }
   
   /**
    * Launch, draw and set up GUI
    * Do server stuff
    */
   public void start(Stage _stage) {
      // Window setup
      stage = _stage;
      stage.setTitle("TCPServer1");
      stage.setOnCloseRequest(
         new EventHandler<WindowEvent>() {
            public void handle(WindowEvent evt) { System.exit(0); }
         } );
      stage.setResizable(false);
      root = new VBox(8);
   
      // TOP components
      FlowPane fpBot = new FlowPane(8,8);
      fpBot.setAlignment(Pos.CENTER);
      taLog.setPrefRowCount(10);
      taLog.setPrefColumnCount(35);
      fpBot.getChildren().addAll(lblLog, taLog);
      root.getChildren().add(fpBot);
      
      // Show window
      scene = new Scene(root, 475, 200);
      stage.setScene(scene);
      stage.show();      
      
      // Do server work in a thread
      Thread t = 
         new Thread() {
            public void run() { doServerWork(); }
         };
      t.start();
   }
   
   /** doServerWork - does the basic non-GUI work of the server */
   public void doServerWork() {
      // Claim the port and start listening for new connections
      try {
         sSocket = new ServerSocket(SERVER_PORT);
      }
      catch(IOException ioe) {
         log("IO Exception (1): "+ ioe + "\n");
         return;
      }
   
      // Socket for comm with client      
      Socket cSocket = null;
      
      while(true) {
         try {
         // Wait for a connection
            cSocket = sSocket.accept();
         }
         catch(IOException ioe) {
            log("IO Exception (2): "+ ioe + "\n");
            return;
         }
      
      // No real processing yet
         log("Client connected!\n");
         ObjectInputStream in = null;
         ObjectOutputStream out = null;
         
         try {
            out = new ObjectOutputStream(cSocket.getOutputStream());
            in = new ObjectInputStream(cSocket.getInputStream());
         }
         catch(Exception e) {
            log("Exception opening streams: " + e + "]n");
         }
      
         try {
            long fileLength = in.readLong();
            log("Receiving a " + fileLength + " byte file\n");
            
            // send the file length back to the client and flush the stream
            out.writeLong(fileLength);
            out.flush();
            
            // receive the Object from the client
            // make sure the Object IS-A File
            // write the File object to a file byte by byte because it's a text file
            Object obj = in.readObject();
            
            if(obj instanceof File) {
               File rcvFile = (File) obj;
               log("Received a File object of length " + rcvFile.length() + " bytes.\n");
               
               FileInputStream fis = new FileInputStream(rcvFile);
               FileOutputStream fos = new FileOutputStream("output.txt");
               fileLength = rcvFile.length();
               
               // file writing loop goes here
               for(long i = 0; i < fileLength; i++) {
                  int b = fis.read();
                  fos.write(b);
               }
               
               fis.close();
               fos.close();
            }
            else {
               throw new Exception();
            }
            
            out.writeUTF("DONE");
            log("Done receiving file from client\n");
            out.flush();
            
            // receive a NameTag object from the client as a generic Object
            // make sure the Object is of type NameTag
            // cast Object as a NameTag object and print its contents to the log
            // send back a "DONE" String to the client
            obj = in.readObject();
            
            if(obj instanceof NameTag) {
               NameTag tag = (NameTag) obj;
               log("Received NameTag with contents:\n");
               log(tag + "\n");
            }
            else {
               throw new Exception();
            }
            
            out.writeUTF("DONE");
            out.flush();
         }
         catch(Exception e) {
            log("Error during transmission: " + e + "\n");
         }
      
      
         try {
            cSocket.close();
            in.close();
            out.close();
         }
         catch(Exception e) {
            log("Exception closing streams: " + e + "\n");
         } 
      
         log("Client disconnected!\n"); 
      }          
   } 
   
   // utility method "log" to log a message in a thread safe manner
   private void log(String message) {
      Platform.runLater(
         new Runnable() {
            public void run() {
               taLog.appendText(message);
            }
         } );
   } // of log   
}