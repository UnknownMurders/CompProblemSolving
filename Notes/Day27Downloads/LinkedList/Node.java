class Node<T>
{
  private T data;
  private Node<T> link;
  
  public Node(T _data)
  {
    data = _data;
    link = null;
  }
  
  public T getData() { return data; }
  public void setData(T _data) { data = _data; }
  public Node<T> getLink() { return link; }
  public void setLink(Node<T> _link) { link = _link; }
  
  public void insertAfter(Node<T> newNode) {
    newNode.setLink(getLink());
    setLink(newNode);
  }
}
