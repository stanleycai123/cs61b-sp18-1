/**
 * Created by JunhaoW on 04/01/2019
 */

public class LinkedListDeque<T> {

  /** main */
  public static void main(String[] args) {
    LinkedListDeque<Integer> D = new LinkedListDeque<>(5);
    System.out.println(D);
  }

  /**
   * Node
   */
  private class Node {

    public T item;
    public Node prev;
    public Node next;

    public Node(T i) {
      item = i;
    }

    public Node(T i, Node p, Node n) {
      item = i;
      prev = p;
      next = n;
    }

    public T get(int index) {
      if (index == 0) {
        return item;
      }
      return next.get(index--);
    }
  }


  /** Field */
  public int size;
  public Node sentinel; /** Circular Sentinel */


  /**
   * Constructor
   */
  /** Empty Deque */
  public LinkedListDeque() {
    sentinel = new Node(null);
    sentinel.prev = sentinel;
    sentinel.next = sentinel;
    size = 0;
  }

  /** One-Node Deque */
  public LinkedListDeque(T item) {
    this();
    sentinel.next = new Node(item, sentinel, sentinel);
    sentinel.prev = sentinel.next;
    size = 1;
  }


  /**
   * API Method
   */
  /** isEmpty */
  public boolean isEmpty() {
    return (size == 0);
  }

  /** size */
  public int size() {
    return size;
  }

  /** get */
  public T get(int index) {
    if (isEmpty()) { /* you can remove "if", and put count < size in the "while" */
      return null;
    }
    Node p = sentinel; /* start from 0 */
    /** If p = sentinel.next, count should be 1 */
    int count = 0;
    while (count <= index) {
      count += 1;
      p = p.next;
    }
    return p.item;
  }

  /** getRecursive */
  public T getRecursive(int index) {
    if (isEmpty()) {
      return null;
    }
    /** sentinel.next won't be null */
    return sentinel.next.get(index);
  }


  /**
   * Add
   */
  /** addFirst */
  public void addFirst(T item) {
    Node newNode = new Node(item, sentinel, sentinel.next);
    sentinel.next = newNode;
    if (size == 0) {
      sentinel.prev = newNode;
    } /* Otherwise, no need to change sentinel.prev */
    size += 1;
  }

  /** addLast */
  public void addLast(T item) {
    /** Remember DOUBLY linked list! */
    // Node p = sentinel;
    // int count = 0;
    // while (count < size) {
    //   count += 1;
    //   p = p.next;
    // }
    // p.next = new Node(item, p, sentinel);
    // sentinel.prev = p.next;
    /* if size == 0, p.next == sentinel.next */
    /* if size == 1, p.next == sentinel.next.next */
    /** If there is no sentinel, you need to add "if (first == null)" */
    /** first.prev will crash if first is null */
    // sentinel.prev = new Node(item, sentinel.prev.prev, sentinel);  <- bug: sentinel.prev (old last node)
    sentinel.prev = new Node(item, sentinel.prev, sentinel);
    sentinel.prev.prev.next = sentinel.prev;
    size += 1;
  }


  /**
   * Remove
   */
  /** removeFirst */
  public T removeFirst() {
    if (isEmpty()) { /* because of circular sentinel, removedNode won't be null */
      return null;
    }
    Node removedNode = sentinel.next; /* even without if, these two lines won't crash */
    sentinel.next = removedNode.next;
    removedNode.next.prev = sentinel; /* ok for all size >= 1 */
    size -= 1; /** But "if" decides if this line should be run */
    return removedNode.item;
  }

  /** removeLast */
  public T removeLast() {
    // if (isEmpty()) {
    //   return null;
    // }
    // Node p = sentinel;
    // int count = 0;
    // while (count < size) {
    //   count += 1;
    //   p = p.next;
    // }
    // p.prev.next = p.next; /* p.prev.next = sentinel */
    // p.next.prev = p.prev; /* sentinel.prev = p.prev */
    if (isEmpty()) {
      return null;
    }
    Node removedNode = sentinel.prev;
    sentinel.prev.prev.next = sentinel; /* removedNode.prev.next = sentinel */
    sentinel.prev = sentinel.prev.prev; /* sentinel.prev = removedNode.prev */
    size -= 1;
    return removedNode.item;
  }


  /**
   * Other
   */
  /** printDeque */
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
