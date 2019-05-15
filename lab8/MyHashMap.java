import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by JunhaoW on 05/14/2019
 */

public class MyHashMap<K, V> implements Map61B<K, V> {

  private static final int INIT_SIZE = 16;
  private static final double LOAD_FACTOR = 0.75;
  private double loadFactor = 0.75;
  private int N;  // number of Entries
  private int M;  // number of buckets
  private MyLinkedList<K, V>[] buckets;
  private HashSet<K> allKeys;


  /**
   * MyLinkedList
   */
  private class MyLinkedList<K, V> {
    private int size;
    private Entry sentinel;

    public MyLinkedList() {
      sentinel = new Entry(null, null);
      sentinel.prev = sentinel.next = sentinel;
      size = 0;
    }

    public int size() {
      return size;
    }

    public void removeAll() {
      sentinel.prev = sentinel.next = null;
      size = 0;
    }

    public Entry getFirst() {
      if (size == 0) return null;
      return sentinel.next;
    }

    public Entry getLast() {
      if (size == 0) return null;
      return sentinel.prev;
    }

    public void addFirst(K key, V val) {
      Entry newEntry = new Entry(key, val);
      newEntry.prev = sentinel;
      newEntry.next = sentinel.next;
      sentinel.next = newEntry;
      newEntry.next.prev = newEntry;
      size += 1;
    }

    public void addLast(K key, V val) {
      Entry newEntry = new Entry(key, val);
      newEntry.next = sentinel;
      newEntry.prev = sentinel.prev;
      sentinel.prev = newEntry;
      newEntry.prev.next = newEntry;
      size += 1;
    }

    public Entry removeFirst() {
      if (size == 0) return null;
      Entry removedEntry = sentinel.next;
      sentinel.next = removedEntry.next;
      removedEntry.next.prev = sentinel;
      size -= 1;
      return removedEntry;
    }

    public Entry removeLast() {
      if (size == 0) return null;
      Entry removedEntry = sentinel.prev;
      sentinel.prev = removedEntry.prev;
      removedEntry.prev.next = sentinel;
      size -= 1;
      return removedEntry;
    }

    public boolean contains(K key) {
      if (key == null) throw new IllegalArgumentException();
      return get(key) != null;
    }

    public V get(K key) {
      if (size == 0) return null;
      Entry p = sentinel.next;
      for (int count = 0; count < size; p = p.next, count += 1) {
        if (key.equals(p.key))
          return p.val;
      }
      return null;
    }

    /**
     * put
     * @return {@code true} only if the inserted key is a new one.
     */
    public boolean put(K key, V val) {
      if (contains(key) == false) {
        addLast(key, val);
        return true;
      }
      else {
        // replace
        Entry p = sentinel.next;
        for (int count = 0; count < size; p = p.next, count += 1) {
          if (key.equals(p.key)) {
            p.val = val;
            return false;
          }
        }
      }
      return false;
    }



    /**
     * Entry
     */
    private class Entry {
      public K key;
      public V val;
      public Entry next;
      public Entry prev;

      public Entry(K key, V val) {
        this.key = key;
        this.val = val;
      }
    }
  }

  /**
   * Constructor
   */
  public MyHashMap() {
    this(INIT_SIZE, LOAD_FACTOR);
  }

  public MyHashMap(int initialSize) {
    this(initialSize, LOAD_FACTOR);
  }

  public MyHashMap(int initialSize, double loadFactor) {
    this.N = 0;
    this.M = initialSize;
    this.loadFactor = loadFactor;
    this.buckets = initBuckets(M);
    this.allKeys = new HashSet<>();
  }

  private MyLinkedList<K, V>[] initBuckets(int S) {

    MyLinkedList<K, V>[] ret = new MyLinkedList[S];
    for (int i = 0; i < S; i += 1) {
      ret[i] = new MyLinkedList<>();
    }
    return ret;
  }

  /**
   * hash
   */
  private int hash(K key) {
    return hash(key, this.M);
  }

  private int hash(K key, int mod) {
    return (key.hashCode() & 0x7FFFFFFF) % mod;
  }

  /**
   * resize
   */
  private void resize(int new_M) {
    MyLinkedList<K, V>[] newBuckets = initBuckets(new_M);


    for (K key : allKeys) { // or: this
      V val = get(key);  /* using old M */
      int newNum = hash(key, new_M);
      MyLinkedList<K, V> L = newBuckets[newNum];
      L.put(key, val);
    }
    this.M = new_M;
    this.buckets = newBuckets;
  }

  /**
   * size
   */
  @Override
  public int size() {
    return N;
  }

  /**
   * keySet
   */
  @Override
  public Set<K> keySet() {
    return allKeys;
  }

  /**
   * clear
   */
  @Override
  public void clear() {
    this.N = 0;
    for (int i = 0; i < this.buckets.length; i += 1) {
      this.buckets[i].removeAll();
    }
    this.allKeys.clear();
  }

  /**
   * containsKey
   */
  @Override
  public boolean containsKey(K key) {
    if (key == null) throw new IllegalArgumentException();
    return get(key) != null;
  }


  /**
   * get
   */
  @Override
  public V get(K key) {
    if (key == null) throw new IllegalArgumentException();
    int num = hash(key);
    MyLinkedList<K, V> L = buckets[num];
    return L.get(key);
  }

  /**
   * put
   */
  @Override
  public void put(K key, V val) {
    if (key == null) throw new IllegalArgumentException();

    if (N / M >= loadFactor) {
      // resize
      resize(M * 2);
    }

    int num = hash(key);
    MyLinkedList L = buckets[num];
    if (L.put(key, val)) {
      N += 1;
      allKeys.add(key);
    }
  }


  /**
   * Remove
   */
  @Override
  public V remove(K key) {
    throw new UnsupportedOperationException();
  }

  @Override
  public V remove(K key, V value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Iterator<K> iterator() {
    return keySet().iterator();
  }


  public static void main(String[] args) {
      MyHashMap<String, String> a = new MyHashMap<String, String>();
      MyHashMap<String, Integer> b = new MyHashMap<String, Integer>();
      MyHashMap<Integer, String> c = new MyHashMap<Integer, String>();
      MyHashMap<Boolean, Integer> e = new MyHashMap<Boolean, Integer>();
  }
}
