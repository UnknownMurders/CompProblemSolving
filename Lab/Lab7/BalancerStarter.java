import java.util.*;

public class BalancerStarter {
   private String balancedEquation = "-{ [ b * b - (4 * a * c) ] / (2 * a) }";
   // -{ [ b * b - (4 * a * c) ] / (2 * a) } -> TEST CASE 0 - NO ERRORS - BALANCED EQUATION 
   // -{ [ b * b - [4 * a * c) ] / (2 * a) } -> TEST CASE 1 - MISMATCHED CHARACTERS - UNBALANCED EQUATION
   // - [ { [ b * b - (4 * a * c) ] / (2 * a) } -> TEST CASE 2 - TOO MANY OPENING ( { [ - UNBALANCED EQUATION
   // -{ [ b * b - [4 * a * c) ] / (2 * a) } ] -> TEST CASE 3 - TOO MANY CLOSING ) } ] - UNBALANCED EQUATION


   public static void main(String[] args) {
      new BalancerStarter();
   }
   
   public BalancerStarter() {
      boolean balanced = true;
      String balancedEquation = "-{ [ b * b - (4 * a * c) ] / (2 * a) }";
   
      Stack<Character> stack = new Stack<Character>();
   
     
      for (int i = 0; i < balancedEquation.length(); i++)
      {
         Character c = new Character(balancedEquation.charAt(i));
         if ( c == '{' || c == '[' || c == '(')
         {
            stack.push(c);
            
            System.out.println(c + " has been pushed in!");
            System.out.println("Stack content: " + stack.toString());
         }
         else if ( c == '}' || c == ']' || c == ')')
         {
            if (stack.isEmpty())
            {
               System.out.println("Stack is Empty - Killing Program");
               System.exit(1);
            }
            else
            {
               stack.pop();
               
               System.out.println(c + " has been popped out!");
               System.out.println("Stack content: " + stack.toString());
            }
         
         }
      
      }
   }
}

