/*
NAME: Edward Riley
PROFESSOR: Peter Lutz
COURSE: Computation Problem Solving - Information Domain II (Java II)
DATE: 9/17/2018
*/

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


public class ByteIO{   

   public static void main(String[] args){
   
      double total = 0;
      String name = "";
      String id = "";
      int number = 0;
      double grade1, grade2, grade3, grade4;
      double Avg = 0; 
     
      
      
      try
      {
         File filename = new File("ClassList.dat");
         FileInputStream inputStream = new FileInputStream(filename);
         BufferedInputStream bis = new BufferedInputStream(inputStream);
         DataInputStream dis = new DataInputStream(bis);
      
         // Too difficult to try to do it manually - System.out.println("Name \t\t\t\t\tID\t\t\tGrade 1\t\tGrade 2\t\tGrade 3\t\tGrade 4\t\tAverage");
         System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s \n","Name", "ID", "grade1", "grade2", "grade3", "grade4", "avgs");
         
         //Reads as much as it can until what is left of the file exception.          
         while (true)
         {
            
            name = dis.readUTF();
            number = dis.readInt();
            grade1 = dis.readDouble();
            grade2 = dis.readDouble();
            grade3 = dis.readDouble();
            grade4 = dis.readDouble();
         
            total = (grade1 + grade2 + grade3 + grade4);
            
            Avg =  total/4;
            
            System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n",name, number, grade1, grade2, grade3, grade4, String.format("%.2f",Avg));  
         
         }
      }
      //A normal specific "error"
      catch (EOFException eof)
      {
         System.out.println("END OF THE FILE REACHED: " + eof.toString());
      }
      catch (IOException ioe)
      {
         System.out.println("INPUT/OUTPUT ERROR: " + ioe.toString());
      }
      //In case something immediately goes wrong
      catch(Exception e) 
      {
         System.out.print("FATAL ERROR: " + e.toString() + "\t SEEK HELP IMMEDIATELY");
      }
   }
}
