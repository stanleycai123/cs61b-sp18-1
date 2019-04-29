/**
 * Created by JunhaoW on 03/30/2019
 */

public class SLList {
  private IntNode first;
  private int size;

  public SLList() {
    first = null;
    size = 0;
  }

  public SLList(int x) {
    first = new IntNode(x, null);
    size = 1;
  }

  public static void main(String[] args) {
    // foo
  }

  /** Adds x to the front of the list */
  public void addFirst(int x) {
    /** redundant code
    IntNode newFirst = new IntNode(x, first);
    first = newFirst;
     */
    first = new IntNode(x, first);
    size += 1;
  }

  /** Return the first item in the list */
  public int getFirst() {
    if (first == null) {
      return -1;
    }
    return first.item;
  }


  /** Delete the first element in the SLList */
  /** Without sentinel */
  public int deleteFirst() {
    if (first == null) {
      return -1;
    }
    IntNode deletedNode = first;
    first = first.next; /* could be null */
    size -= 1;
    return deletedNode.item;
  }


  /** addLast (should be considered when using empty list) */
  public void addLast(int x) {
    size += 1;
    /* Code for special case - empty list */
    if (first == null) {
      first = new IntNode(x, null);
      return;
    }
    IntNode p = first; /* p is not null */
    while (p.next != null) { /* Move p until it reaches the end of the list */
      p = p.next;
    } /* now p points to the last element rather than null */
    p.next = new IntNode(x, null);
  }


  /** size() */
  public int size() {
    return size;
  }

  /** Outdated Version - if there is no instance variable size */
  public int size_oldVersion() {
    IntNode p = first;
    int totalSize = 0;
    while (p != null) {
      totalSize += 1;
      p = p.next;
    }
    return totalSize;
  }

  /** Recursive version
   *  Tricky. 'cause SLList is not recursive!
   */
  public int size_re() { // public method that uses the private method
    return size_re(first);
  }
  /** Returns the size of the list that starts at IntNode p (common pattern) */
  /** Private Helper Method */
  private static int size_re(IntNode p) { /* same name but different signature - overload */
    if (p == null) { /* or p.next == null (consider empty list case) */
      return 0;      /* or return 1; */
    }
    return 1 + size_re(p.next);
  }

  /**
   * IntNode
   * private: cannot access IntNode outside SLList
   * static:  if code in IntNode never looks outwards
   */
  private class IntNode {
    public int item;
    public IntNode next;

    public IntNode(int item, IntNode next) {
      this.item = item;
      this.next = next;
    }

    // IntNode should have addFirst?
    /** Tricky - addFirst for IntList (IntNode) */
    public void addFirst(int x) {
      // this = new IntNode(x, this); not okay!
      /* change the first object */
      IntNode node2 = new IntNode(item, next);
      item = x;
      next = node2;
    }
  }
}
