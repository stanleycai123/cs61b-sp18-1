import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @date 05/22/2019
 * @author Junhao Wang
 */

public class MyTrieSet implements TrieSet61B {
  private Node root;

  public MyTrieSet() {
    root = new Node(false);
  }

  private static class Node {
    private boolean isKey;
    private Map<String, Node> next;

    private Node(boolean b) {
      isKey = b;
      next = new HashMap<>();
    }

    public void setKey(boolean k) {
      isKey = k;
    }
  }

  /**
   * Clear
   */
  @Override
  public void clear() {
    root = new Node(false);
  }

  /**
   * If it contains a key
   */
  @Override
  public boolean contains(String key) {
    if (key == null || key.length() == 0) return false;
    Node p = root;
    for (int i = 0; i < key.length(); i += 1) {
      boolean end = (i == key.length() - 1);
      String sub = Character.toString(key.charAt(i));
      Map<String, Node> map = p.next;
      if (map.containsKey(sub) == false) return false;  // fall off
      p = map.get(sub);  // get the current node
      if (end && p.isKey) return p.isKey;  // end checking
    }
    return false;
  }

  /**
   * Add
   */
  @Override
  public void add(String key) {
    if (key == null || key.length() == 0) return;
    Node p = root;
    for (int i = 0; i < key.length(); i += 1) {
      boolean end = (i == (key.length() - 1));
      String sub = Character.toString(key.charAt(i));
      Map<String, Node> map = p.next;
      if (map.containsKey(sub) == false) {
        map.put(sub, new Node(end));  // add new node
      }
      p = map.get(sub); // get and set current
      if (end) {  // last node: Not exist (added); Exist (update the key)
        p.setKey(true);
      }
    }
  }

  /**
   * keysWithPrefix
   * @return empty list if prefix doesn't match
   */
  @Override
  public List<String> keysWithPrefix(String prefix) {
    // Find the node a
    Node p = root;
    for (int i = 0; i < prefix.length(); i += 1) {
      String sub = Character.toString(prefix.charAt(i));
      p = p.next.get(sub);  // current node
      if (p == null) break;
    } // now p points to the node a or null

    List<String> x = new ArrayList<>();
    if (p == null) return x;
    colHelp(prefix, x, p);  // p points to node a
    return x;
  }

  /**
   * Helper function: Add all keys with prefix {@code s} to {@code x}
   * @param pf the prefix
   * @param x the return list
   * @param n the node that represents that last character of {@code pf}
   */
  private void colHelp(String pf, List<String> x, Node n) {
    if (n == null) return;
    if (n.isKey) {
      x.add(pf);
    }
    for (String s : n.next.keySet()) {
      // System.out.println(s);
      colHelp(pf + s, x, n.next.get(s));
    }
  }

  /**
   * longestPrefixOf
   */
  @Override
  public String longestPrefixOf(String key) {
    return null;
  }
}
