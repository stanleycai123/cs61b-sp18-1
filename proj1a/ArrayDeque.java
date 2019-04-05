/**
 * Created by JunhaoW on 04/03/2019
 */

public class ArrayDeque<T> {

  /** Field */
  private int size;
  private int nextFirst;
  private int nextLast;
  private T[] items;

  private int initSize = 4;

  /**
   * Constructor
   */
  public ArrayDeque() {
    items = (T[]) new Object[initSize];
    int mid = items.length / 2;
    nextFirst = mid - 1;
    nextLast = mid;
    size = 0;
  }

  /**
   * API Method
   */

  /** size */
  public int size() {
    return size;
  }

  /** get */
  public int get(int index) {
    return 0;
  }

  /** resize */
  public void resize(int capacity) {
    T[] newItems = (T[]) new Object[capacity];
    //  0  1  2  3  4  5  6  7
    //       [x][x][x][x]       items (copy array)
    // [ ][ ][ ][ ][ ][ ][ ][ ] newItems
    //    [x][x][x]   if initSize is an odd number
    // [ ][ ][ ][ ][ ][ ]
    int startPos = newItems.length / 2 - size / 2; /* draw a picture */
    /** Copy */
    /* // stupid way
    int oldIndex = (nextFirst + 1 > size - 1) ? (0) : (nextFirst + 1);
    // if nextF points at the last position, it means we need to start from 0
    */
    int oldIndex = nextFirst + 1; /* Use MOD operation instead */
    int newIndex = startPos; /* newIndex for newItems */
    int count = 0;
    while (count < size) { /* yes! the original size! */
      newItems[newIndex] = items[oldIndex % size]; /* 3 + 1 = 4, 4 % 4 = 0 */
      oldIndex++; newIndex++;
      count++;
    }
    /** Set new fields */
    items = newItems;
    // size = capacity; bug! we don't need to change size!
    nextFirst = startPos - 1;
    nextLast = newIndex; /* or nextFirst + size */
  }

  /** addFirst */
  public void addFirst(T item) {
    if (size == items.length) { /** FULL! */
      resize(items.length * 2);
    }
    items[nextFirst] = item;
    nextFirst = ((nextFirst - 1) + items.length) % items.length;
    /* since in java, -5 % 6 == -5, we need to add items.length */
    size++;
  }

  /** addLast */
  public void addLast(T item) {
    if (size == items.length) {
      resize(items.length * 2);
    }
    items[nextLast] = item;
    nextLast = (nextLast + 1) % items.length; /* nextLast += 1 */
    size++;
  }

  /** removeFirst */
  public T removeFirst() {
    return null;
  }

  /** removeLast */
  public T removeLast() {
    return null;
  }

  /** printDeque */
  public void printDeque() {

  }

  /**
   * getItem - for testing
   */
  public T getItem(int index) {
    return items[index];
  }
  public int getLength() {
    return items.length;
  }
}
