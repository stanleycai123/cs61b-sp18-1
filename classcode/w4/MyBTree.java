import java.util.ArrayList;
import java.util.List;

/**
 * Created by JunhaoW on 05/06/2019
 */

public class MyBTree<Key extends Comparable<Key>, Value> {
  /**
   * 2-3 tree: Order-of-3, 3 children max
   * 2-3-4 tree: Order-of-4, 4 children max
   * Compatible for more-order trees
   */
  /* Note: Manually set ORDER for testing different trees */
  private static int ORDER = 3; /* 3-order tree */
  // private static int ORDER = 4; /* 4-order tree */

  private Node root;
  private int height;
  private int n;

  /* B-tree Node */
  private static class Node {
    private int m;    // number of items; #children = m + 1
    private Entry[] children = new Entry[ORDER + 1]; /* Extra temp position */
    // children store items (Entries); Entries store real children (nexts)
    /** E.g. for node (8, 9)
     *   0  1  2  3  (index)
     *  x1  8  9 x2
     *  x1 (item: null, val: null, next: points to the previous item of 8)
     *  x2 (temp for splitting)
     */

    // create a node with k items
    private Node(int k) {
      m = k;
      children[0] = new Entry(null, null, null);
      /* this is the left child of the first item */
    }
  }

  private static class Entry {
    private Comparable key;
    private final Object val;
    private Node next; /* right child */

    public Entry(Comparable key, Object val, Node next) {
      this.key = key;
      this.val = val;
      this.next = next;
    }
  }

  /**
   * Initialize an empty B-tree
   */
  public MyBTree() {
    root = new Node(0);
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  /**
   * Number of nodes
   */
  public int size() {
    return n;
  }

  /**
   * For debug
   */
  public int height() {
    return height;
  }

  /**
   * Get
   */
  public Value get(Key key) {
    if (key == null) throw new IllegalArgumentException();
    return search(root, key, height);
  }

  // helper
  private Value search(Node x, Key key, int ht) {
    // leaf node - null link
    if (ht == 0) {
      for (int j = 1; j < x.m; j++) {
        if (equal(key, x.children[j].key)) {
          return (Value) x.children[j].val;
        }
      }
    }
    // internal node
    else {
      for (int j = 0; j < x.m; j++) { /* how many times for comparison */
        if (j == x.m - 1 || less(key, x.children[j + 1].key)) { /* break */
          return search(x.children[j].next, key, ht - 1);
        }
      }
    }
    return null;
  }

  /**
   * Put
   */
  public void put(Key key, Value val) {
    if (key == null) throw new IllegalArgumentException();

    root = insert(root, key, val, height);

    // need to split root
    if (root.m == ORDER) {
      Node newRoot = new Node(0);

      Node left = root;
      Node right = new Node(0);
      newRoot.children[1] = splitNode(left, right);
      // left
      newRoot.children[0].next = left;
      // right
      newRoot.children[1].next = right;
      // height
      newRoot.m++; /* because added mid */
      height++;
      root = newRoot;
    }
  }

  // helper
  private Node insert(Node x, Key key, Value val, int ht) {
    // internal node
    if (ht > 0) {
      for (int j = 0; j < x.m + 1; j++) {
        if (j == x.m || less(key, x.children[j + 1].key)) {
          Node next = insert(x.children[j].next, key, val, ht - 1);

          // split the next node if necessary (later we'll connect splitted nodes to current node x)
          if (next.m == ORDER) {
            /** E.g.
             *  // ORDER = 3
             *   0   1   2   3
             *  24  25  26      25, ceil(3 / 2.0) - 1 = 1
             *  // ORDER = 4
             *  24  25  26  27 -   25, ceil(4 / 2.0) - 1 = 1
             *  int mid = (int) Math.ceil(ORDER / 2.0) - 1;
             */
            Node left = next;
            Node right = new Node(0); // O: 3 -> 1,   O:4 -> 2
            Entry midEntry = splitNode(left, right);
            // left - how to link the two nodes? Bravo!
            x.children[j].next = left;
            // right
            midEntry.next = right;
            // insert midEntry
            for (int i = x.m; i >= j + 1; i--) { /* move (>=j+1) nodes to behind */
              x.children[i + 1] = x.children[i];
            }
            x.children[j + 1] = midEntry; /* insert */
            x.m++;
          }
          break;
        }
      }
    }
    // leaf / external node
    else {
      Entry t = new Entry(key, val, null); // to insert
      int ind; /* remember considering 1st-node insertion */
      for (ind = 1; ind < x.m + 1; ind++) { /* locate the position */
        if (less(key, x.children[ind].key)) {
          break;
        }
      }
      // insert
      for (int i = x.m; i >= ind; i--) { /* move nodes to behind */
        x.children[i + 1] = x.children[i];
      }
      x.children[ind] = t; /* insert */
      x.m++; /* because added mid */
      n++; /* size */
    }
    return x;
  }

  /** left node is the original node */
  private Entry splitNode(Node left, Node right) {
    int mid = (int) Math.ceil(ORDER / 2.0);
    Entry midEntry = left.children[mid];
    /* set mid entry.next to the right node 0-entry's next */
    right.children[0].next = midEntry.next;
    left.children[mid] = null; /* relieve left's ownership of mid node */
    midEntry.next = null; /* it's safe because it has been pointed */
    left.m--;
    /* move from rightSide to the right node */
    for (int k = mid + 1, count = 1; k < ORDER + 1; k++, count++) {
      right.children[count] = left.children[k];
      /* count starts from 1, the 0 is for mid.next */
      left.children[k] = null;
      left.m--; right.m++;
    }
    return midEntry;
  }

  /**
   * Help debug
   */
  public void showTree() {
    if (n == 0) {
      System.out.println("Tree is empty!");
      return;
    }

    List<Node>[] data = new ArrayList[height + 1];
    // Init
    for (int i = 0; i < data.length; i++) {
      data[i] = new ArrayList<>();
    }
    addNodeToData(root, data, 0);

    /* print */
    for (int i = 0; i < data.length; i++) { /* layer */
      List<Node> nodes = data[i];
      // System.out.println(nodes.size());
      for (Node x : nodes) { /* node */
        for (int j = 1; j < x.m + 1; j++) {
          if (j != x.m) {
            System.out.print(x.children[j].key + ",");
          } else {
            System.out.print(x.children[j].key);
          }
        }
        System.out.print("  ");
      }
      System.out.println();
    }
    System.out.println("---- END ----");
  }

  // helper
  private void addNodeToData(Node x, List<Node>[] data, int depth) {
    if (depth > height) return;
    List<Node> L = data[depth];
    L.add(x);
    Entry[] children = x.children;
    for (int j = 0; j < x.m + 1; j++) {
      if (children[j] != null) {
        addNodeToData(children[j].next, data, depth + 1);
      }
    }
  }

  // helper
  private boolean less(Comparable k1, Comparable k2) {
    return k1.compareTo(k2) < 0;
  }

  private boolean equal(Comparable k1, Comparable k2) {
    return k1.compareTo(k2) == 0;
  }

  public static void main(String[] args) {
    test1(); // order = 3 - in order
    // test2(); // order = 3 - not in order
    // test3(); // order = 4 - remember manually setting ORDER to 4
  }

  private static void test1() {
    /** Remember to set ORDER!!! */
    MyBTree<String, Integer> st = new MyBTree<String, Integer>();
    sizeHeight(st);
    st.showTree();

    add(st, "A", 1);
    add(st, "B", 2);
    add(st, "C", 3);
    add(st, "D", 4);
    add(st, "E", 5);
    add(st, "F", 6);
    add(st, "G", 7);
  }

  private static void test2() {
    /** Remember to set ORDER!!! */
    MyBTree<String, Integer> st = new MyBTree<String, Integer>();
    sizeHeight(st);
    st.showTree();

    add(st, "D", 4);
    add(st, "C", 3);
    add(st, "E", 5);
    add(st, "B", 2);
    add(st, "F", 6);
    add(st, "A", 1);
    add(st, "G", 7);
  }

  private static void test3() {
    /** Remember to set ORDER!!! */
    MyBTree<String, Integer> st = new MyBTree<String, Integer>();
    sizeHeight(st);
    st.showTree();

    add(st, "A", 1);
    add(st, "B", 2);
    add(st, "C", 3);
    add(st, "D", 4);
    add(st, "E", 5);
    add(st, "F", 6);
    add(st, "G", 7);
  }

  // decorator
  private static void add(MyBTree<String, Integer> T, String key, Integer val) {
    T.put(key, val);
    sizeHeight(T);
    T.showTree();
  }

  private static void sizeHeight(MyBTree T) {
    System.out.println("size: " + T.size() + ", height: " + T.height());

  }
}










