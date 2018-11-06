import java.util.concurrent.locks.*;

/** Example found at: 
 * https://www.tutorialspoint.com/java/java_thread_synchronization.htm
 */
class PrintDemo {
   public void printCount() {
      try {
         for(int i = 5; i > 0; i--) {
            System.out.println(Thread.currentThread().getName() + " Counter   ---   "  + i );
         }
      }catch (Exception e) {
         System.out.println("Thread  interrupted.");
      }
   }
}

class ThreadDemo extends Thread {
   PrintDemo  PD;
   Lock myLock;

   ThreadDemo( String name,  PrintDemo pd, Lock _myLock) {
      this.setName(name);
      PD = pd;
      myLock = _myLock;
   }
   
   public void run() {
      try {
         myLock.lock();
         PD.printCount();
         System.out.println("Thread " +  this.getName() + " exiting.");
      }
      catch(Exception e) {
         // do what you need to do to handle the exception
      }
      finally {
         myLock.unlock();
      }
   }
}

public class TestThread3 {
   public static void main(String args[]) {
   
      PrintDemo PD = new PrintDemo();
      Lock myLock = new ReentrantLock();
   
      ThreadDemo T1 = new ThreadDemo( "Thread - 1 ", PD, myLock );
      ThreadDemo T2 = new ThreadDemo( "Thread - 2 ", PD, myLock );
   
      T1.start();
      T2.start();
   
      // wait for threads to end
      try {
         T1.join();
         T2.join();
      }catch( Exception e) {
         System.out.println("Interrupted");
      }
   }
}