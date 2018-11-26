import java.util.*;


public class TestStack {

   private int[] myArray = {5, -1, 10, 23, 4, 8, 2, 25, 7, 0};
   Stack<Integer> myStack = new Stack<Integer>();

   public static void main(String[] args)
   {
      new TestStack();
   }

   public TestStack() {
      {
         System.out.println("Array Contents: " + Arrays.toString(myArray));
         for (int i = 0; i < myArray.length; i++)
         {
            myStack.push(myArray[i]);
         }
         System.out.println("Stack Contents that are all pushed " + myStack.toString());
          
         System.out.print("Removed Data: ");
      
         while (!myStack.empty())
         {
           //System.out.println(myStack.pop() + " ");
            Integer i = myStack.pop();
            System.out.print(i + " ");
         }
         System.out.print("\n");
         
         System.out.println("Stack currently contains: " + myStack.toString());
         
      
      
      }
   }
}