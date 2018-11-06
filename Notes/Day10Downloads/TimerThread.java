import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.text.*;
import java.util.Scanner;
import java.io.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.Arrays;
import java.lang.*;

public class TimerThread extends Thread 
{
   private TextField tfTimer = null;
   
   /** constructor */
   public TimerThread(TextField _tfTimer)
   {
      tfTimer = _tfTimer;
   }
   
   /** run - the main program for the thread */
   public void run()
   {
      int time = 0;  // Count seconds
      
      while (true) 
      
         try 
         {
            Thread.currentThread().sleep(1000); // wait for 1 second
         // Update the timer
            time++;
            tfTimer.setText(String.format("%02d:%02d", (time / 60), (time % 60)));
         }
         catch(InterruptedException ie) 
         {
         
         }
         
        
   }
}

