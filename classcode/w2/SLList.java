/**
 * Created by JunhaoW on 03/30/2019
 */

public class SLList {
  public IntNode first;

  public SLList(int x) {
    first = new IntNode(x, null);
  }

  public static void main(String[] args) {

  }

  /** Adds x to the front of the list */
  public void addFirst(int x) {
    /** redundant code
    IntNode newFirst = new IntNode(x, first);
    first = newFirst;
     */
    first = new IntNode(x, first);
  }

  /** Return the first item in the list */
  public int getFirst() {
    return first.item;
  }
}


class IntNode {
  public int item;
  public IntNode next;

  public IntNode(int item, IntNode next) {
    this.item = item;
    this.next = next;
  }

  /** Tricky - addFirst for IntList (IntNode) */
  public void addFirst(int x) {
    // this = new IntNode(x, this); not okay!
    /* change the first object */
    IntNode node2 = new IntNode(item, next);
    item = x;
    next = node2;
  }
}
