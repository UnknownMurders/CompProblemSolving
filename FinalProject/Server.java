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


import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

 
public class Server extends Application implements EventHandler<ActionEvent> {
   // Window attributes
   private Stage stage;
   private Scene scene;
   private VBox root;
   
   // GUI Components
   public Label lblLog = new Label("Log:");
   public TextArea taLog = new TextArea();
   public Button btnStartStop = new Button("Start");
   
   // Socket stuff
   private ServerSocket sSocket = null;
   public static final int SERVER_PORT = 50000;
   private ServerThread serverThread = null;
   
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
      stage.setTitle("SERVER");
      stage.setOnCloseRequest(
         new EventHandler<WindowEvent>() {
            public void handle(WindowEvent evt) { System.exit(0); }
         } );
      stage.setResizable(false);
      root = new VBox(8);
      
      // Start/Stop button
      FlowPane fpStart = new FlowPane(8,8);
      fpStart.setAlignment(Pos.BASELINE_RIGHT);
      fpStart.getChildren().add(btnStartStop);
      btnStartStop.setOnAction(this);
      root.getChildren().add(fpStart);
   
      // LOG components
      FlowPane fpLog = new FlowPane(8,8);
      fpLog.setAlignment(Pos.CENTER);
      taLog.setPrefRowCount(10);
      taLog.setPrefColumnCount(35);
      fpLog.getChildren().addAll(lblLog, taLog);
      root.getChildren().add(fpLog);
      
      // Show window
      scene = new Scene(root, 475, 300);
      stage.setScene(scene);
      stage.show();   
   }
   
   // Start/Stop button
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
   
   public void doStart() {
      serverThread = new ServerThread();
      serverThread.start();
      btnStartStop.setText("Stop");
   }
   
   public void doStop() {
      serverThread.stopServer();
      btnStartStop.setText("Start");
   }
   
   class ServerThread extends Thread {
      public void run() {
         // Server stuff ... wait for a connection and process it
         try {
            sSocket = new ServerSocket(SERVER_PORT);
         }
         catch(IOException ioe) {
            log("ServerThread: IO Exception (1): "+ ioe + "\n");
            return;
         }
          
         while(true) {
            // Socket for the client
            Socket cSocket = null;
             
            try {
               // Wait for a connection and set up IO
               cSocket = sSocket.accept();
            }
            catch(IOException ioe) {
               // Happens when sSocket is closed in the stop (below)
               // and the accept (above) is blocked. This is OK.
               // Log the error then return
               return;
            }   
             
            // Create a thread for the client, passing cSocket to the
            // thread’s constructor and start the thread...
            ClientThread ct = new ClientThread(cSocket);
            ct.start();      
            
         } // of while loop
      } // of run
      
      public void stopServer() {
         try {
            sSocket.close();  // This terminates any blocked accepts
         }
         catch(Exception e) {
            log("ServerThread: Exception: " + e + "\n");
         }
      }
   } // of ServerThread
   
   class ClientThread extends Thread {
      // Since attributes are per-object items, each ClientThread has its OWN
      // socket, unique to that client
      private Socket cSocket;
      private String clientId = "";
   
      // Constructor for ClientThread
      public ClientThread(Socket _cSocket) {
         cSocket = _cSocket;
         clientId = "<" + cSocket.getInetAddress().getHostAddress() + ">" + "<" + cSocket.getPort() + ">";
      }
      
      // main program for a ClientThread
      public void run() {
         DataInputStream in = null;
         DataOutputStream out = null;
         
         log(clientId + " Client connected!\n");
         
         try {
            // Open streams 
            in = new DataInputStream(cSocket.getInputStream());
            out = new DataOutputStream(cSocket.getOutputStream());            
         }
         catch(IOException ioe) {
            log(clientId + " IO Exception (ClientThread): "+ ioe + "\n");
            return;
         }
         
         // ADD CODING IN THIS HERE //
         // - Received from Client
         // - Display 6-8 Images
         // - Sent back to Server
         
         
      
      // EXAMPLE -->  BUT PLEASE ADJUST IT AS WELL. <--   
         try {
            
            double size = in.readDouble();
            taLog.appendText("Received: " + size + "\n");
            taLog.appendText("Sending: " + size + "\n");
            out.writeDouble(size);
            
            String fileName = in.readUTF();
            taLog.appendText("Received: " + fileName + "\n");
            taLog.appendText("Sending: " + fileName + " \n");
            out.writeUTF(fileName);
            
            String extension = in.readUTF();
            taLog.appendText("Received: " + extension + "\n");
            taLog.appendText("Sending: " + extension + " \n");
            out.writeUTF(extension);
               
         }
         catch(Exception e) {
            taLog.appendText("Error during transmission: " + e + "\n");
         }
      
         // on EOF, client has disconnected 
         try {
            // Close the Socket and the streams
            cSocket.close();
            in.close();
            out.close();
         }
         catch(IOException ioe) {
            log(clientId + " IO Exception (3): "+ ioe + "\n");
            return;
         }
         
         log(clientId + " Client disconnected!\n");
      }  
   } // End of inner class
   
   // utility method "log" to log a message in a thread safe manner
   private void log(String message) {
      Platform.runLater(
         new Runnable() {
            public void run() {
               taLog.appendText(message);
            }
         } );
   } // of log  
   

   public void doGreyscale()
   {
      BufferedImage greyscaleImage = null;
      File f = null;
      
      //
      int width = greyscaleImage.getWidth();
      int height = greyscaleImage.getHeight();
      
      //converts to greyscale
      for (int y = 0; y < height; y++)
      {
         for (int x = 0; x < width; x++)
         {
            int p = greyscaleImage.getRGB(x,y);
         
            int a = (p>>24)&0xff;
            int r = (p>>16)&0xff;
            int g = (p>>8)&0xff;
            int b = p&0xff;
         
         //calculate average
            int avg = (r+g+b)/3;
         
         //replace RGB value with avg
            p = (a<<24) | (avg<<16) | (avg<<8) | avg;
         
            greyscaleImage.setRGB(x, y, p);
         }
      }
      
   
   }
}