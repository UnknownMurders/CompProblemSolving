import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;

/**
 * ButtonRunnable ... class to represent one button
 * @author Pete Lutz
 * @version 10-30-2017
 */
 
public class ButtonRunnable extends Button implements Runnable  {
   private String name;
   private int colorIndex;
   private String color;
   private boolean keepGoing = true;
   int counter = 0;

   
   
   public ButtonRunnable(String _name, int _colorIndex) { 
      name = _name;
      colorIndex = _colorIndex;
      color = Animate1.colors[colorIndex];
      Platform.runLater(
         new Runnable() 
         {
            public void run() 
            {
               setText(name);
               setStyle("-fx-background-color: " + "#ff0000");
            //             setOpaque(true);
            }
         } );
   }
   
   public void run() {
      keepGoing = true;
      while(keepGoing) 
      {  
      
         try 
         {
            Thread.currentThread().sleep((int) (Math.random() * 1000));
         }
         
         catch(InterruptedException ie) { }
         if (colorIndex == 0)
         {
            colorIndex = 1 ;
            color = Animate1.colors[colorIndex];
            System.out.println("Color Index: " + colorIndex + "\t Color: " + color);
            Platform.runLater(
               new Runnable() {
                  public void run() { setStyle("-fx-background-color: "+ color); }
               } );
         
         }
         else if (colorIndex == 1)
         {
            colorIndex = 2;
            color = Animate1.colors[colorIndex];
            System.out.println("Color Index: " + colorIndex + "\t Color: " + color);
            Platform.runLater(
               new Runnable() {
                  public void run() { setStyle("-fx-background-color: "+ color); }
               } );
         }
         else if (colorIndex == 2)
         {
            colorIndex = 3 ;
            color = Animate1.colors[colorIndex];
            System.out.println("Color Index: " + colorIndex + "\t Color: " + color);
            Platform.runLater(
               new Runnable() {
                  public void run() { setStyle("-fx-background-color: "+ color); }
               } );
         
         }
         else if (colorIndex == 3)
         {
            counter++;
            colorIndex = 0 ;
            color = Animate1.colors[colorIndex];
            System.out.println("Color Index: " + colorIndex + "\t Color: " + color);
            System.out.println("Counter: " + counter);
            if (counter == 4)
            {
               System.out.println("Everyone did a four");
               System.exit(0);
            }
            Platform.runLater(
               new Runnable() {
                  public void run() { setStyle("-fx-background-color: " + color); }
               } );
            
         }
      }
   }
   
   public void kill() {
   
   
      keepGoing = false;
      Platform.runLater(
               new Runnable() {
                  public void run() { setStyle("-fx-background-color: " + "#ffff00"); }
               } );
   
   }
}