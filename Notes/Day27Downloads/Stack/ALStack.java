import java.util.ArrayList;






public class ALStack<T>
{
  private ArrayList<T> stack = new ArrayList();
  
  public ALStack() {}
  
  public T pop() throws ALStackException
  {
    if (stack.size() == 0)
      throw new ALStackException("Pop of an empty stack");
    T top = stack.get(stack.size() - 1);
    stack.remove(stack.size() - 1);
    return top;
  }
  

  public T peek()
    throws ALStackException
  {
    if (stack.size() == 0)
      throw new ALStackException("Peek of an empty stack");
    return stack.get(stack.size() - 1);
  }
  


  public void push(T newValue)
  {
    stack.add(newValue);
  }
  


  public boolean empty()
  {
    return stack.size() == 0;
  }
  


  public String toString()
  {
    String retVal = "";
    
    if (stack.size() == 0)
      return "  EMPTY\n";
    retVal = "  " + stack.get(stack.size() - 1).toString() + " <-- TOP\n";
    
    for (int i = stack.size() - 2; i >= 0; i--) {
      retVal = retVal + "  " + stack.get(i).toString() + "\n";
    }
    return retVal;
  }
}
