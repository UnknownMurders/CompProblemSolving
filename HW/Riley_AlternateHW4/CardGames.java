public class CardGames
{

   public static void main(String [] args)
   {
      FirstThread t1 = new FirstThread();
      SecondThread t2 = new SecondThread();
      int counterToTen = 0;
      
      while (counterToTen >= 10)
      {
      t1.start();
      t2.start();
      counterToTen++;
       System.out.println(counterToTen);
      }     
      try
      {
         t1.join();
      }
      catch (InterruptedException ie)
      {
         System.out.println("T1 Interrupted Exception: " + ie.toString());
      }
      catch (Exception e)
      {
         System.out.println("T1 FATAL ERROR: " + e.toString());
      }
      try
      {
         t2.join();
      }
      catch (InterruptedException ie)
      {
         System.out.println("T2 Interrupted Exception: " + ie.toString());
      }
      catch (Exception e)
      {
         System.out.println("T2 FATAL ERROR: " + e.toString());
      }
   
      
      
      
      System.out.println("All done (main)");
   }
      
} 



class FirstThread extends Thread 
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
class SecondThread extends Thread 
{
    
   public void run() 
   {
      for (int counterTwo = 20; counterTwo >= 0; counterTwo -= 1)
      {
         System.out.println("        "+counterTwo);
      }
   }
}





