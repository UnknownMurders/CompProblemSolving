/**
 * TCP Client
 * Allows client to send a color image file to the server 
 * and requests that the server return either a grayscale,
 * negative, or sepia version of the image. 
 * @author Trent Jacobson, Edward Riley, William Gardner, Melody Kabbai	
 * @version 11-9-2018
 * @Purpose FINAL PROJECT
 */

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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.nio.file.Path;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Client extends Application implements EventHandler<ActionEvent>, Serializable {
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
   private Button btnSendImage = new Button("Send Image");
   
   //Clear Button
   Button btnClear = new Button("Clear");
   
   // Compoonents - BOT
   // The BOT will be a FlowPane
   private Label lblLog = new Label("Log:");
   private TextArea taLog = new TextArea();
   
   final ToggleGroup group = new ToggleGroup();

   private RadioButton rbGreyscale = new RadioButton("Greyscale");
   private RadioButton rbNegative = new RadioButton("Negative");
   private RadioButton rbSepia = new RadioButton("Sepia");
   

   // IO attributes
   private ObjectInputStream in = null;
   private ObjectOutputStream out = null;

   // OTHER attributes
   public static final int SERVER_PORT = 50000; 
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
      rbGreyscale.setToggleGroup(group);
      rbNegative.setToggleGroup(group);
      rbSepia.setToggleGroup(group);
   
   
      stage = _stage;
      stage.setTitle("CLIENT");
      stage.setOnCloseRequest(
            new EventHandler<WindowEvent>() {
               public void handle(WindowEvent evt) { System.exit(0); }
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
      fpRow2.getChildren().addAll(rbGreyscale, rbNegative, rbSepia);
         
      FlowPane fpRow3 = new FlowPane(8,8);
      fpRow3.setAlignment(Pos.CENTER);
      fpRow3.getChildren().addAll(btnClear, btnSendImage);
      
         
      // tfSentence and btnSend disabled until connected
      btnSendImage.setDisable(false);
      root.getChildren().addAll(fpRow2, fpRow3);
      
      // LOG ... Label + text area
      FlowPane fpLog = new FlowPane();
      fpLog.setAlignment(Pos.CENTER);
      taLog.setPrefColumnCount(35);
      taLog.setPrefRowCount(10);
      fpLog.getChildren().addAll(lblLog, taLog);
      root.getChildren().add(fpLog);
      
      // Listen for the buttons
      btnConnect.setOnAction(this);
      btnSendImage.setOnAction(this);
      btnClear.setOnAction(this);
   
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
         case "Send Image":
            doSendImage();
            break;
         case "Clear":
            doClear();
            break;
      }
   }
   
   private void doClear() {
      taLog.setText("");
   }

   /**
    * doConnect - Connect button
    */
   private void doConnect() {
      try {
         // Connect to server and set up two streams, a Scanner for input from the
         // server and a PrintWriter for output to the server
         socket = new Socket(tfServerIP.getText(), SERVER_PORT);
         out = new ObjectOutputStream(socket.getOutputStream());
         in = new ObjectInputStream(socket.getInputStream());
        
         out.flush();
      }
      catch(IOException ioe) {
         taLog.appendText("IO Exception: " + ioe + "\n");
         return;
      }
      taLog.appendText("Connected!\n");
      btnConnect.setText("Disconnect");
      
      // Enable text field and Send button
      btnSendImage.setDisable(false);
   }

   /**
    * doDisconnect - Disconnect button'
    */
   private void doDisconnect() {
      try {
         // Close the socket and streams
         out.flush();
         socket.close();
         out.close();
         in.close();
         
         taLog.appendText("Disconnected!\n");
      }
      catch(IOException ioe) {
         taLog.appendText("IO Exception: " + ioe + "\n");
         return;
      }
      btnConnect.setText("Connect");
      
      // Disable text field and Send button
      btnSendImage.setDisable(true);
   }

   /**
    * doSend - Send button'
    */
   private void doSendImage() {
   
    try 
      {
      int i = 0;
      
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Pick an Image");
      FileChooser.ExtensionFilter extFilter = 
                        new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png");
      File selectedFile = fileChooser.showOpenDialog(stage);
   
      long fileLength = selectedFile.length();
      String fileName = selectedFile.getName();
      
      FileInputStream fis = null;
      long receivedDouble = 0;
      String receivedString = "";
      String extension = "";
      String radioChoice = "";
      
     // ObjectInputStream in = null;
     // ObjectOutputStream out = null;
      
     
         out.writeLong(fileLength);
         out.flush();
       // receivedDouble = in.readLong();
         //taLog.appendText(fileLength + " has been successfully sent!\n");
         
         String fullName = selectedFile.getName();
         out.writeUTF(fullName);
         out.flush();
        // receivedString = in.readUTF();
         //taLog.appendText(fullName + " has been successfully sent!\n");
         
         //taLog.appendText("Sent from Client:  \nFile Name - (" + fileName + ") " + fileLength + " Bytes \n"); //updated

         
         int dot = fullName.lastIndexOf(".");
         extension = fullName.substring(dot+1);
         out.writeUTF(extension);
         out.flush();
         
         if (rbGreyscale.isSelected()) {
            radioChoice = "Greyscale";
            System.out.println("GREYSCALE IS ON");
         
         }
         else if (rbSepia.isSelected()) {
            radioChoice = "Sepia";
            System.out.println("SEPIA IS ON");
         }
         else if (rbNegative.isSelected()) {
            radioChoice = "Negative";
            System.out.println("NEGATIVE IS ON");
         }
         else {
            radioChoice = "invalid";
            System.out.println("NO ACTIVE RADIO BUTTON");
         }
      
         out.writeUTF(radioChoice);
         out.flush();
      
         taLog.appendText("Sent from Client:  \nFile Name - (" + fileName + ") " + "(" +  fileLength + " Bytes) " + "(" + radioChoice + " is selected!)\n"); //updated
         //taLog.appendText(radioChoice + " is selected!\n");
        ByteArrayOutputStream baos=null; 
        File file=null;
        try{ 
         file = new File(selectedFile.getAbsolutePath());
         BufferedImage buff = ImageIO.read(file);
         baos = new ByteArrayOutputStream();
         ImageIO.write(buff,"jpg",baos);
         //send colored file
         baos.flush();
         out.writeObject(baos.toByteArray());
         taLog.appendText("Successfully Sent File");
         //send colored file
        }
        catch(NullPointerException ne){
          System.out.println("Null Pointer");
        }
        
         baos = new ByteArrayOutputStream();
         
         String fileNameProcessedFull = file.getName();
         int dotIndex = fileNameProcessedFull.lastIndexOf(".");
         String fileNameWanted = fileNameProcessedFull.substring(0, dotIndex);
         System.out.println(fileNameWanted);
         
         String workingDirectory = System.getProperty("user.dir");
     
         String absoluteFilePath = workingDirectory + File.separator + fileNameWanted + "_"+ radioChoice+ "."+ extension;
         System.out.println(absoluteFilePath);
         // Read in the converted file from the Server
         System.out.println("Reading in the ByteBuffer from the Server");
         byte[] byteFile = (byte[]) in.readObject();
         
         // Write out the file from the server to a local file.   
         System.out.println("Writing the file.");
         try(FileOutputStream fos = new FileOutputStream(absoluteFilePath))
         {
            fos.write(byteFile);
            fos.close();
         }
         System.out.println("Makes it here.");
      ;
      }
      
      catch (Exception e){
         e.printStackTrace();
         System.out.println(e);
      
      }
   }
}
