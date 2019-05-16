import java.util.NoSuchElementException;

/**
 * Created by JunhaoW on 05/11/2019
 * Reference: https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/AVLTreeST.java.html
 */

public class AVLTree<Key extends Comparable<Key>, Value> {
  /**
   * The root node.
   */
  private Node root;

  /**
   * This class represents an inner node of the AVL tree.
   */
  private class Node {
    private final Key key;
    private Value val;
    private int height;
    private int size;
    private Node left;
    private Node right;

    public Node(Key key, Value val, int height, int size) {
      this.key = key;
      this.val = val;
      this.size = size;
      this.height = height;
    }
  } // Node class

  /**
   * Empty Constructor.
   */
  public AVLTree() {}

  /**
   * Checks if the symbol table is empty.
   * @return {@code true} if the symbol table is empty
   */
  public boolean isEmpty() {
    return root == null;
  }

  /**
   * Returns the number of pairs in the symbol table.
   * @return the number of pairs in the symbol table
   */
  public int size() {
    return size(root);
  }

  /**
   * Returns the number of nodes in the subtree.
   * @param x the subtree
   * @return the number of nodes in the subtree
   */
  private int size(Node x) {
    if (x == null) return 0;
    return x.size;
  }

  /**
   * Returns the height of the internal AVL tree.
   * It is assumed that the height of an empty tree is -1,
   * and the height of a tree with just one node is 0.
   * @return the height of the internal AVL tree
   */
  public int height() {
    return height(root);
  }

  /**
   * Returns the height of the subtree.
   * @param x the subtree
   * @return the height of the subtree
   */
  private int height(Node x) {
    if (x == null) return -1;
    return x.height;
  }

  /**
   * Returns the value associated with the given item.
   * @param key the item
   * @return the value associated with the given item if the item is in the
   *         symbol table and {@code null} if the item is not in the
   *         symbol table
   * @throws IllegalArgumentException if {@code item} is {@code null}
   */
  public Value get(Key key) {
    if (key == null) throw new IllegalArgumentException("argument to get() is null");
    Node x = get(root, key);
    if (x == null) return null;
    else return x.val;
  }

  /**
   * Returns value associated with the given item in the subtree or
   * {@code null} if no such item
   * @param x the subtree
   * @param key the item
   * @return value associated with the given item in the subtree or
   *         {@code null} if no such item
   */
  private Node get(Node x, Key key) {
    if (x == null) return null;
    int cmp = key.compareTo(x.key);
    if (cmp < 0)
      return get(x.left, key);
    else if (cmp > 0)
      return get(x.right, key);
    else
      return x;
  }

  /**
   * Checks if the symbol table contains the given item.
   * @param key the item
   * @return {@code true} if the symbol table contains {@code item}
   *         and {@code false} otherwise
   * @throws IllegalArgumentException if {@code item} is {@code null}
   */
  public boolean contains(Key key) {
    return get(key) != null;
  }

  /**
   * Inserts the specified item-value pair into the symbol table,
   * overwriting the old value with the new value if the symbol table already
   * contains the specified item. Deletes the item if the specified value
   * is {@code null}
   * @param key the item
   * @param val the value
   * @throws IllegalArgumentException if {@code item} is {@code null}
   */
  public void put(Key key, Value val) {
    if (key == null) throw new IllegalArgumentException("first argument to put() is null");
    if (val == null) {
      delete(key); return;
    }
    root = put(root, key, val);
  }

  /**
   * Insert Helper
   * @param x the subtree
   * @param key the item
   * @param val the value
   * @return the subtree
   */
  private Node put(Node x, Key key, Value val) {
    if (x == null) return new Node(key, val, 0, 1);
    int cmp = key.compareTo(x.key);
    if (cmp < 0) {
      x.left = put(x.left, key, val);
    } else if (cmp > 0) {
      x.right = put(x.right, key, val);
    } else { /* found */
      x.val = val;
      return x;
    }
    x.size = 1 + size(x.left) + size(x.right);
    x.height = 1 + Math.max(height(x.left), height(x.right));
    return balance(x);
  }

  /**
   * Restores the AVL tree property of the subtree
   *
   * @param x the subtree
   * @return the subtree with restored AVL property
   * @Visual https://bloggg-1254259681.cos.na-siliconvalley.myqcloud.com/fcjdy.png
   */
  private Node balance(Node x) {
    // RR, RL
    if (balanceFactor(x) < -1) {
      if (balanceFactor(x.right) > 0) { // or if (item < x.right.item)
        x.right = rotateRight(x.right); // RL
      }
      x = rotateLeft(x); // RR
    }
    // LL, LR
    else if (balanceFactor(x) > 1) {
      if (balanceFactor(x.left) < 0) { // or if (item > x.left.item)
        x.left = rotateLeft(x.left); // LR
      }
      x = rotateRight(x); // LL
    }
    return x;
  }

  /**
   * Returns the balance factor of the subtree.
   * @param x the subtree
   * @return the balance factor of the subtree
   */
  private int balanceFactor(Node x) {
    return height(x.left) - height(x.right);
  }

  /**
   * Rotates the given subtree to the right.
   * @param x the subtree
   * @return the right rotated subtree
   */
  private Node rotateRight(Node x) {
    // notice the order
    Node h = x.left; // new boss
    x.left = h.right;
    h.right = x;
    // update sizes
    h.size = x.size;  // new boss
    x.size = 1 + size(x.left) + size(x.right);
    // update heights
    x.height = 1 + Math.max(height(x.left), height(x.right));
    h.height = 1 + Math.max(height(h.left), height(h.right));
    return h;
  }

  /**
   * Rotates the given subtree to the left.
   * @param x the subtree
   * @return the left rotated subtree
   */
  private Node rotateLeft(Node x) {
    // notice the order
    Node h = x.right; // new boss
    x.right = h.left;
    h.left = x;
    // update sizes
    h.size = x.size; // new boss
    x.size = 1 + size(x.left) + size(x.right);
    // update heights;
    x.height = 1 + Math.max(height(x.left), height(x.right));
    h.height = 1 + Math.max(height(h.left), height(h.right));
    return h;
  }

  /**
   * Removes the specified item and its associated value from the symbol table
   * @param key the item
   * @throws IllegalArgumentException if {@code item} is {@code null}
   */
  public void delete(Key key) {
    if (key == null) throw new IllegalArgumentException("argument to delete() is null");
    if (!contains(key)) return;
    root = delete(root, key);
  }

  /**
   * Removes the specified item and its associated value from the symbol table
   * @param x the subtree
   * @param key the item
   * @return the updated subtree
   */
  private Node delete(Node x, Key key) {
    int cmp = key.compareTo(x.key);
    if (cmp < 0) {
      x.left = delete(x.left, key);
    }
    else if (cmp > 0) {
      x.right = delete(x.right, key);
    }
    else {
      if (x.left == null) {
        return x.right; /* x.right might be null too */
      }
      else if (x.right == null) {
        return x.left;
      }
      else {
        Node y = x;
        x = min(y.right);
        x.left = y.left;
        x.right = deleteMin(y.right);
      }
    }
    x.size = 1 + size(x.left) + size(x.right);
    x.height = 1 + Math.max(height(x.left), height(x.right));
    return balance(x);
  }

  /**
   * Removes the smallest item and associated value from the symbol table
   * @throws java.util.NoSuchElementException if the symbol table is empty
   */
  public void deleteMin() {
    if (isEmpty()) throw new NoSuchElementException();
    root = deleteMin(root);
  }

  /**
   * @param x the subtree
   * @return the updated subtree
   */
  private Node deleteMin(Node x) {
    if (x == null) return x.right; /* x.item is the smallest */
    x.left = deleteMin(x.left);
    x.size = 1 + size(x.left) + size(x.right);
    x.height = 1 + Math.max(height(x.left), height(x.right));
    return balance(x);
  }

  /**
   * Returns the smallest item in the symbol table.
   * @return the smallest item in the symbol table
   * @throws NoSuchElementException if the symbol table is empty
   */
  public Key min() {
    if (isEmpty()) throw new NoSuchElementException();
    return min(root).key;
  }

  /**
   * Returns the node with the smallest item in the subtree.
   * @param x the subtree
   * @return the node with the smallest item in the subtree
   */
  private Node min(Node x) {
    if (x.left == null) return x; /* x.item is the smallest */
    return min(x.left);
  }

}
