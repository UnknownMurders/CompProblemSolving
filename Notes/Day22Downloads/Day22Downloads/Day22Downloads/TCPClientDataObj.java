import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import java.io.Serializable; 

import java.net.*;
import java.io.*;
import java.util.*;

/**
 * TCP Client
 * Fairly complete client. Connects / disconnects / sends a message and awaits a reply
 * Connect/disconnect button allows multiple interactions
 * @author Pete Lutz
 * @version 9-16-2017
 */
public class TCPClientDataObj extends Application implements EventHandler<ActionEvent> {
   // Window Attributes
   private Stage stage;
   private Scene scene;
   private VBox root;
   
   // Components - TOP
   // The TOP will itself be GridPane ... we will use Row1 and Row2 of the this Grid
   // These are for Row1
   private Label lblServerIP = new Label("Server Name or IP: ");
   private TextField tfServerIP = new TextField();
   private Button btnConnect = new Button("Connect");

   // These will be in Row2
   private Label lblFile = new Label("File: ");
   private TextField tfFile = new TextField("test.txt");
   private Button btnSend = new Button("Send");
   
   // Compoonents - BOT
   // The BOT will be a FlowPane
   private Label lblLog = new Label("Log:");
   private TextArea taLog = new TextArea();

   // IO attributes
   private DataInputStream in = null;
   private DataOutputStream out = null;

   // OTHER attributes
   public static final int SERVER_PORT = 49153;
   private Socket socket = null;

   /**
    * main program 
    */
   public static void main(String[] args) {
      launch(args);
   }

   /**
    * start method ... draw and set up GUI
    */
   public void start(Stage _stage) {
      stage = _stage;
      stage.setTitle("TCP Client1");
      stage.setOnCloseRequest(
         new EventHandler<WindowEvent>() {
            public void handle(WindowEvent evt) { 
               System.exit(0); 
            }
         } );
      stage.setResizable(false);
      root = new VBox(8);
      
      // ROW1 ... FlowPane ... 
      FlowPane fpRow1 = new FlowPane(8,8);
      fpRow1.setAlignment(Pos.CENTER);
      tfServerIP.setPrefColumnCount(15);
      fpRow1.getChildren().addAll(lblServerIP, tfServerIP, btnConnect);
      root.getChildren().add(fpRow1);
   
      // ROW2 - Textfield for a sentence to send and Send button
      FlowPane fpRow2 = new FlowPane(8,8);
      fpRow2.setAlignment(Pos.CENTER);
      tfFile.setPrefColumnCount(24);
      fpRow2.getChildren().addAll(lblFile, tfFile, btnSend);
         
         // tfSentence and btnSend disabled until connected
      tfFile.setDisable(true);
      btnSend.setDisable(true);
      root.getChildren().add(fpRow2);
      
      // LOG ... Label + text area
      FlowPane fpLog = new FlowPane();
      fpLog.setAlignment(Pos.CENTER);
      taLog.setPrefColumnCount(35);
      taLog.setPrefRowCount(10);
      fpLog.getChildren().addAll(lblLog, taLog);
      root.getChildren().add(fpLog);
      
      // Listen for the buttons
      btnConnect.setOnAction(this);
      btnSend.setOnAction(this);
   
      scene = new Scene(root, 475, 300);
      stage.setScene(scene);
      stage.show();      
   }


   /** 
    * Button dispatcher
    */
   public void handle(ActionEvent ae) {
      String label = ((Button)ae.getSource()).getText();
      switch(label) {
         case "Connect":
            doConnect();
            break;
         case "Disconnect":
            doDisconnect();
            break;
         case "Send":
            doSend();
            break;
      }
   }

   /**
    * doConnect - Connect button
    */
   private void doConnect() {
      try {
         // Connect to server and set up two streams, a Scanner for input from the
         // server and a PrintWriter for output to the server
         socket = new Socket(tfServerIP.getText(), SERVER_PORT);
         in = new DataInputStream(socket.getInputStream());
         out = new DataOutputStream(socket.getOutputStream());
      }
      catch(IOException ioe) {
         taLog.appendText("IO Exception: " + ioe + "\n");
         return;
      }
      taLog.appendText("Connected!\n");
      btnConnect.setText("Disconnect");
      
      // Enable text field and Send button
      tfFile.setDisable(false);
      btnSend.setDisable(false);
   }

   /**
    * doDisconnect - Disconnect button'
    */
   private void doDisconnect() {
      try {
         // Close the socket and streams
         socket.close();
         in.close();
         out.close();
      }
      catch(IOException ioe) {
         taLog.appendText("IO Exception: " + ioe + "\n");
         return;
      }
      btnConnect.setText("Connect");
      
      // Disable text field and Send button
      tfFile.setDisable(true);
      btnSend.setDisable(true);
   }

   /**
    * doSend - Send button'
    */
   private void doSend() {
      ArrayList al = new ArrayList();
   
      // Open the specified file
      // Determine how many bytes are in the file
      // Send the number of bytes to the server
      // Wait to receive the number of bytes back from the server
      // Send the file one byte at a time to the server
      // After sending all bytes, wait for the server to respond with a String
      
      try {
         taLog.appendText("Opening file: " + tfFile.getText() + "\n");
         File myFile = new File(tfFile.getText());
         DataInputStream fin = new DataInputStream(new FileInputStream(myFile));
         long fileLength = myFile.length();
         out.writeLong(fileLength);
         
         taLog.appendText("Preparing to send file\n");
         taLog.appendText("File is " + fileLength + " bytes\n");
         
         in.readLong();
         
         taLog.appendText("Sending file\n");
         
         for(long i = 0; i < fileLength; i++) {
            byte b = fin.readByte();
            out.writeByte(b);
            al.add(fileLength);
            taLog.appendText("Sending byte " + (i+1) + ": " + b + "\n");
         }
         
         taLog.appendText("Done sending file\n");
         in.readUTF();  
         taLog.appendText("Server has received the file\n");
         
         out.flush();
         fin.close();
      }
      catch(Exception e) {
         taLog.appendText("Error during transmission: " + e + "\n");
      }
      
      taLog.appendText("Disconnected from server!\n");
      doDisconnect();
   }

}