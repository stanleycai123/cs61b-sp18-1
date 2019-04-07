/**
 * Created by JunhaoW on 04/01/2019
 */

public class LinkedListDeque<T> implements Deque<T> {

  /**
   * Node
   */
  private class Node { /* should be private */

    public T item;
    public Node prev;
    /**
     * DLList - Doubly Linked List
     */
    public Node next;

    public Node(T i) {
      item = i;
    }

    public Node(T i, Node p, Node n) {
      item = i;
      prev = p;
      next = n;
    }

    /**
     * Helper Method (Recursive)
     */
    public T get(int index) {
      if (index <= 0) {
        return item;
      } else if (this == sentinel) { /* what if index is very large */
        /** Returns null if index is out of bound */
        return null;
      }
      return next.get(index - 1);   /* better */
    }
  }


  /**
   * Field
   */
  private int size;
  private Node sentinel; /** Circular Sentinel */


  /**
   * Constructor
   */
  /**
   * Empty Deque
   */
  public LinkedListDeque() {
    sentinel = new Node(null);
    sentinel.prev = sentinel;
    sentinel.next = sentinel;
    size = 0;
  }


  /**
   * API Method
   */
  /**
   * isEmpty
   */
  @Override
  public boolean isEmpty() {
    return (size == 0);
  }

  /**
   * size
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * get
   */
  // 0 1 2 3 4 5
  // 3 5 9 5 3 8
  @Override
  public T get(int index) {
    // if (isEmpty()) { return null; }
    /* could be removed, since we set sentinel.item == null */
    Node p = sentinel.next; /* the 1st real node */
    while (index > 0 && p != sentinel) { /* p != sentinel stops outofbound cases */
      p = p.next;
      index--;
    }
    return p.item; /* when index is out of bound, returns null (sentinel) */
  }

  /**
   * getRecursive
   */
  public T getRecursive(int index) {
    /** sentinel.next won't be null */
    Node firstNode = sentinel.next;
    return firstNode.get(index); /* Node.get */
  }


  /**
   * Add
   */
  /**
   * addFirst
   */
  @Override
  public void addFirst(T item) {
    Node newNode = new Node(item, sentinel, sentinel.next);
    sentinel.next = newNode;
    newNode.next.prev = newNode;
    size += 1;
  }

  /**
   * addLast
   */
  @Override
  public void addLast(T item) {
    /** Compatible with empty list */
    Node newNode = new Node(item, sentinel.prev, sentinel);
    sentinel.prev = newNode;
    newNode.prev.next = newNode;
    size += 1;
  }

  /**
   * Remove
   */
  /**
   * removeFirst
   */
  @Override
  public T removeFirst() {
    if (isEmpty()) { /* because of circular sentinel, removedNode won't be null */
      return null; /* just in case size -= 1, return is okay since sentinel.item is null */
    }
    Node removedNode = sentinel.next; /* even without if, these two lines won't crash */
    sentinel.next = removedNode.next;
    removedNode.next.prev = sentinel; /* ok for all size >= 1 */
    size -= 1; /** But "if" decides if this line should be run */
    return removedNode.item;
  }

  /**
   * removeLast
   */
  @Override
  public T removeLast() {
    if (isEmpty()) {
      return null;
    }
    /** Usually, when using a circular sentinel and the list is empty, both sides of the equation are pointing at the sentinel! Just do useless work */
    Node removedNode = sentinel.prev; /** Use some local variables */
    removedNode.prev.next = sentinel;
    sentinel.prev = removedNode.prev;
    size -= 1;
    return removedNode.item;
  }


  /**
   * Other
   */
  /**
   * printDeque
   */
  @Override
  public void printDeque() {
    /* No need to check null */
    Node p = sentinel.next;
    int count = 0;
    while (count < size) {
      String separator = (count < size - 1) ? " " : "";
      System.out.print(p.item + separator);
      count += 1;
      p = p.next;
    }
  }
}
