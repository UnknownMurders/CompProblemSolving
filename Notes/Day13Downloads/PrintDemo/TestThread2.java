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

   ThreadDemo( String name,  PrintDemo pd) {
      this.setName(name);
      PD = pd;
   }
   
   public void run() {
      synchronized(PD) {
         PD.printCount();
         System.out.println("Thread " +  this.getName() + " exiting.");
      }
   }

}

public class TestThread2 {
   public static void main(String args[]) {

      PrintDemo PD = new PrintDemo();

      ThreadDemo T1 = new ThreadDemo( "Thread - 1 ", PD );
      ThreadDemo T2 = new ThreadDemo( "Thread - 2 ", PD );

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