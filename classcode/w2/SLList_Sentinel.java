/**
 * Created by JunhaoW on 03/31/2019
 */

public class SLList_Sentinel {
  private int size;
  private IntNode sentinel; /* The first item (if it exists) is at sentinel.next */
  /* sentinel should not be changed later (after init) */

  public SLList_Sentinel() {
    sentinel = new IntNode(63, null); /* whatever item is */
    size = 0;
  }

  public SLList_Sentinel(int x) {
    sentinel = new IntNode(63, null);
    sentinel.next = new IntNode(x, null);
    size = 1;
  }

  /**
   * Constructor with array
   */
  public SLList_Sentinel(int[] intArray) {
    this(); /* empty list with a sentinel */
    IntNode p = sentinel; /** Notice to create something you'll need this pattern */
    for (int x : intArray) {
      p.next = new IntNode(x, null);
      p = p.next;
      size += 1;
    }
  }

  public static void main(String[] args) {
    int[] intArray = new int[]{1, 2, 3, 4, 5};
    SLList_Sentinel L = new SLList_Sentinel(intArray);
    System.out.println("L = " + L);

    // System.out.println(L.getFirst()); /* getFirst bug */
  }

  /**
   * Adds x to the front of the list
   */
  public void addFirst(int x) {
    sentinel.next = new IntNode(x, sentinel.next);
    size += 1;
  }

  /**
   * Return the first item in the list
   */
  public int getFirst() {
    return sentinel.next.item; /* BUT could be NullPointer still */
    /* solution */
    // if (sentinel.next == null) {
    //   return -1;
    // } else {
    //   return sentinel.next.item;
    // }
  }

  /** Delete the first element in the SLList */
  /**
   * With sentinel
   */
  public int deleteFirst() {
    /* sentinel.next could be null when size == 0 */
    if (sentinel.next == null) {
      return -1;
    }
    IntNode deletedNode = sentinel.next;
    sentinel.next = sentinel.next.next;
    /* .next.next could be null if size == 1, but it's okay */
    size -= 1;
    return deletedNode.item;
  }


  /**
   * .addLast (should be considered when using empty list)
   */
  public void addLast(int x) {
    /* Code for special case */
    /*if (first == null) {
      first = new IntNode(x, null);
      return;
    }*/
    IntNode p = sentinel; /* absolutely not null */
    /* p.next == null, if there is an empty list*/
    while (p.next != null) {
      p = p.next;
    }
    p.next = new IntNode(x, null);
    size += 1;
  }


  /**
   * .size()
   */
  public int size() {
    return size;
  }

  /**
   * Outdated Version
   */
  public int size_oldVersion() {
    IntNode p = sentinel.next;
    int totalSize = 0;
    while (p != null) {
      totalSize += 1;
      p = p.next;
    }
    return totalSize;
  }

  /**
   * Recursive version
   * Tricky. 'cause SLList is not recursive!
   */
  public int size_re() { // public method that uses the private method
    return size(sentinel.next);
  }
  /** Returns the size of the list that starts at IntNode p (common pattern) */
  /**
   * Private Helper Method
   */
  private static int size(IntNode p) { /* same name but different signature - overload */
    if (p == null) { /* or p.next == null (consider empty list case) */
      return 0;      /* or return 1; */
    }
    return 1 + size(p.next);
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

    /**
     * Tricky - addFirst for IntList (IntNode)
     */
    public void addFirst(int x) {
      // this = new IntNode(x, this); not okay!
      /* change the first object */
      IntNode node2 = new IntNode(item, next);
      item = x;
      next = node2;
    }
  }
}
