/*
Student: Andrew Grubbs
Date: October 12, 2017
Midterm Exam: Thread
Instructor: Dan Kennedy
*/

import java.util.*;
import java.lang.*;

public class ThreadExam 
{
   public static int count = 1;
   
      
   // Main 
   public static void main(String [] args){
      new ThreadExam();
   }
   
   public ThreadExam()
   {
      
      Thread t1 = new ThreadName("Thread 1");
      Thread t2 = new ThreadName("Thread 2");
      Thread t3 = new ThreadName("Thread 3");
      Thread t4 = new ThreadName("Thread 4");
      Thread t5 = new ThreadName("Thread 5");
      
      t1.start();
      t2.start();
     
      t3.start();
      //t3.yield();
      t4.start();
      //t4.yield();
      t5.start();
      //t5.yield();
          
            
      System.out.println("Ending count is " + --count);    
   }
   
   
   class ThreadName extends Thread {
   
      private String newName;
      
      
      // Pass _name to newName
      public ThreadName(String _name)
      {
         newName = _name;
      }
      // Run from thread
      public void run()
      {
         yield();
         //for(count = 20; count >= 0; count -=2)
         
         
         for(int count = 20; count >= 0; count -= 2)
         {  
         
               //System.out.println(newName + " counter " + count);
            synchronized("lock")
            {
            
               System.out.println(newName + " counter " + count);
            } 
            try {
               Thread.sleep(100);
            }
            catch(InterruptedException ie) {}
         }  
         
      }
      
   }
}