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

public class BinaryIO2 extends Application {
   // Window attributes
   Stage stage;
   Scene scene;
   VBox root;
   
   int jerseyNumber = 0;
   String name = "";
   String position = "";
   String birthday = "";
   double weight = 0;
   int age = 0;
   String experience = "";
   String college = "";
   int count = 0;
   DataInputStream dis = null;
   FileInputStream fis = null;
   String lightestPlayer = "NULL";
   double lightestWeight = 0;
   double secondLightestWeight = 0;
   String lightestPosition = "NULL";
   double totalPlayerAge = 0;
   double averagePlayerAge = 0;
   ArrayList<Double> list = new ArrayList<Double>();
   
   
   // GUI Components
   private TextArea taOutput = new TextArea();

   // Main program
   public static void main(String[] args) {
      launch(args);
   }
   
   // Callback to start the GUI
   public void start(Stage _stage) {
      try 
      { 
         fis = new FileInputStream("BBRosterBIN.dat");
         dis = new DataInputStream(fis);
         
         while (true)
         {
            jerseyNumber = dis.readInt();
            name = dis.readUTF();
            position = dis.readUTF();
            birthday = dis.readUTF();
            weight = dis.readDouble();
            age = dis.readInt();
            experience = dis.readUTF();
            college = dis.readUTF();
            count++;
            totalPlayerAge += age;
            
            
            if (170 > lightestWeight)
            {
               
               lightestWeight = weight;
               lightestPlayer = name;
               lightestPosition = position;
            }
            else
            {
            
            }
            
         }
      
      
      }
      catch (Exception e)
      {
         //System.out.println("There are  " + count + " players on the roster.");
         
         averagePlayerAge = totalPlayerAge / count;
         
        // System.out.println("The lightest player is " + lightestPlayer + " weight: " + lightestWeight + " lbs  position : " + lightestPosition);  
        // System.out.println(String.format("The average player age is "+ averagePlayerAge));
        // System.out.println(String.format("The average player age is %2f", averagePlayerAge));
                    
                    
         
         taOutput.appendText("There are " + count + " players on the roster.\n");
         taOutput.appendText("The lightest player is " + lightestPlayer + " weight: " + lightestWeight + " lbs  position : " + lightestPosition + "\n");
         taOutput.appendText(String.format("The average player age is %2f", averagePlayerAge));
      }
   
      try
      {  
         dis.close();
         System.out.println("DataInputStream Check Close #1");
      }
      catch (Exception e)
      {
         System.out.println("DIS failed to close.");
      }
      
   
   
   
   
   
   
   
   
      // Setup window
      stage = _stage;
      stage.setTitle("Practical 1");
      stage.setOnCloseRequest(
         new EventHandler<WindowEvent>() {
            public void handle(WindowEvent evt) {
               
               try 
               {
                  dis.close();
                  System.out.println("DataInputStream Check Close #2");
                  System.out.println("DataInputStream successfully closed.");
               }
               catch (Exception e)
               {
                  System.out.println("DIS failed to close.");
               }
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
      
      
   }
}