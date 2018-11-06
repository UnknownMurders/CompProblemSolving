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

/**
 * TCPClient - simple tcp client program
 * together with TCPServer only does connect ... not even complete disconnect.
 * @author Pete Lutz
 * @version 9-16-2017
 */
public class TCPClient extends Application implements EventHandler<ActionEvent> {
   // Window attributes
   private Stage stage;
   private Scene scene;
   private VBox root;
   
   // Components - TOP
   private Label lblServerIP = new Label("Server Name or IP: ");
   private Label lblSentence = new Label("Sentence: " ); 
   private TextField tfServerIP = new TextField();
   private TextField tfSentence = new TextField();
   private Button btnConnect = new Button("Connect");
   private Button btnSend = new Button("Send");
   DataInputStream in = null;
   DataOutputStream out = null;
   
   // Components - BOTTOM
   private Label lblLog = new Label("Log:");
   private TextArea taLog = new TextArea();
   
   // Other attributes
   public static final int SERVER_PORT = 49153;
   private Socket socket = null;
    
   /**
    * main program 
    */
   public static void main(String[] args) {
      launch(args);
   }
   
   /**
    * launch ... draw and set up GUI
    */
   public void start(Stage _stage) {
      stage = _stage;
      stage.setTitle("TCP Client");
      stage.setOnCloseRequest(
         new EventHandler<WindowEvent>() {
            public void handle(WindowEvent evt) { System.exit(0); }
         } );
      stage.setResizable(false);
      root = new VBox(8);
      
      // TOP ... label, text field, button
      FlowPane fpTop1 = new FlowPane(8,8);
      fpTop1.setAlignment(Pos.CENTER);
      fpTop1.getChildren().addAll(lblServerIP, tfServerIP);
      root.getChildren().add(fpTop1);
      
      FlowPane fpTop2 = new FlowPane(8,8);
      fpTop2.setAlignment(Pos.CENTER);
      fpTop2.getChildren().addAll(lblSentence, tfSentence);
      root.getChildren().add(fpTop2);
      
      FlowPane fpTop3 = new FlowPane(8,8);
      fpTop3.setAlignment(Pos.CENTER);
      btnSend.setDisable(true);
      tfSentence.setDisable(true);
      fpTop3.getChildren().addAll(btnConnect, btnSend);
      root.getChildren().add(fpTop3);
      
      // BOTTOM ... Label + text area
      FlowPane fpBot = new FlowPane(8,8);
      fpBot.setAlignment(Pos.CENTER);
      taLog.setPrefRowCount(10);
      taLog.setPrefColumnCount(35);
      fpBot.getChildren().addAll(lblLog, taLog);
      root.getChildren().add(fpBot);
      
      // Listen for the button
      btnConnect.setOnAction(this);
      btnSend.setOnAction(this);
   
      // Show window
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
         case "Send":
            doSend();
            break;
         case "Connect":
            doConnect();
            break;
         case "Disconnect":
            doDisconnect();
            break;
      }
   }
   
   /**
    * doConnect - Connect button
    */
   private void doConnect() {
      try {
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
      tfSentence.setDisable(false);
      btnSend.setDisable(false);
   }
   
   /**
    * doDisconnect - Disconnect button'
    */
   private void doDisconnect() {
      try {
         socket.close();
      }
      catch(IOException ioe) {
         taLog.appendText("IO Exception: " + ioe + "\n");
         return;
      }
      
      taLog.appendText("Disconnected!\n");
      tfSentence.setDisable(true);
      btnSend.setDisable(true);
      btnConnect.setText("Connect");
   }
   
   private void doSend() 
   {
      try 
      {
         out.writeUTF(tfSentence.getText());
         taLog.appendText("Sent: " + tfSentence.getText() + "\n");
         tfSentence.setText("");
         taLog.appendText("Waiting for a response from server...\n");
         String reply = in.readUTF();
         taLog.appendText("Received a Response: " + reply + "");
         
      }
      catch (Exception e)
      {
      
      }
   }

}