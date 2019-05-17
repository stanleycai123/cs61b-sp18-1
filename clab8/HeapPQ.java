import java.util.Comparator;

/**
 * @date 05/17/2019
 * @author Junhao Wang
 */

public class HeapPQ<Item> {

  private Item[] items;
  private int size; // size always points to the last item
  private Comparator<Item> comparator;

  public HeapPQ(int size, Comparator<Item> comparator) {
    this.items = (Item[]) new Object[size + 1]; // start from 1
    this.size = 0;
    this.comparator = comparator;
  }

  /** Size */
  public int size() {
    return size;
  }

  /** Get parent for item k */
  private int getParentPos(int k) {
    return k / 2;
  }

  /** Get left child for item k */
  private int getLeftPos(int k) { // return -1 if no left child
    return (k * 2 <= size) ? (k * 2) : -1;
  }

  /** Get right child for item k */
  private int getRightPos(int k) { // return -1 if no right child
    return (k * 2 + 1 <= size) ? (k * 2 + 1) : -1;
  }

  /**
   * Swap
   */
  private void swap(int k1, int k2) {
    Item temp = items[k1];
    items[k1] = items[k2];
    items[k2] = temp;
  }

  /**
   * Add a item into the heap
   */
  public void add(Item it) {
    int pos = size + 1;
    items[pos] = it;
    size += 1;
    promote(pos);
  }

  /**
   * Promote an item to the top if necessary
   * MinPQ's comparator: parent > child => cmp > 0
   * MaxPQ's comparator: parent < child => cmp > 0
   */
  private void promote(int childPos) {
    int parentPos = getParentPos(childPos);
    if (parentPos == 0) return;
    int cmp = comparator.compare(items[parentPos], items[childPos]);
    if (cmp > 0) { // not okay!
      swap(parentPos, childPos); // swap
      promote(parentPos); // check its parent
    }
  }

  /**
   * Get a smallest / largest item from the heap
   */
  public Item get() {
    if (items == null && size == 0) {
      System.out.println("Empty heap! (get)");
      return null; /* empty */
    }
    return items[1];
  }

  /**
   * Remove a smallest / largest item from the heap
   */
  public Item remove() {
    if (items == null && size == 0) {
      System.out.println("Empty heap! (remove)");
      return null;
    }

    // replace the root with the last item
    Item retItem = items[1];
    items[1] = items[size];
    items[size] = null;
    size -= 1;

    // demote
    demote(1);

    return retItem;
  }

  /**
   * Demote an item to the bottom if necessary
   * MinPQ's comparator: parent > child => cmp > 0
   * MaxPQ's comparator: parent < child => cmp > 0
   */
  private void demote(int parentPos) {
    if (checkProperty(parentPos) == true) {
      return;
    }

    int leftPos = getLeftPos(parentPos);
    int rightPos = getRightPos(parentPos);

    // Need swapping!
    // only has left node
    if (leftPos != -1 && rightPos == -1) {
      swap(parentPos, leftPos);
    }
    // have two children
    else {
      int cmp = comparator.compare(items[leftPos], items[rightPos]);
      if (cmp < 0) { // MinPQ: leftChild < rightChild // MaxPQ: leftChild > rightChild
        swap(parentPos, leftPos);
        demote(leftPos);
      } else {
        swap(parentPos, rightPos);
        demote(rightPos);
      }
    }
  }

  private boolean checkProperty(int parentPos) {
    int leftPos = getLeftPos(parentPos);
    int rightPos = getRightPos(parentPos);

    if (leftPos == -1 && rightPos == -1) return true; // leaf node
    if (leftPos != -1 && rightPos == -1) { // only has left node
      int cmp = comparator.compare(items[parentPos], items[leftPos]);
      return (cmp < 0); // cmp < 0 => true, no more demotion
    }

    // have two children
    int leftCmp = comparator.compare(items[parentPos], items[leftPos]);
    int rightCmp = comparator.compare(items[parentPos], items[rightPos]);
    return (leftCmp < 0) && (rightCmp < 0);
  }


  /** Just for testing */
  public void display() {
    int[] indArray = new int[items.length];
    for (int i = 0; i < indArray.length; i += 1) {
      indArray[i] = i;
    }
    System.out.print("Index:  ");
    for (int i = 0; i < size + 1; i += 1) {
      System.out.printf("%-6d", indArray[i]);
    }
    System.out.println();
    System.out.print("Items:  ");
    for (int i = 0; i < size + 1; i += 1) {
      System.out.printf("%-6s", items[i] == null ? "null" : items[i].toString());
    }
    System.out.println();

  }


  /**
   * For Testing
   */

  public static void main(String[] args) {
    // testMin();
    // testMax();

    // Testing comparator for Flight class
    Comparator<Flight> minComp = (i, j) -> i.passengers() - j.passengers();
    int cmp = minComp.compare(new Flight(0, 0, 3), new Flight(0, 0, 4));
    System.out.println(cmp);

    Comparator<Flight> maxComp = (i, j) -> j.passengers() - i.passengers();
    cmp = maxComp.compare(new Flight(0, 0, 9), new Flight(0, 0, 4));
    System.out.println(cmp);
  }

  private static void testMin() {
    System.out.println("--------- testMin() ---------");
    int N = 100;
    Comparator<Integer> intMinComparator = (i, j) -> i.compareTo(j);
    HeapPQ<Integer> minPQ = new HeapPQ<>(N, intMinComparator);
    addTest(minPQ, 10);
    addTest(minPQ, 3);
    addTest(minPQ, 5);
    addTest(minPQ, 20);
    addTest(minPQ, 1);
    removeTest(minPQ);
  }

  private static void testMax() {
    System.out.println("--------- testMax() ---------");
    int N = 100;
    Comparator<Integer> intMaxComparator = (i, j) -> j.compareTo(i);
    HeapPQ<Integer> maxPQ = new HeapPQ<>(N, intMaxComparator);
    addTest(maxPQ, 10);
    addTest(maxPQ, 3);
    addTest(maxPQ, 5);
    addTest(maxPQ, 20);
    addTest(maxPQ, 1);
    removeTest(maxPQ);
  }

  private static <T> void addTest(HeapPQ<T> pq, T t) {
    System.out.println("Printing Items with Indices... (add: " + t + ")");
    pq.add(t);
    pq.display();
  }

  private static <T> void removeTest(HeapPQ<T> pq) {
    System.out.println("Printing Items with Indices... (remove: " + pq.get() + ")");
    pq.remove();
    pq.display();
  }
}
