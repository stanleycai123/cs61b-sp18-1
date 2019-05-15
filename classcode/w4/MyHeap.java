/**
 * Created by JunhaoW on 05/14/2019
 */

public class MyHeap <Key extends Comparable> {

  private Node root;

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
  }

  public MyHeap() {
    root = null;
  }

  public int size() {
    if (root == null) return 0;
    return root.size;
  }

  public int height() {
    if (root == null) return 0;
    return root.height;
  }


  /**
   * Insert
   */
  public void insert(Key key) {
    root = insert(root, key);
  }

  private Node insert(Node x, Key key) {
    if (x == null) {
      return new Node(key);
    }

    // go to left - it's not full
    if (isFull(x.left, x.height) == false) {
      x.left = insert(x.left, key);
    }
    // go to right
    else if (isFull(x.right, x.height) == false) {
      x.right = insert(x.right, key);
    }
    // both full - insert to the left-most node
    else {
      x.left = insert(x.left, key);
    } /* can't be both empty */

    x.size = 1 + x.left.size + x.right.size;
    x.height = 1 + Math.max(x.left.height, x.right.height);

    x = promote(x);

    return x;
  }

  private boolean isFull(Node x, int ht) { // Is subtree full?
    int fullSize = (int) Math.pow(2, ht) - 1;
    return x.size == fullSize;
  }

  private boolean isEmpty(Node x, int ht) { // does subtree have no nodes at the bottom?
    int emptySize = (int) Math.pow(2, ht - 1) - 1;
    return x.size == emptySize;
  }

  private Node promote(Node x) {
    if (x.left != null && x.left.key.compareTo(x.key) < 0) {
      swap(x, x.left);
    }
    if (x.right != null && x.right.key.compareTo(x.key) < 0) {
      swap(x, x.right);
    }
    return x;
  }

  /**
   * Remove - getSmallest
   */

  public Key getSmallest() {
    if (root == null) return null;
    Key theSmallest = root.key;
    root = getSmallest(root);

    // demote the root
    demote(root);

    return theSmallest;
  }

  private Node getSmallest(Node x) {
    // reach a leaf node (consider size == 1, return null => root = null)
    if (x.left == null && x.right == null) {
      // replace
      root.key = x.key;
      return null; // remove
    }

    // left is full
    if (isFull(x.left, x.height)) {
      if (isEmpty(x.right, x.height)) {
        // right is empty? - yes => go to left
        x.left = getSmallest(x.left);
      } else {
        // right is not empty || is full => go to right
        x.right = getSmallest(x.right);
      }
    }
    // left is not full => go to left
    else { // left won't be empty, otherwise the height is different
      x.left = getSmallest(x.left);
    }

    // x won't be null
    x.size = 1 + x.left.size + x.right.size;
    x.height = 1 + Math.max(x.left.height, x.right.height);

    // x = promote(x); // not necessary
    return x;
  }

  private void demote(Node x) {
    if (x == null) return; /* when size == 1 & getSmallest() => root = null */
    if (x.left == null && x.right == null) return;
    if (x.left != null && x.right == null) {
      if (x.key.compareTo(x.left.key) > 0) {
        swap(x, x.left);
        return;
      }
    }

    // left < right => goto left
    if (x.left.key.compareTo(x.right.key) < 0) {
      swap(x, x.left);
      demote(x.left);
    }
    // left >= right => goto right
    else {
      swap(x, x.right);
      demote(x.right);
    }
  }

  private void swap(Node x1, Node x2) {
    Key tmp = x1.key;
    x1.key = x2.key;
    x2.key = tmp;
  }
}
