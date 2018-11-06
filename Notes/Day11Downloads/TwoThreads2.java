/**
 * Two Threads - in class exercise
 * @author Pete Lutz
 * @version 10-23-2017
 */
public class TwoThreads2 {
   public static void main(String[] args) {
      FirstThread t1 = new FirstThread();
      SecondThread t2 = new SecondThread();
      
      t1.start();
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
   
      t2.start();
           
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
   
class FirstThread extends Thread {
   public void run() {
      for(int count = 1; count <= 10; count++) {
         System.out.println("" + count);
         yield();
      }
      System.out.println("All done (FirstThread)");
   }
}

class SecondThread extends Thread {
   public void run() {
      for(int count = 20; count >= 2; count -= 2) {
         System.out.println("               " + count);
         yield();
      }
      System.out.println("All done (SecondThread)");
   }
}
