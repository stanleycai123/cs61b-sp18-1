/**
 * Created by JunhaoW on 05/14/2019
 */

public class MyHeap <Key extends Comparable> {

  private Node root;

  /** Node class */
  private class Node {
    Key key;
    Node left;
    Node right;
    int size;
    int height;

    public Node(Key k) {
      key = k;
      left = null;
      right = null;
      size = 1;
      height = 0;
    }
  } // Node class ending here

  public MyHeap() {
    root = null;
  }

  public int size() {
    return size(root);
  }

  private int size(Node x) { // helper method - make code better
    if (x == null) return 0;
    return x.size;
  }

  public int height() {
    return height(root);
  }

  private int height(Node x) { // helper method
    if (x == null) return 0;
    return x.height;
  }

  /**
   * Add a new key
   * @example
   * 1) x = root = null, 0 node
   * 2) x = root, 1 node (with 2 null children)
   * 3) x = root, 2 nodes (with left child)
   */
  public void add(Key key) {
    root = add(root, key);
  }

  private Node add(Node x, Key key) { // helper method
    if (x == null) {
      return new Node(key);
    }

    // go to left - it's not full
    if (x.left == null || isBottomFull(x.left, x.height) == false) {
      x.left = add(x.left, key);
    }
    // go to right
    else if (x.right == null || isBottomFull(x.right, x.height) == false) {
      x.right = add(x.right, key);
    }
    // both full - insert to the left-most position at a new level
    else {
      x.left = add(x.left, key);
    } /* can't be both empty */

    // Don't write code like: x.size = 1 + x.left.size + x.right.size;
    x.size = 1 + size(x.left) + size(x.right);
    x.height = 1 + Math.max(height(x.left), height(x.right));

    x = promote(x);

    return x;
  }

  /**
   * Is the subtree full of nodes? (e.g. isBottomFull(x.left))
   * @param x the subtree is not {@code null}
   * @param ht the height of x's parent
   */
  private boolean isBottomFull(Node x, int ht) {
    if (x == null) return true;
    int fullSize = (int) Math.pow(2, ht) - 1;
    return x.size == fullSize;
  }
  //      0 (the height fo x's parent height == 2)
  //    /   \
  //   1     3 (x == 3; ht == 2; fullSize == 3; emptySize == 1)
  //  / \   / \
  // 5  10 7   5 (with 7, 5, it's full; without both, it's empty)
  /**
   * Is the subtree empty (no bottom nodes)?
   * @param x the subtree
   * @param ht the height of x's parent
   */
  private boolean isBottomEmpty(Node x, int ht) {
    if (x == null) return true;
    int emptySize = (int) Math.pow(2, ht - 1) - 1;
    return x.size == emptySize;
  }

  /**
   * Promote the node's children if necessary
   * @param x with possible two children is not {@code null}
   * @example
   *    3 (x)
   *   / \
   *  4   2   <= 2 should be promoted
   *  @note why is it correct? See my drawing below
   *  https://bloggg-1254259681.cos.na-siliconvalley.myqcloud.com/5uvp4.png
   */
  private Node promote(Node x) {
    if (x == null) return null;
    if (x.left != null && x.left.key.compareTo(x.key) < 0) {
      swap(x, x.left);
    }
    if (x.right != null && x.right.key.compareTo(x.key) < 0) {
      swap(x, x.right);
    }
    return x;
  }


  /**
   * getSmallest & remove!
   */
  public Key getSmallest() {
    if (root == null) return null;
    Key theSmallestKey = root.key;
    root = getSmallest(root);

    // demote the root
    demote(root);

    return theSmallestKey;
  }

  private Node getSmallest(Node x) { // helper method
    if (x == null) return null;
    // reach a leaf node or size == 1
    if (x.left == null && x.right == null) {
      // replace the root
      root.key = x.key;
      return null; // remove
    }

    // left is full
    if (x.left == null || isBottomFull(x.left, x.height)) {
      if (x.right == null || isBottomEmpty(x.right, x.height)) {
        // right's bottom is empty? - yes => go to left
        x.left = getSmallest(x.left);
      } else {
        // right is not empty || is full (both) => go to right
        x.right = getSmallest(x.right);
      }
    }
    // left is not full => go to left
    else { // left won't be empty, otherwise the height is different
      x.left = getSmallest(x.left);
    }

    x.size = 1 + size(x.left) + size(x.right);
    x.height = 1 + Math.max(height(x.left), height(x.right));

    return x;
  }

  /**
   * Demote a node to the bottom if necessary
   */
  private void demote(Node x) {
    /* Check property */
    if (checkProperty(x) == true) { // including null cases
      return;
    }

    /* Need swapping */
    if (x.right == null) { /* when size == 2 */
      swap(x, x.left);
      return; /* no more children */
    }

    // Get the smallest. A (parent) - B - C
    // Why? We know A is not the smallest because checkProperty() wasn't passed.
    // So if we find the min(B, C), the min is definitely the min of A, B, and C.
    if (x.left.key.compareTo(x.right.key) < 0) { // left < right => goto left
      swap(x, x.left);
      demote(x.left);
    } else { // left >= right => goto right
      swap(x, x.right);
      demote(x.right);
    }
  }

  private boolean checkProperty(Node x) {
    if (x == null) return true; /* when size == 0 */
    if (x.left == null && x.right == null) return true; /* when size == 1 */
    if (x.right == null) { /* when size == 2 */
      return x.key.compareTo(x.left.key) < 0;
    }
    /* when size >= 3 */
    return x.key.compareTo(x.left.key) < 0 && x.key.compareTo(x.right.key) < 0;
  }

  private void swap(Node x1, Node x2) {
    Key tmp = x1.key;
    x1.key = x2.key;
    x2.key = tmp;
  }


  public static void main(String[] args) {

  }
}