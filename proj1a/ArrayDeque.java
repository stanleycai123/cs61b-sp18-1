/**
 * Created by JunhaoW on 04/03/2019
 */

public class ArrayDeque<T> {

  // private static void main(String[] args) {
  //   ArrayDeque<Integer> d = new ArrayDeque<>();
  //   d.addLast(0);
  //   System.out.println(d.removeFirst());
  // }

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

  /** isEmpty */
  public boolean isEmpty() {
    return (size == 0);
  }

  /** get */
  public T get(int index) {
    if (index < 0 || index >= size) {
      return null;
    }
    int oldIndex = nextFirst + 1; /* will be MODed */
    while (index > 0) {
      oldIndex++;
      index--;
    }
    return items[oldIndex % items.length];
  }

  /** resize */
  private void resize(int capacity) {

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
      newItems[newIndex] = items[oldIndex % items.length]; /* 3 + 1 = 4, 4 % 4 = 0 */
      oldIndex++; newIndex++;
      count++;
    }
    /** Set new fields */
    items = newItems;
    // size = capacity; bug! we don't need to change size!
    nextFirst = startPos - 1;
    nextFirst = startPos - 1;
    nextLast = newIndex; /* or nextFirst + size */
  }

  /** resizeDown */
  private void resizeDown() {
    resize(size * 2 < initSize ? initSize : size * 2);
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
    /** Invariant: nextFirst always points to the previous position of the first element */
    if (isEmpty()) {
      return null;
    }
    /** Resize down */
    if ((double) size / items.length < 0.25 && items.length > initSize) {
      resizeDown();
    }

    nextFirst = (nextFirst + 1) % items.length;
    T removedItem = items[nextFirst];
    items[nextFirst] = null; /* since it is a reference */
    size--;
    return removedItem;
  }

  /** removeLast */
  public T removeLast() {
    //  0  1  2  3
    // [ ][x][x][x]
    // [ ][ ][ ][ ]
    if (isEmpty()) {
      return null;
    }
    /** Resize down */
    if ((double) size / items.length < 0.25 && items.length > initSize) {
      resizeDown();
    }

    nextLast = ((nextLast - 1) + items.length) % items.length;
    T removedItem = items[nextLast];
    items[nextLast] = null;
    size--;
    return removedItem;
  }

  /** printDeque */
  public void printDeque() {
    /* As in resize  */
    int oldIndex = nextFirst + 1;
    int count = 0;
    String str = "";
    while (count < size) {
      T val = items[oldIndex % items.length];
      str += count == size - 1 ? val : (val + " ");
      oldIndex++;
      count++;
    }
    System.out.println(str);
    // return str;
  }

  /**
   * getItem - for testing
   */
  private T getItem(int index) {
    return items[index];
  }
  private int getLength() {
    return items.length;
  }
}
