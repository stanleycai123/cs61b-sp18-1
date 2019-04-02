/**
 * Array based list.
 *
 * @author Josh Hug
 */

public class AList {

  int[] items;
  int size;

  private final int MAX = 100;

  /**
   * Creates an empty list.
   */
  public AList() {
    items = new int[MAX];
    size = 0;
  }

  /**
   * Inserts X into the back of the list.
   */
  public void addLast(int x) {
    if (size < MAX) {
      items[size] = x;
      size += 1;
    }
  }

  /**
   * Returns the item from the back of the list.
   */
  public int getLast() {
    if (size > 0) {
      return items[size - 1];
    }
    return -1;
  }

  /**
   * Gets the ith item in the list (0 is the front).
   */
  public int get(int i) {
    if (size > 0 && i >= 0 && i < size) {
      return items[i];
    }
    return -1;
  }

  /**
   * Returns the number of items in the list.
   */
  public int size() {
    return size;
  }

  /**
   * Deletes item from back of the list and
   * returns deleted item.
   */
  public int removeLast() {
    if (size > 0) {
      size -= 1;
      return items[size];
    }
    return -1;
  }
} 