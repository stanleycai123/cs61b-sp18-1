/**
 * @date 05/25/2019
 * @author Junhao Wang
 */
package bearmaps.proj2ab;

import edu.princeton.cs.algs4.Stopwatch;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

  public static void main(String[] args) {
    Stopwatch sw = new Stopwatch();
    ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();
    pq.add(10, 1);
    pq.add(20, 2);
    pq.add(30, 3);
    pq.add(40, 4);
    printItem(pq);
    pq.removeSmallest();
    printItem(pq);
    pq.add(0, 0);
    pq.add(50, 8);
    pq.add(60, 11);
    pq.add(70, 13);
    pq.add(80, 19);
    pq.add(90, 22);
    pq.add(100, 29);
    pq.add(110, 30);
    pq.add(120, 55);
    pq.add(130, 9);
    printItem(pq);
    pq.changePriority(40, 1);
    printItem(pq);
    pq.changePriority(100, -1);
    printItem(pq);
    System.out.println(sw.elapsedTime() + " s");
  }

  public static void printItem(ArrayHeapMinPQ<Integer> pq) {
    Integer[] items = pq.itemArray();
    System.out.println("----------------");
    PrintHeapDemo.printSimpleHeapDrawing(items);
  }


  /**
   * Class: PriorityNode
   */
  private class PriorityNode<Item> implements Comparable<PriorityNode<Item>> {
    Item item;
    double priority;
    int index;

    PriorityNode(Item e, double p, int i) {
      this.item = e;
      this.priority = p;
      this.index = i;
    }

    Item getItem() {
      return item;
    }

    double getPriority() {
      return priority;
    }

    int getIndex() {
      return index;
    }

    void setPriority(double p) {
      this.priority = p;
    }

    void setIndex(int i) {
      this.index = i;
    }

    @Override
    public int compareTo(PriorityNode<Item> o) {
      if (o == null) {
        return -1;
      }
      return Double.compare(this.getPriority(), o.getPriority());
    }

    @Override
    public boolean equals(Object o) {
      if (o == null || o.getClass() != this.getClass()) {
        return false;
      } else {
        return ((PriorityNode<Item>) o).getItem().equals(getItem());
      }
    }

    @Override
    public int hashCode() {
      return item.hashCode();
    }
  }

  /**
   * Fields
   */
  private final static int INIT_SIZE = 10;
  private final static int RESIZE_FACTOR = 2;
  private final static double SHRINK_LIMIT = 0.75;

  private int size;
  private PriorityNode<T>[] items;  // starts from 1 ~ size
  private Map<T, PriorityNode<T>> itemMap; // for contains(), hashCode of T

  /**
   * Constructor
   */
  public ArrayHeapMinPQ() {
    // items = (PriorityNode<T>[]) new Object[INIT_SIZE + 1]; // error
    // or
    // items = new ArrayHeapMinPQ.PriorityNode[INIT_SIZE + 1]; // ok
    items = (PriorityNode<T>[]) new PriorityNode[INIT_SIZE + 1];

    itemMap = new HashMap();
    size = 0;
  }

  /**
   * Add
   * size always points to the last item
   */
  @Override
  public void add(T item, double priority) {
    if (item == null) return;
    if (contains(item)) throw new IllegalArgumentException(); // if already exists

    // resize if necessary
    if (isFull()) { // isFull?
      resize(size * RESIZE_FACTOR);
    }

    // add to heaps
    size += 1;
    items[size] = new PriorityNode(item, priority, size); // add to the rightmost pos

    // add to itemMap
    itemMap.put(item, items[size]);

    swim(size);
  }

  private boolean isFull() {
    return size == items.length - 1;
  }

  /**
   * Swim
   */
  private void swim(int k) {
    if (k <= 1) return;
    int cmp = items[k].compareTo(items[parent(k)]);
    if (cmp < 0) {
      swap(k, parent(k));
      swim(parent(k));
    }
  }

  /**
   * Sink
   *    6      6        6
   *   / \    / \      /  \
   *  4  3   3  null  null null
   */
private void sink(int k) {
    if (leftChild(k) > size) return; // leaf node

    int cmp = items[leftChild(k)].compareTo(items[rightChild(k)]); // compareTo(null) return -1
    if (cmp < 0) { // 1. left < right 2. right is null
      swap(k, leftChild(k)); // left
      sink(leftChild(k));
    } else {
      swap(k, rightChild(k)); // right
      sink(rightChild(k));
    }
  }

  /**
   * Resize (compatible with shrinkage)
   */
  private void resize(int capacity) {
    PriorityNode<T>[] newItems = (PriorityNode<T>[]) new PriorityNode[capacity + 1];

    for (int i = 1; i <= size(); i += 1) {
      newItems[i] = items[i];
    }
    this.items = newItems;
  }

  /**
   * Contains
   */
  @Override
  public boolean contains(T item) {
    return itemMap.containsKey(item);
  }

  private PriorityNode<T> getNodeWithItem(T item) {
    return itemMap.get(item);
  }


  @Override
  public T getSmallest() {
    if (size == 0) throw new NoSuchElementException();
    return items[1].getItem();
  }

  @Override
  public T removeSmallest() {
    if (size == 0) throw new NoSuchElementException();

    // remove from heap
    swap(1, size); // swap root and the rightmost
    PriorityNode<T> removedNode = items[size];
    items[size] = null; // avoid hanging
    size -= 1;
    sink(1); // size = 0 is well-handled

    // remove from itemMap
    itemMap.remove(removedNode.getItem());

    // shrink if necessary - items.length > 11 && > 0.75
    int emptyNum = items.length - 1 - size();
    if (items.length > (INIT_SIZE + 1) && emptyNum / ((double) items.length - 1) > SHRINK_LIMIT) {
      resize(size / RESIZE_FACTOR);
    }

    return removedNode.getItem();
  }

  @Override
  public void changePriority(T item, double priority) {
    if (item == null) return;
    if (contains(item) == false) throw new NoSuchElementException();
    // contains get
    PriorityNode<T> node = getNodeWithItem(item);
    node.setPriority(priority);
    sink(node.getIndex());
    swim(node.getIndex());
  }

  private void swap(int x1, int x2) {
    PriorityNode<T> temp = items[x1];
    items[x1] = items[x2];
    items[x2] = temp;
    // swap indices
    items[x1].setIndex(x1);
    items[x2].setIndex(x2);
  }

  @Override
  public int size() {
    return size;
  }

  private int parent(int k) {
    return k / 2;
  }

  private int leftChild(int k) {
    return k * 2;
  }

  private int rightChild(int k) {
    return k * 2 + 1;
  }

  /**
   * For testing
   */
  private Integer[] itemArray() {
    Integer[] ret = new Integer[size + 1];
    for (int i = 1; i <= size; i += 1) {
      ret[i] = (int) items[i].getPriority();
    }

    return ret;
  }
}
