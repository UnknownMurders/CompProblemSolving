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
 
public class Server extends Application implements EventHandler<ActionEvent>{
   // Window attributes
   private Stage stage;
   private Scene scene;
   private VBox root;
   
   // GUI Components
   public Label lblLog = new Label("Log:");
   public TextArea taLog = new TextArea();
   public Button btnStartStop = new Button("Start");
   
   //Clear Button
   Button btnClear = new Button("Clear");
   
   // Socket stuff
   private ServerSocket sSocket = null;
   public static final int SERVER_PORT = 50000;
   private ServerThread serverThread = null;
   
   public static final int RUNNING_THREAD_COUNT=6;
   public static final int NUMBER_OF_SECTORS=6;
   public static final int MAX_CLIENTS=3;
   public static final int CHECK_TIME_INTERVAL=500;
   public static final int X_UNITS=3;
   public static final int Y_UNITS=2;
   
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
         
      FlowPane fpRow1 = new FlowPane(8,8);
      fpRow1.setAlignment(Pos.CENTER);
      fpRow1.getChildren().addAll(btnClear, btnStartStop);
      root.getChildren().add(fpRow1);

      btnStartStop.setOnAction(this);
      btnClear.setOnAction(this);

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
         case "Clear":
            doClear();
            break;
      }
   } 
   
   private void doClear() {
      taLog.setText("");
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
      
     private TaskQueueSystem tqs= null;
     public ServerThread(){
       tqs=new TaskQueueSystem(RUNNING_THREAD_COUNT,CHECK_TIME_INTERVAL,NUMBER_OF_SECTORS,MAX_CLIENTS,taLog);
     }
     public void run() {
         // Server stuff ... wait for a connection and process it
         try {
            sSocket = new ServerSocket(SERVER_PORT);
         }
         catch(IOException ioe) {
            log("ServerThread: IO Exception (1): "+ ioe + "\n");
            return;
         }
         tqs.start(); 
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
            ClientThread ct = new ClientThread(cSocket,tqs);
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
   
      private TaskQueueSystem tqs;
      
      // Constructor for ClientThread
      public ClientThread(Socket _cSocket,TaskQueueSystem _tqs) {
         cSocket = _cSocket;
         clientId = "<" + cSocket.getInetAddress().getHostAddress() + ">" + "<" + cSocket.getPort() + ">";
         
         tqs=_tqs;
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
                  //taLog.appendText("Received: " + size + "\n");
               
                  String fileName = in.readUTF();
                  //taLog.appendText("Received: " + fileName + "\n");
               
                  String extension = in.readUTF();
                  //taLog.appendText("Received: " + extension + "\n");
                  //taLog.appendText("Sending: " + extension + " \n");
               
                  String radioChoice = in.readUTF();
                  //taLog.appendText("Received: " + radioChoice + "\n");
                  
                  log("Received from Client:  \nFile Name - (" + fileName + ") " + "(" + size + " Bytes) \n"); //updated
                  //log("Sent from Server:  \nFile Name - (" + fileName + ") " + "(" + size + " Bytes) \n");  //updated
               
               
                  ByteArrayInputStream bais =  new ByteArrayInputStream((byte[])in.readObject());
                  BufferedImage image = ImageIO.read(bais);
                  File file = new File("temp"+fileName);
                  ImageIO.write(image,extension,file);
                  image =ImageIO.read(file);
                  // Convert image to a Buffered image
               
               
               //  BufferedImage [] imageArray = imageProcessing.divide(image);
               
                  switch (radioChoice)
                  {
                     case "Greyscale": 
                        System.out.println("Greyscale");
                        radioChoice = "_greyscale";
                        break;

                     case "Sepia": 
                        System.out.println("Sepia");
                        radioChoice = "_sepia";
                        break;
                     case "Negative": 
                        System.out.println("Negative");
                        radioChoice = "_negative";
                        break;
                     default: 
                        System.out.println("No Active Radio Buttons");
                        break;
                  }                  
                  
            
                  boolean belowThreshold=false;
                  while(!belowThreshold){
                      if(tqs.getFilenameCount()<MAX_CLIENTS){
                        for(int i = 0;i<Y_UNITS;i++){
                           for(int k =0;k<X_UNITS;k++){
                              SectorBuilder sb = new SectorBuilder(image,k,i,X_UNITS,Y_UNITS);
                              Task task = new Task(sb,"builder",radioChoice,3,fileName,cSocket,out);
                              tqs.add(task);
                           }
                        }
                        belowThreshold=true;
                     }
                     else{ 
                        try{
                          Thread.sleep(CHECK_TIME_INTERVAL);
                        }
                        catch(InterruptedException ie)
                        {
                          log(clientId+":Programmus Interruptus");
                        }
                     }
                  }
               }
         
          
         }
         catch(IOException ioe){
          log(clientId+"Connection Closed");
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
   

      
}

class Sector{
   private BufferedImage buffImg;
   private int x,y,width,height;
   public Sector(BufferedImage _buffImg,int _x,int _y,int _width,int _height){
      buffImg=_buffImg;
      x=_x;
      y=_y;
      width=_width;
      height=_height;
   }
   public int getRGB(int _x,int _y){
      if((_x >= width)||(_y>=height)||(_x<0)||(_y<0))
      {
         throw new IndexOutOfBoundsException();
      }
      return buffImg.getRGB((x+_x),(y+_y)); 
   }
   public void setRGB(int _x,int _y, int value){
      
      if((_x >= width)||(_y>=height)||(_x<0)||(_y<0))
      {
         throw new IndexOutOfBoundsException();
      }
      buffImg.setRGB((x+_x),(y+_y),value);
   }
   public int getWidth(){
      return width;
   }
   public int getHeight(){
      return height;
   }
   public void setWidth(int _width){
      width=_width;
   }
   public void setHeight(int _height){
      height=_height;
   }
   public int getX(){
      return x;
   }
   public int getY(){
      return y;
   }
   public BufferedImage getImage(){
      return buffImg;
   }
}
class SectorBuilder extends Thread{
   private BufferedImage buffImg;
   private int sectionX, sectionY,xUnits,yUnits;
   private Sector sect=null;
   public SectorBuilder(BufferedImage _buffImg,int _sectionX,int _sectionY, int _xUnits,int _yUnits){
      buffImg=_buffImg;
      sectionX=_sectionX;
      sectionY=_sectionY;
      xUnits=_xUnits;
      yUnits=_yUnits;
   
      
   }
   public void run(){
      int width = buffImg.getWidth()/xUnits;
      int height= buffImg.getHeight()/yUnits;
   
      int x = width * sectionX;
      int y = height * sectionY;
      if ((sectionX==(xUnits-1))&&((width % xUnits)>0)){
         width += (buffImg.getWidth() % xUnits);
      }
      
      if ((sectionY==(yUnits-1))&&((height % yUnits)>0)){
         height += (buffImg.getHeight() % yUnits);
      }
      sect= new Sector(buffImg,x,y,width,height);
   }
   public int getSectorId(){
      return ((sectionY*xUnits)+sectionX);
   }
   public Sector getSector(){
      return sect;
   }
}
class Task extends Thread implements Comparable<Task>{
   private Thread work;
   private String workType="";
   private int taskId;
   private String filename;
   private String imageType;
   private int priority;
   private Socket cSocket;
   private ObjectOutputStream oos;
   public Task(Thread _work,String _workType,String _imageType,int _priority,String _filename,Socket _cSocket,ObjectOutputStream _oos)
   {
      work = _work;
      workType = _workType;
      imageType= _imageType;
      taskId = 0;
      priority = _priority;
      filename = _filename;
      cSocket= _cSocket;
      oos=_oos;
   }
   public ObjectOutputStream getOos(){
      return oos;
   }
   public void setTaskId(int id){
      taskId=id;
   }
   public int getTaskPriority(){
      return priority;
   }
   public String getFilename(){
      return filename;
   }
   public String getImageType(){
      return imageType;
   }
   public int compareTo(Task otherTask){
      return (priority-otherTask.getTaskPriority());
   }
   public Socket getSocket(){
      return cSocket;
   }
   public void run(){
      switch(workType){
            case "builder":
               ((SectorBuilder)work).start();
               break;
            case "imageProcessing":
               ((ImageProcessing)work).start();
               break;
            case "sendingFile":
               ((SendImage)work).start();
               break;
      }
      try {
         work.join();

      }catch(InterruptedException ie)
      {
         System.out.println("Task "+taskId+" Interrupted");
      }

   }
   public String getWorkType(){
      return workType;
   }
   public Thread getWork(){
      return work;
   }
}
class TaskQueueSystem extends Thread{
   private TextArea taLog;
   private PriorityQueue<Task> taskPool;
   private int runningThreadCount;
   private int checkTimeInterval;
   private int taskIdCounter=0;
   private int numberOfSectors;
   private LinkedList<Task> runningThreads;
   private LinkedList<LinkedList<Task>> imageProcessingTasks;
   private boolean stayinAlive = true;
   private java.util.Timer checkTimer;
   public TaskQueueSystem(int _runningThreadCount,int _checkTimeInterval,int _numberOfSectors,int maxClients,TextArea _taLog){
      checkTimeInterval=_checkTimeInterval;
      runningThreadCount=_runningThreadCount;
      taskPool = new PriorityQueue<Task>();
      numberOfSectors=_numberOfSectors;
      runningThreads = new LinkedList<Task>();
      imageProcessingTasks= new LinkedList<LinkedList<Task>>();
      taLog = _taLog;
      checkTimer = new java.util.Timer();
   }
   public void run(){ 
      checkTimer.scheduleAtFixedRate(new TimerTask(){
         public void run(){
            check();
            //System.out.println("Checking"); 
         }
      },0,checkTimeInterval);
      while(stayinAlive){
         Thread.yield();
      }
   }
   public void killQueue(){
      checkTimer.cancel();
      checkTimer.purge();
      stayinAlive=false;
   }
   public int getFilenameCount(){
      ArrayList<String> names =new ArrayList<String>();
      Iterator<Task> poolIter = taskPool.iterator();
      while(poolIter.hasNext()){
         Task temp= poolIter.next();
         if(!names.contains(temp.getFilename())){
            names.add(temp.getFilename());
         }
      }
      Iterator<Task> runningIter= runningThreads.iterator();
      while(runningIter.hasNext()){
         Task temp= runningIter.next();
         if(!names.contains(temp.getFilename())){
            names.add(temp.getFilename());
         }
      }
      Iterator<LinkedList<Task>> iPT = imageProcessingTasks.iterator();
      while(iPT.hasNext()){
         Task temp= iPT.next().get(0);
         if(!names.contains(temp.getFilename())){
            names.add(temp.getFilename());
         }
      }
      return names.size();
   }

    private void log(String message) {
         Platform.runLater(
             new Runnable() {
                  public void run() {
                      taLog.appendText(message);
                  }
             } );
    } // of log   
   public void check(){
      // Check if linkedlist taskisdone
      Iterator<Task> runningIter = runningThreads.iterator();
      while(runningIter.hasNext()){
         Task tmp= runningIter.next();
         if(!tmp.isAlive()){
            postWork(tmp);
            System.out.println(tmp.getFilename()+tmp.getWorkType()+" has finished");
            runningIter.remove();
         }
      }
      // Check if imageProc linkedList is full
      Iterator<LinkedList<Task>> imgProcIter = imageProcessingTasks.iterator();
      while(imgProcIter.hasNext()){
         LinkedList<Task> tmp = imgProcIter.next();
         if(tmp.size()==numberOfSectors){
            SendImage si = new SendImage((((ImageProcessing)tmp.get(0).getWork()).getSector()).getImage(),tmp.get(0).getSocket(),taLog,tmp.get(0).getImageType(),tmp.get(0).getOos());
            Task newTask = new Task(si,"sendingFile",tmp.get(0).getImageType(),1,tmp.get(0).getFilename(),tmp.get(0).getSocket(),tmp.get(0).getOos());
            //System.out.print("Sending Thread Queued");
            add(newTask);
            imgProcIter.remove();
         }
      }
      // Check if running link list is full
      while((runningThreads.size()<runningThreadCount)&&(taskPool.size()>0)){
         Task newTask = taskPool.poll();
         newTask.start();
	       //System.out.println("Starting task " + newTask.getFilename()+newTask.getWorkType());
         runningThreads.add(newTask);
      }
   }
   public void add(Task task){
      task.setTaskId(taskIdCounter);
      taskIdCounter++;
      taskPool.add(task);
   }
   public void postWork(Task task){
      Task newTask=null;
      switch(task.getWorkType()){
         case "builder":
            SectorBuilder sc = (SectorBuilder)task.getWork();
            ImageProcessing iP = new ImageProcessing(sc.getSector(),task.getImageType());
            newTask= new Task(iP,"imageProcessing",task.getImageType(),5,task.getFilename(),task.getSocket(),task.getOos());
            add(newTask);
            break;
         case "imageProcessing":
            int index = fileNameIndex(task.getFilename());
            if(index>=0){
               imageProcessingTasks.get(index).add(task);
            }
            else{
               LinkedList<Task> llt = new LinkedList<Task>();
               llt.add(task);
               imageProcessingTasks.add(llt);
            }
            break;
         case "sendingFile":

            log(clientId(task)+"File"+task.getFilename()+" Successfully Sent");
            break;
      }
   }
   public String clientId(Task task){
      Socket cSocket = task.getSocket();
      return "<" + cSocket.getInetAddress().getHostAddress() + ">" + "<" + cSocket.getPort() + ">";
   }
   public int fileNameIndex(String filename){
      for(int i = 0;i<imageProcessingTasks.size();i++){
         if(imageProcessingTasks.get(i).get(0).getFilename().equals(filename)){
            return i;
         }
      }
      return -1;
   }
}
class ImageProcessing extends Thread{
   private Sector sector;
   private String type;
   private int width,height; 
   public ImageProcessing(Sector _sector,String _type){
      sector=_sector;
      type=_type;

      int width = sector.getWidth();
      int height = sector.getHeight();
      
      //System.out.println("Is of type:"+type+"W:"+width+"|H:"+height);
   }
   public void run(){
      BufferedImage img = sector.getImage();
      //System.out.println("its actually running this");
      int sWidth=0,sHeight=0;
      synchronized(sector){
        sWidth+=sector.getWidth();
        sHeight+=sector.getHeight();
      }
      int x,y;
      //System.out.println(sHeight+""+sWidth);
      for (y = 0; y < sHeight; y++){
         //System.out.println("It gets Herey");
         for (x = 0; x < sWidth; x++)
         {
            //System.out.println("How about Herey!");
            int p=0;
            //synchronized(sector){
               p = sector.getRGB(x,y);
            //}
    
            //System.out.println("P value before:"+p); 
            int a = (p>>24)&0xff;
            int r = (p>>16)&0xff;
            int g = (p>>8)&0xff;
            int b = p&0xff;
            switch(type){
               case "_greyscale":
                  
                  p=doGreyscale(a,r,g,b); 
                  //System.out.println("P value after:"+p); 
                  break;
               case "_sepia":
                  p=doSepia(a,r,g,b);
                  break;
               case "_negative":
                  p=doNegative(a,r,g,b);
                  break;
            }
	          //synchronized(sector){
               sector.setRGB(x,y,p); 
            //}
         }
      }
      //File tmp = new File("temp"+sector.getX()+sector.getY());
      //try{
        // ImageIO.write(img,"jpg",tmp);
      //}
      //catch(IOException ioe){}
      
   }
   public Sector getSector(){
      return sector;
   }

   public int doGreyscale(int a,int r, int g, int b) {
   
   //grabs argb
         //calculate average
       int p;
       int avg = (r+g+b)/3;
         
         //reassign RGB value
       p = (a<<24) | (avg<<16) | (avg<<8) | avg;
       return p;
         
       
   
       //return greyscaleImage;
   }//end greyscale

   public int doNegative(int a,int r,int g,int b) {
        
         //assign new values
             int p;
             int new_a = a;
             int r_new = 255 - r;
             int g_new = 255 - g; 
             int b_new = 255 - b;  
         
         
         //reassign RGB value
             p = (a<<24) | (r_new<<16) | (g_new<<8) | b_new;
             return p; 
         
       
   }//end negative

   public int doSepia(int a,int r,int g,int b) {
   
   //
             int p; 
         //calculate average
             int a_new = a; 
             int r_new = (int)(0.393*r + 0.769*g + 0.189*b);
             int g_new = (int)(0.349*r + 0.686*g + 0.168*b);
             int b_new = (int)(0.272*r + 0.534*g + 0.131*b);
               
         //reassign RGB value
             
             if (r_new > 255)
               r_new = 255;
             if (g_new > 255)
               g_new = 255;
             if (b_new > 255)
               b_new = 255;   
             p = (a_new<<24) | (r_new<<16) | (g_new<<8) | b_new;
             return p;
         
       
   }//end sepia
   
   
}   
class SendImage extends Thread{
   private BufferedImage image;
   private Socket cSocket;
   private String clientId;
   private TextArea taLog;
   private String extension;
   private ObjectOutputStream oos;
   public SendImage(BufferedImage _image,Socket _cSocket,TextArea _taLog,String _extension,ObjectOutputStream _Oos){
      image=_image;
      cSocket=_cSocket;
      taLog=_taLog;
      oos=_Oos;
      clientId = "<" + cSocket.getInetAddress().getHostAddress() + ">" + "<" + cSocket.getPort() + ">";
   }
   public void run(){
      //System.out.println("Gets Here!");
      try{
         //System.out.println("Gets This far"); 
                    
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         
         ImageIO.write(image,"jpg",baos);
         oos.writeObject(baos.toByteArray());
         oos.flush();
         //System.out.println("Sending File Back!");
         log(clientId+"sending file!\n");
      }
      catch(IOException ioe) {
         System.out.print(clientId+"IOEXCEPTION" +ioe+"\n");
         log(clientId + " IO Exception (ClientThread): "+ ioe + "\n");
         return;
      }
   }
   
    private void log(String message) {
         Platform.runLater(
             new Runnable() {
                  public void run() {
                      taLog.appendText(message);
                  }
             } );
    } // of log   

}
