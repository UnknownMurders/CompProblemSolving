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
import java.nio.file.Path;
import java.nio.*;
import java.io.*;
import java.nio.file.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import java.awt.Graphics;
import java.awt.Graphics2D;
 



 

 
public class Server extends Application implements EventHandler<ActionEvent>, Serializable {
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
   ArrayList <ClientThread> ctArray = new ArrayList<ClientThread>();
   
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
         ObjectOutputStream out = null;
         ObjectInputStream in = null;
         
         
         log(clientId + " Client connected!\n");
         
         try {
            // Open streams 
            out = new ObjectOutputStream(cSocket.getOutputStream());   
            in = new ObjectInputStream(cSocket.getInputStream());
                    
         }
         catch(IOException ioe) {
            log(clientId + " IO Exception (ClientThread): "+ ioe + "\n");
            return;
         }
      
         try {
            
            
            while(true)
            {
                  long size = in.readLong();
                  taLog.appendText("Received: " + size + "\n");
               // taLog.appendText("Sending: " + size + "\n");
               //  out.writeLong(size);
               
                  String fileName = in.readUTF();
                  taLog.appendText("Received: " + fileName + "\n");
               // taLog.appendText("Sending: " + fileName + " \n");
               // out.writeUTF(fileName);
               
                  String extension = in.readUTF();
                  taLog.appendText("Received: " + extension + "\n");
                  taLog.appendText("Sending: " + extension + " \n");
               // out.writeUTF(extension);
               
                  String radioChoice = in.readUTF();
                  taLog.appendText("Received: " + radioChoice + "\n");
               
               
               //read in colored image
                  File file = (File) in.readObject();
               
               // Convert image to a Buffered image
                  BufferedImage image = ImageIO.read(file);
               
                  ImageProcessing imageProcessing = new ImageProcessing();
               
                  BufferedImage [] processedArray = imageProcessing.divide(image);
               //  BufferedImage [] imageArray = imageProcessing.divide(image);
               
                  switch (radioChoice)
                  {
                     case "Greyscale": 
                     
                     // System.out.println("Greyscale");
                        radioChoice = "_greyscale";
                     
                     //convert to greyscale iamge
                     
                     
                     
                        for(int i = 0; i < processedArray.length; i++)
                        {
                           image =  imageProcessing.doGreyscale(processedArray[i]);
                        // processedArray[i] = greyscaleImage;
                           processedArray[i] = image;
                        
                        
                        }
                     
                     
                        for(BufferedImage imageJoined : processedArray)
                        {
                           image = imageProcessing.joinImage(processedArray);
                        }
                     
                     //write greyscale image back to client
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(image, extension, baos);
                     
                     
                     
                     // Convert image to a byte array
                        byte[] imageBytes = baos.toByteArray();
                     
                     // Write the byte array object to the Client/Server stream output.                  
                        out.writeObject(imageBytes);
                        out.flush();
                     
                     
                        break;
                  
                     case "Sepia": 
                     
                     // System.out.println("Sepia");
                        radioChoice = "_sepia";
                     
                     //convert to greyscale iamge
                     //  BufferedImage sepiaImage =  imageProcessing.doSepia(image);
                     
                     
                        for(int i = 0; i < processedArray.length; i++)
                        {
                           image =  imageProcessing.doSepia(processedArray[i]);
                        // processedArray[i] = greyscaleImage;
                           processedArray[i] = image;
                        
                        
                        }
                     
                     
                        for(BufferedImage imageJoined : processedArray)
                        {
                           image = imageProcessing.joinImage(processedArray);
                        }
                     
                     //write greyscale image back to client
                        baos = new ByteArrayOutputStream();
                        ImageIO.write(image, extension, baos);
                     
                     
                     
                     // Convert image to a byte array
                        imageBytes = baos.toByteArray();
                     
                     // Write the byte array object to the Client/Server stream output.                  
                        out.writeObject(imageBytes);
                        out.flush();
                     
                     
                        break;
                  
                  
                     case "Negative": 
                     //  System.out.println("Negative");
                     
                        radioChoice = "_Negative";
                        for(int i = 0; i < processedArray.length; i++)
                        {
                           image =  imageProcessing.doNegative(processedArray[i]);
                        // processedArray[i] = greyscaleImage;
                           processedArray[i] = image;
                        
                        
                        }
                     
                     
                        for(BufferedImage imageJoined : processedArray)
                        {
                           image = imageProcessing.joinImage(processedArray);
                        }
                     
                     //write greyscale image back to client
                        baos = new ByteArrayOutputStream();
                        ImageIO.write(image, extension, baos);
                     
                     
                     
                     // Convert image to a byte array
                        imageBytes = baos.toByteArray();
                     
                     // Write the byte array object to the Client/Server stream output.                  
                        out.writeObject(imageBytes);
                        out.flush();
                     
                        break;
                     default: 
                     // System.out.println("No Active Radio Buttons");
                        break; 
                  
                  
               }
               
          
            
            }
            
          
         
              
         }
          
         // }
         catch(Exception e) {
            //System.out.println(e.getStackTrace().toString());
            e.printStackTrace();
            System.out.println(e);
            
            taLog.appendText("Error during transmission: " + e + "\n");
         }
      
      
      
         
         // on EOF, client has disconnected 
       //  try {
            // Close the Socket and the streams
          //  cSocket.close();
          //  in.close();
         //  out.close();
        // }
        // catch(IOException ioe) {
         //   log(clientId + " IO Exception (3): "+ ioe + "\n");
          //  return;
        // }
         
         //log(clientId + " Client disconnected!\n");
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
   

   public class ImageProcessing extends Thread
   {
      public synchronized BufferedImage doGreyscale(BufferedImage image) {
      // BufferedImage greyscaleImage = null;
         BufferedImage greyscaleImage = image;
      
         int width = greyscaleImage.getWidth();
         int height = greyscaleImage.getHeight();
      
      //grabs argb
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
               int a_new = a;
               int r_new = avg;
               int g_new = avg;
               int b_new = avg;
              
            
            //reassign RGB value
               //p = (a<<24) | (avg<<16) | (avg<<8) | avg;
               int p_new = (a_new<<24) | (r_new<<16) | (g_new<<8) | b_new;
               greyscaleImage.setRGB(x,y,p_new);
            
               //greyscaleImage.setRGB(x, y, p);
            }
         }
      
         return greyscaleImage;
      }//end greyscale
   
      public synchronized BufferedImage doNegative(BufferedImage image) {
         BufferedImage negativeImage = image;
        // File f = null;
      
      //
         int width = negativeImage.getWidth();
         int height = negativeImage.getHeight();
      
      //grabs argb
         for (int y = 0; y < height; y++)
         {
            for (int x = 0; x < width; x++)
            {
               int p = negativeImage.getRGB(x,y);
            
               int a = (p>>24)&0xff;
               int r = (p>>16)&0xff;
               int g = (p>>8)&0xff;
               int b = p&0xff;
            
            //assign new values
               int new_a = a;
               int r_new = 255 - r;
               int g_new = 255 - g; 
               int b_new = 255 - b;  
            
            
            //reassign RGB value
               p = (a<<24) | (r_new<<16) | (g_new<<8) | b_new;
            
               negativeImage.setRGB(x, y, p);
            }
         }
         
         return negativeImage;
      }//end sepia
   
      public synchronized BufferedImage doSepia(BufferedImage image) {
         BufferedImage sepiaImage = image;
        // File f = null;
      
      //
         int width = sepiaImage.getWidth();
         int height = sepiaImage.getHeight();
      
      //grabs argb
         for (int y = 0; y < height; y++)
         {
            for (int x = 0; x < width; x++)
            {
               int p = sepiaImage.getRGB(x,y);
            
               int a = (p>>24)&0xff;
               int r = (p>>16)&0xff;
               int g = (p>>8)&0xff;
               int b = p&0xff;
            
            //calculate average
               int a_new = a; 
               int r_new = (int)(0.393*r + 0.769*g + 0.189*b);
               int g_new = (int)(0.349*r + 0.686*g + 0.168*b);
               int b_new = (int)(0.272*r + 0.534*g + 0.131*b);
               
               if (r_new > 255)
                  r_new = 255;
               if (g_new > 255)
                  g_new = 255;
               if (b_new > 255)
                  b_new = 255;   
                  
            //reassign RGB value
               int p_new = (a_new<<24) | (r_new<<16) | (g_new<<8) | b_new;
            
               sepiaImage.setRGB(x, y, p_new);
            }
         }
         
         return sepiaImage;
      }//end negative
      
      
      public BufferedImage joinImage(BufferedImage [] imageArray )
      {
         int offset = 2;
         int width = imageArray[0].getWidth() + imageArray[1].getWidth() + imageArray[2].getWidth() + offset;
      //int height = Math.max(img1.getHeight(), img2.getHeight()) + offset;
         int height = Math.max(imageArray[0].getHeight(), Math.max(imageArray[1].getHeight(), imageArray[2].getHeight()) )
               + Math.max(imageArray[3].getHeight(), Math.max(imageArray[4].getHeight(), imageArray[5].getHeight()) ) + offset;
         Canvas canvas = new Canvas(width,height);
      
         BufferedImage image1 = imageArray[0];
      
      
         BufferedImage newImage = new BufferedImage(width, height,
            BufferedImage.OPAQUE);
         Graphics g =  newImage.getGraphics();
      
      //g.setPaint(Color.BLACK);
      // g.fillRect(0, 0, width, height);
      //  g.setColor(oldColor);
         g.drawImage(imageArray[0],0, 0, null);
         g.drawImage(imageArray[1], imageArray[0].getWidth(), 0, null);
         g.drawImage(imageArray[2], imageArray[0].getWidth() + imageArray[1].getWidth(), 0, null);
         g.drawImage(imageArray[3], 0, imageArray[0].getHeight(), null);
         g.drawImage(imageArray[4], imageArray[0].getWidth(), imageArray[0].getHeight(), null);
         g.drawImage(imageArray[5], imageArray[0].getWidth() + imageArray[1].getWidth(), imageArray[0].getHeight(), null);
      
      
      
      
      
      
         g.dispose();
      
      
         return newImage;
      }
      
      public BufferedImage[] divide(BufferedImage image) {
         // Split the image into 6 Sectors
         // 1 2 3
         // 4 5 6
      
         // Calculate the width of each Section
         int height = image.getHeight() / 2;
         int fullHeight = image.getHeight();
         int width = image.getWidth() / 3;
         int width2 = width + width;
         int fullWidth = image.getWidth();
      
       
            // Break the picture into 6 parts.
            // Compensate for any image that is not evenly divisible.
         return new BufferedImage[]
            {
            image.getSubimage(0, 0, width, height), // Sector 1
            
            image.getSubimage(width, 0, width, height), // Sector 2
            
             image.getSubimage(width2, 0, fullWidth - (width2), height), // Sector 3
            
            image.getSubimage(0, height, width, fullHeight-height), // Sector 4
            image.getSubimage(width, height, width, fullHeight-height), // Sector 5
            image.getSubimage(width2, height, fullWidth - (width2), fullHeight-height) // Sector 6
            
            };
            
      }
   
   
   
   }   
}