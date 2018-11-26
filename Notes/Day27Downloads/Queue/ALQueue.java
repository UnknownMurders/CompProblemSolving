import java.util.ArrayList;






public class ALQueue<T>
{
  private ArrayList<T> queue = new ArrayList();
  
  public ALQueue() {}
  
  public T dequeue() throws ALQueueException
  {
    if (queue.size() == 0)
      throw new ALQueueException("Dequeue of an empty queue");
    T head = queue.get(0);
    queue.remove(0);
    return head;
  }
  

  public T peek()
    throws ALQueueException
  {
    if (queue.size() == 0)
      throw new ALQueueException("Peek of an empty queue");
    return queue.get(0);
  }
  


  public void enqueue(T newValue)
  {
    queue.add(newValue);
  }
  


  public boolean empty()
  {
    return queue.size() == 0;
  }
  


  public String toString()
  {
    String retVal = "";
    
    if (queue.size() == 0) {
      return "  EMPTY\n";
    }
    if (queue.size() == 1) {
      return "  " + queue.get(0).toString() + " <-- HEAD <-- TAIL\n";
    }
    retVal = "  " + queue.get(0).toString() + " <-- HEAD\n";
    
    for (int i = 1; i < queue.size() - 1; i++) {
      retVal = retVal + "  " + queue.get(i).toString() + "\n";
    }
    retVal = retVal + "  " + queue.get(queue.size() - 1).toString() + " <-- TAIL\n";
    return retVal;
  }
}
