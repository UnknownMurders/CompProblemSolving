import java.util.*;


public class TestQueue {

   private int[] myArray = {5, -1, 10, 23, 4, 8, 2, 25, 7, 0};
   Stack<Integer> myQueue = new Stack<Integer>();

   public static void main(String[] args)
   {
      new TestQueue();
   }

   public TestQueue() {
      {
         System.out.println("z Contents: " + Arrays.toString(myArray));
         for (int i = 0; i < myArray.length; i++)
         {
            myQueue.push(myArray[i]);
         }
         System.out.println("Stack Contents that are all pushed " + myQueue.toString());
          
         System.out.print("Removed Data: ");
      
         while (!myQueue.empty())
         {
           //System.out.println(myStack.pop() + " ");
            Integer i = myQueue.pop();
            System.out.print(i + " ");
         }
         System.out.print("\n");
         
         System.out.println("Queue currently contains: " + myQueue.toString());
         
      
      
      }
   }
}