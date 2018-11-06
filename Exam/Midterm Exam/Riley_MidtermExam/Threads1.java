import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import java.io.*;
import java.util.*;

public class Threads1 extends Application {
   // Window attributes
   Stage stage;
   Scene scene;
   VBox root;
   ArrayList<Integer> list = new ArrayList<Integer>();

   
   // GUI Components
   private TextArea taOutput = new TextArea();

   // Main program
   public static void main(String[] args) {
      launch(args);
   }
   
   // Callback to start the GUI
   public void start(Stage _stage) {
   
   
      // Setup window
      stage = _stage;
      stage.setTitle("Practical 1");
      stage.setOnCloseRequest(
         new EventHandler<WindowEvent>() {
            public void handle(WindowEvent evt) {
               System.exit(0);
            }
         } );
      root = new VBox();
      
      // Format the text area
      root.getChildren().add(taOutput);
      taOutput.setFont(Font.font("Monospaced", 14));
      
      // Visible and do work
      scene = new Scene(root, 600, 200);
      stage.setScene(scene);
      stage.show();
      
      
      //Instance Variables
      int counter = 0;
      
      
      Thread t1 = new ThreadName("Thread 1", 2);
      Thread t2 = new ThreadName("Thread 2", 2);
      Thread t3 = new ThreadName("Thread 3", 2);
      Thread t4 = new ThreadName("Thread 4", 2);
      Thread t5 = new ThreadName("Thread 5", 2);
      
      t1.start();
      t2.start();
      t3.start();
      t4.start();
      t5.start();
   
      
      try 
      {
         t1.join();
         t2.join();
         t3.join();
         t4.join();
         t5.join();
      }
      catch (Exception e)
      { }
      
      
      Thread t = 
         new Thread() {
            public void run() { 
               doWork(); 
            }
         };
      
      t.start();
   }
   
   // Do the work of the program
   public void doWork() {
   
   }
   
   class ThreadName extends Thread
   {
      String thread = "";
      int count = 0;
   
      public ThreadName(String _thread, int _count )
      {
         thread = _thread;
         count = _count;
      }
   
      public void run()
      { 
         
         for (count = 2; count <= 50; count += 2)
         {
            //for (int i = 0; i <= 100; i++)
            //{
               synchronized (list)
               {
                  list.add(count);
                  System.out.println(thread + "\tcount: " + count /*list.get(i)*/);
                  yield();
               }
               try 
               {
                  Thread.sleep(200);
               }
               catch (Exception e) {}
            
          //  }
         }
         if (count == 52)
         { 
            count -= 2;
            System.out.println("if confirmed");
         }
         
      }
      
   }
      
   
}
   
   
