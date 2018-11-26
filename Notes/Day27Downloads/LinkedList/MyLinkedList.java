public class MyLinkedList<T> {
  private Node<T> head;
  
  public MyLinkedList() {
    head = null;
  }
  
  public Node<T> getHead() { return head; }
  public void setHead(Node<T> _head) { head = _head; }
  
  public String toString() {
    int index = -1;
    String retVal = "START(-1) --> ";
    index++;
    Node<T> current = head;
    while (current != null) {
      retVal = retVal + current.getData().toString() + "(" + index + ") --> ";
      current = current.getLink();
      index++;
    }
    retVal = retVal + "END";
    return retVal;
  }
}
