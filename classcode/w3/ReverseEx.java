/**
 * Created by JunhaoW on 04/03/2019
 */

public class ReverseEx {
  public static void main(String[] args) {

    /**
     * Iterative (passed)
     */
    System.out.println("Iterative:");
    SLList L1 = new SLList(1); L1.addLast(2); L1.addLast(3);
    // SLList L1 = new SLList(1); L1.addLast(2);
    // SLList L1 = new SLList(1);
    System.out.println("Origin = " + L1);
    L1.reverse_my();
    System.out.println("My Way = " + L1);
    L1.reverse_answer(); L1.reverse_answer(); /* Reverse for 2 times */
    System.out.println("Answer = " + L1);

    /**
     * Recursive (passed)
     */
    System.out.println("\nRecursive:");
    SLList L2 = new SLList(1); L2.addLast(2); L2.addLast(3);
    // SLList L2 = new SLList(1); L2.addLast(2);
    // SLList L2 = new SLList(1);
    System.out.println("Origin = " + L2);
    L2.reverse_re_my();
    System.out.println("My Way = " + L2);
    L2.reverse_re_answer(); L2.reverse_re_answer();
    System.out.println("Answer = " + L2);
  }
}

class SLList {

  public IntNode first;

  public SLList(int x) {
    this.first = new IntNode(x, null);
  }

  public void addLast(int x) {
    IntNode ptr = first;
    if (ptr == null) {
      ptr = new IntNode(x, null);
      return;
    }
    while (ptr.next != null) {
      ptr = ptr.next;
    }
    ptr.next = new IntNode(x, null);
  }


  /**
   * My solution
   */

  public void reverse_my() {
    IntNode p = first;
    IntNode prev = null; /* The ending of the new list */
    while (p != null && p.next != null) {
      IntNode nextPtr = p.next.next;
      p.next.next = p;
      IntNode temp = p.next;
      p.next = prev;
      prev = temp;
      p = nextPtr; /* Update for the next round */
    }
    if (p == null) {
      first = prev;
    } else {
      p.next = prev;
      first = p;
    }
  }

  /**
   * Answer
   */
  // first -1-> ptr -2-> ptr.next (temp) -3-> ptr.next.next
  public void reverse_answer() {
    if (first == null || first.next == null) {
      return;
    }
    IntNode ptr = first.next;
    first.next = null; /* -1-> */

    while (ptr != null) {
      IntNode temp = ptr.next;
      ptr.next = first; /* -2-> */
      first = ptr; /* changing -3-> needs first */
      ptr = temp;
    }
  }


  /**
   * My solution (recursive)
   */
  public void reverse_re_my() {
    reverseHelper_my(first); /* don't use the return value */
  }

  private IntNode reverseHelper_my(IntNode fst) {
    if (fst == null || fst.next == null) { /* 1 or 2 nodes only */
      /* e.g., fst is pointing at a3 now. Since a3.next is null, a3 is the new head */
      /* Remember set first.next before setting a new head */
      first.next = null;
      first = fst;
      return fst; /* returns a3 */
    }
    IntNode retNode = reverseHelper_my(fst.next);
    /* In the first round, fst.next == a2 */
    /* In the last round, retNode is a3 */
    retNode.next = fst;
    return fst;
  }


  /**
   * Answer (recursive)
   */
  public void reverse_re_answer() {
    first = reverseHelper_answer(first);
  }

  private IntNode reverseHelper_answer(IntNode fst) {
    if (fst == null || fst.next == null) {
      return fst;
    } else {
      IntNode endOfReversed = fst.next;
      IntNode reversed = reverseHelper_answer(fst.next);
      endOfReversed.next = fst; /* a2.next = fst (a1) */
      fst.next = null; /* a1.next originally points at a2 */

      return reversed;
    }
  }

  private static class IntNode {

    public int item;
    public IntNode next;

    public IntNode(int i, IntNode r) {
      item = i;
      next = r;
    }
  }

  @Override
  public String toString() {
    String str = "";
    IntNode ptr = first;
    while (ptr != null) {
      str = str + ptr.item + " ";
      ptr = ptr.next;
    }
    return str;
  }
}

