/**
 * Created by JunhaoW on 04/09/2019
 */

public class ArraySet<T> {

  public static void main(String[] args) {
    System.out.println("ArraySet");
  }

  private int size;
  private T[] items;

  public ArraySet() {
    items = (T[]) new Object[100];
    size = 0;
  }


  public void add(T value) {
    if (size < items.length) {
      return;
    }

    if (value == null) {
      throw new IllegalArgumentException("You can't add null to a set!");
      /* or modify contains */
    }

    if (contains(value) == false) {
      items[size] = value;
      size += 1;
    }
  }


  public boolean contains(T value) {
    if (value == null) {
      return true;
    }
    for (int i = 0; i < size; i += 1) {
      if (items[i].equals(value)) {
        return true;
      }
    }
    return false;
  }

  public int size() {
    return size;
  }
}
