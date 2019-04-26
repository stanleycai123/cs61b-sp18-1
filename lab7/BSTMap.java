import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by JunhaoW on 04/25/2019
 */

public class BSTMap<Key extends Comparable<Key>, Value> implements Map61B<Key, Value> {

  class Node {
    private Key key;
    private Value val;
    private Node left;
    private Node right;
    private int size;

    public Node(Key key, Value value, Node left, Node right, int size) {
      this.key = key;
      this.val = value;
      this.left = left;
      this.right = right;
      this.size = size;
    }
  }

  private Node root;

  public BSTMap() {
    this(null);
  }

  public BSTMap(Node root) {
    this.root = root;
  }

  @Override
  public void clear() {
    root = null;
  }


  @Override
  public boolean containsKey(Key key) {
    if (key == null) throw new IllegalArgumentException();
    return get(key) != null;
  }

  @Override
  public Value get(Key key) {
    return get(root, key);
  }

  private Value get(Node x, Key key) {
    if (key == null) throw new IllegalArgumentException();
    if (x == null) return null; /* base case */
    int cmp = key.compareTo(x.key);
    if (cmp < 0) {
      return get(x.left, key);
    } else if (cmp > 0) {
      return get(x.right, key);
    } else {
      return x.val;
    }
  }

  @Override
  public int size() {
    return size(root);
  }

  private int size(Node x) {
    if (x == null) {
      return 0;
    } else {
      return x.size;
    }
  }

  @Override
  public void put(Key key, Value value) {
    if (key == null) throw new IllegalArgumentException();
    if (value == null) {
      remove(key);
      return;
    }
    root = put(root, key, value);
  }

  private Node put(Node x, Key key, Value value) {
    if (x == null) { /* base case */
      return new Node(key, value, null, null, 1);
    }
    int cmp = key.compareTo(x.key);
    if (cmp < 0) {
      x.left = put(x.left, key, value);
    } else if (cmp > 0) {
      x.right = put(x.right, key, value);
    } else {
      x.val = value;
    }
    // x.size += 1; // seems okay? not for x.val = value (need more code)
    x.size = 1 + size(x.left) + size(x.right);
    return x;
  }

  @Override
  public Set<Key> keySet() {
    Set<Key> set = new TreeSet<>();
    addToSet(root, set);
    return set;
  }

  private void addToSet(Node x, Set<Key> set) {
    if (x == null) return;
    addToSet(x.left, set);
    set.add(x.key);
    addToSet(x.right, set);
  }

  /**
   * Remove
   */
  @Override
  public Value remove(Key key) {
    if (key == null) throw new IllegalArgumentException();
    return remove_helper(key, null); /* specVal = val, always hold true */
  }

  @Override
  public Value remove(Key key, Value value) {
    if (key == null) throw new IllegalArgumentException();
    if (value == null) throw new IllegalArgumentException();
    return remove_helper(key, value);
  }

  private Value remove_helper(Key key, Value specVal) {
    Value foundVal = get(key);
    if (foundVal == null) { return null; } /* key not found */
    if (specVal != null && !foundVal.equals(specVal)) { return null; } /* not matched */
    /* By now, we're sure that: 1. key exists 2. key and val match */
    root = delete(root, key); /* root might be deleted */
    return foundVal;
  }

  /* It's certain that dkey is in T; no need to check value now */
  private Node delete(Node T, Key dKey) {
    if (T == null) { /* T == null and base case */
      return T; /* T is always null */
    }
    int cmp = dKey.compareTo(T.key);
    if (cmp < 0) {
      T.left = delete(T.left, dKey);
    } else if (cmp > 0) {
      T.right = delete(T.right, dKey);
    } else { /* dKey == T.key */
      if (T.left == null || T.right == null) { /* No Children & One Child */
        if (T.left != null) { /* Has a left child */
          T = T.left; /* connect left child to its parent */
        } else { /* Has a right child or no child */
          T = T.right; /* connect to right child or null */
        }
      } else { /* 2 Children */
        Node pred = T.left; // 1. Find pred (largest node in the left subtree)
        while (pred.right != null) { pred = pred.right; }
        T.left = delete(T.left, pred.key); // 2. Delete pred
        T.key = pred.key; // 3. Move pred to T
        T.val = pred.val;
      }
    }
    /* T might be null */
    if (T != null) {
      T.size = 1 + size(T.left) + size(T.right);
    }
    return T;
  }


  @Override
  public Iterator<Key> iterator() {
    return keySet().iterator();
  }


  public void printInOrder() {
    print(root);
    System.out.println();
  }

  private void print(Node x) {
    if (x == null) { return; }
    print(x.left);
    System.out.print(x.key + "(" + x.val + ")" + " ");
    print(x.right);
  }

  public static void main(String[] args) {
    BSTMap map = new BSTMap();
    map.put("dog", 1);
    map.put("bag", 2);
    map.put("flat", 3);
    map.put("alf", 4);
    map.put("cat", 5);
    map.put("abc", 5);
    map.printInOrder();

    // iterable testing
    for (Object k : map) {
      System.out.println(k);
    }
  }
}
