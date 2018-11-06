import java.util.*; // for Date
public class QuickThread
{
   public static void main(String [] args)
   {
      MyThreadZeroClass t0 = new MyThreadZeroClass(); //Thread-0
      MyThreadOneClass t1 = new MyThreadOneClass(); //Thread-1
     // MyTh t3 = new MyTh(); //Thread-2
      
      t0.start();
      t1.start();
      try 
      {
         t0.join();
         t1.join();
      }
      catch (Exception e)
      {
         System.out.println("ERROR: " + e);
      }
   
      System.out.println("Main is Done");
      
   } 
}



class MyThreadZeroClass extends Thread 
{
  // int counterOne = 0;
   public void run() 
   {
      for (int counterOne = 1; counterOne <= 10; counterOne++)
      {
         System.out.println(counterOne);
         yield();
      
      }
   }   
}
class MyThreadOneClass extends Thread 
{
    
   public void run() 
   {
      for (int counterTwo = 20; counterTwo >= 0; counterTwo -= 1)
      {
         System.out.println("        "+counterTwo);
         yield();
      }
   }
}