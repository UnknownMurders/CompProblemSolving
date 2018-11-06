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
import java.text.SimpleDateFormat;




/*
   NAME: Edward Riley
   TEMP-PROF: Erik Golen
   COURSE: Computational Problem Solving in Domain II
   DATE: 10/07/2018
*/

public class TimerFun extends Application implements EventHandler<ActionEvent> {

   private Stage stage;
   private Scene scene;
   

	// GUI
   private VBox root = new VBox(6); // Add some space to the GUI


   private MenuItem menuItemExit; 
   private MenuItem menuItemAbout;  
   private Label date = new Label();

	// Color
   private FlowPane fpColor = new FlowPane();
   public static final int NUM_BARS = 7;
   private Label[] lblColor = new Label[NUM_BARS];

	// File
   private FlowPane fpFile = new FlowPane();
   private ProgressBar pbBar = new ProgressBar();
   
   private Timer timer;
   private Thread t1, t2; 
   
   private FileLoading wordsProgress;
   private boolean fileRunning = true;
   private boolean runColor = true;
   



	// Main
   public static void main(String[] args) {
      launch(args);
   }

	// Start
   public void start(Stage _stage) {
      stage = _stage;
      stage.setTitle("Fun with Timers - Jacobson");
      stage.setOnCloseRequest(
         new EventHandler<WindowEvent>() {
            public void handle(WindowEvent evt) {
               try {
                  Thread.currentThread().sleep(2000);
               }
               catch(InterruptedException ie) { 
                  ie.printStackTrace(); 
               }
               System.exit(0);
            }
         });
   
   
      MenuBar menu = new MenuBar();
   
      Menu menuFile = new Menu("File");
      Menu menuHelp = new Menu("Help");
      Label lblWordProgress = new Label("Word Progress:\t   ");
      Label lblUnabridgedProgress = new Label("Unabridged Progress:");
   
      menu.getMenus().addAll(menuFile, menuHelp);
        
      MenuItem menuItemExit = new MenuItem("Exit"); 
      menuFile.getItems().add(menuItemExit); 
      ProgressBar pbWordProgress = new ProgressBar();
      ProgressBar pbUnabridgedProgress = new ProgressBar();
      
        
      MenuItem menuItemAbout = new MenuItem("About"); 
      menuHelp.getItems().add(menuItemAbout); 
   
      root.getChildren().addAll(menu);
      
      menuItemExit.setOnAction(this);
      menuItemAbout.setOnAction(this);
      
      FlowPane fpTop = new FlowPane(10,10);
      fpTop.setAlignment(Pos.CENTER);
   
      java.util.Timer dateTimeTimer = new java.util.Timer();
      TimerTask dateTimeTimerTask = 
         new TimerTask() {
            public void run() {
               Platform.runLater(
                  new Runnable() {
                     public void run() {    
                        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
                        GregorianCalendar cal = new GregorianCalendar();
                        Date curTime = cal.getTime();    
                        date.setText(dateFormat.format(curTime));        
                        date.setFont(Font.font("Serif", FontWeight.BOLD, 25));   
                     }
                  } );
            }
         };
      dateTimeTimer.scheduleAtFixedRate(dateTimeTimerTask, 0, 1000);
      
      fpTop.getChildren().add(date);
      root.getChildren().addAll(fpTop);
   
   	/*
   	 	Color
   	*/
      ColorRunnable color = new ColorRunnable(lblColor, fpColor);
      Thread colorThread = new Thread(color);
   
   
   	// Threads start
      colorThread.start();
   //       fileThread.start();
   //       fileThread2.start();
   
   	// Setup root
      FlowPane fpFirstBottom = new FlowPane(8,8);
   
      fpFirstBottom.setAlignment(Pos.CENTER);
      fpFirstBottom.getChildren().addAll(lblWordProgress, pbWordProgress);
      
      FlowPane fpLastBottom = new FlowPane(8,8);
      fpLastBottom.setAlignment(Pos.CENTER);
      fpLastBottom.getChildren().addAll(lblUnabridgedProgress, pbUnabridgedProgress);
      
      root.getChildren().addAll(fpColor, fpFirstBottom, fpLastBottom);
      //root.getChildren().addAll(fpColor, fpFile, fpFile2);
   
      String [] fileDirectory = {"words.txt", "words100000.txt", "words125000.txt", "words1500.txt", "words150000.txt", "words20000.txt", "words3000.txt", "words5000.txt", "words50000.txt"};
   
      for(int i = 0; i < fileDirectory.length; i++)
      {
         wordsProgress = new FileLoading("Words Progress:", pbWordProgress, new File(fileDirectory[i]));
      }
      FileLoading unabridgedProgress = new FileLoading("Unabridged Progress:", pbUnabridgedProgress, new File("UnabridgedDictionary.txt"));
       t1 = new Thread(wordsProgress);
       t2 = new Thread(unabridgedProgress);
       
       try {
         t1.sleep(2000);
         t1.join();
         t2.join();
       } catch (InterruptedException ie) {
         ie.printStackTrace();
       }
       
       t1.start();
       t2.start();
       
   
   	// Create Window
      scene = new Scene(root, 350, 270);
      stage.setScene(scene);
      stage.show();
      
   }
   
  public class FileLoading extends Pane implements Runnable{
      //Attributes are being created 
      String name;
      private File files, UnabridgedFile;
      public int percent = 0;
      
      
      /** 
       * There are three attributes in the agrument and pass them to the Files Object from the GUI constructor 
       * @param String name, JProgressBar jpbProgressBar, File files
       **/
      public FileLoading(String name, ProgressBar jpbProgressBar, File files){
         this.files = files;
         this.name = name;
         
      }
      
      //To read the files    
      public void run()
      {

         //To create variables for both bufferreaderOne and bufferreaderTwo  
         BufferedReader bufferreaderOne = null, bufferreaderTwo = null;
         
         try{
         
            //Read the bufferreaderOne
            bufferreaderOne = new BufferedReader(new FileReader(files));
            
            //Attributes
            double lineLength = 0;
            double totalOfRead = 0;
            int total = 0;
            String lineOne = bufferreaderOne.readLine();
            String bigLine = "";
              
            //When the readLine is not empty, it addes the total in length and then read the next line 
            while(lineOne != null){
            
               total += lineOne.length();
               lineOne = bufferreaderOne.readLine();      
            }
            
            // Read the bufferreaderTwo   
            bufferreaderTwo = new BufferedReader(new FileReader(files));
            
            //Attribute is being created
            String lineTwo = bufferreaderTwo.readLine();            
            
            //When the lineTwo is not empty and verify is true, then it counts the number of space in the lineTwo
            while(lineTwo != null && fileRunning == true){
            
               bigLine += lineTwo;
               lineLength = bigLine.length();
               
               //To caluclate this percent before it sets the value, which updates the JProgressBar 
               totalOfRead = (lineLength / total) * 100.0;
               percent = (int)totalOfRead;
               
               System.out.println(percent);
               if (percent == 100)
               {
               System.out.println("COMPLETE");
               pbBar.setProgress(1);
               
               }
               
               //pbBar.setProgress(percent);
               
               lineTwo = bufferreaderTwo.readLine();   
            } 
         }
         catch(Exception e){
         
            e.printStackTrace();
         }
         
         //To see which file gets first and halt while reading the second file. 
         synchronized(this){
         
            //When a file didn't get to 100 percent, it will cancel the moving color panel and halt it!
            if(fileRunning == false){
            
               while(!t1.isInterrupted())
               {
                  delay(4000);
               }
            } 
            
            //When a file is at 100%
            if(fileRunning == true){
            
               fileRunning = false; 
               runColor = false;
            }  
         }
      }
   }


   class ColorRunnable implements Runnable 
   {
   	// Attributes
      Label[] lblColor = null;
      FlowPane fpColor = null;
   
      Timer colorTimer = new Timer();
      ArrayList<Color> colors = new ArrayList<>(Arrays.asList(Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.INDIGO, Color.VIOLET));
   
   	// Constructor
      public ColorRunnable(Label[] _lblColor, FlowPane _fpColor) {
      	// lblColor and fpColor needed to update GUI
         this.lblColor = _lblColor;
         this.fpColor = _fpColor;
      }
   
   	// Run
      public void run() {
         System.out.println("Color Thread Starting");
         colorTimer.scheduleAtFixedRate(new ColorTask(lblColor, fpColor), 2000, 500);
         
      }   
   
   
   	// Color TimerTask
      class ColorTask extends TimerTask {
      	// Attributes
         Label[] lblColor = null;
         FlowPane fpColor = null;
      
      	// Constructor
         ColorTask(Label[] _lblColor, FlowPane _fpColor) {
         	// lblColor and fpColor needed to update GUI
            this.lblColor = _lblColor;
            this.fpColor = _fpColor;
         
         	// Create each label with a color
            for(int i=0; i<lblColor.length; i++) {
            
            	// Setup labels
               lblColor[i] = new Label("");
               lblColor[i].setBackground(
                  new Background(
                  	new BackgroundFill(colors.get(i), null, null)
                  )
                  );
               lblColor[i].setPrefWidth(350);
            
            	// Add to GUI
               fpColor.getChildren().add(lblColor[i]);
            }
         }
      
      	// Run
         public void run() {
         	// Allow thread to write to GUI
            Platform.runLater(
               new Runnable() {
                  public void run() {
                  
                     colors.add( colors.get(0) );
                     colors.remove( colors.get(0) );
                  
                  
                  
                     for(int i=0; i<lblColor.length; i++) {
                        lblColor[i].setBackground(
                           new Background(
                           new BackgroundFill(colors.get(i), null, null)
                           )
                           );
                     }
                  
                  }
               });
         }
      }
   }
   



   public void handle(ActionEvent ae){
   
      String nameMenu = ((MenuItem)ae.getSource()).getText();
    
      switch (nameMenu){
         case "About": 
            doAbout();
            break;
         case "Exit": 
            delay(2000);
            System.exit(0);
      } 
   }

   public void doAbout(){
      Alert alertError = new Alert(Alert.AlertType.INFORMATION, "Fun with Timers and Threads\nAuthor: Edward Riley", new ButtonType[0]);
      alertError.setHeaderText("Author");
      alertError.showAndWait();
   }
   
   public void delay(int num){
      try{
         Thread.sleep(num); 
      }
      catch(InterruptedException io) {}
   }   
   
   
}

