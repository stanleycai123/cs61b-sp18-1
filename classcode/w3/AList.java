/**
 * Array based list.
 *
 * @author Josh Hug
 */

//         0 1  2 3 4 5 6 7 ...
// items: [6 9 -1 0 0 0 0 0 ...]
// size: 3

public class AList<T> {

  private T[] items;
  private int size;

  /**
   * Creates an empty list.
   */
  public AList() {
    items = (T[]) new Object[100];
    size = 0;
  }

  /**
   * Inserts X into the back of the list.
   */
  public void addLast(T x) {
    if (size >= items.length) {
      resize(size * 2);
    }
    items[size] = x;
    size += 1;
  }

  /**
   * Resize the underlying array to the target capacity.
   */
  private void resize(int capacity) {
    T[] a = (T[]) new Object[capacity];
    System.arraycopy(items, 0, a, 0, size); /* or items.length */
    items = a;
  }

  /**
   * Returns the item from the back of the list.
   */
  public T getLast() {
    if (size > 0) {
      return items[size - 1];
    }
    return null;
  }

  /**
   * Gets the ith item in the list (0 is the front).
   */
  public T get(int i) {
    if (size > 0 && i >= 0 && i < size) {
      return items[i];
    }
    return null;
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
  public T removeLast() {
    if (size > 0) {
      size -= 1;
      T removedItem = items[size];
      items[size] = null;
      return removedItem;
    }
    return null;
  }
} 