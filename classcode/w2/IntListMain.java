/**
 * IntListMain
 */
public class IntListMain {
  public static void main(String[] args) {
    IntList L = new IntList(5, null);
    L.rest = new IntList(10, null);
    L.rest.rest = new IntList(15, null);
    // System.out.println(L.iterativeSize());
    System.out.println(L.get_recursive(3));
  }
}

/**
 * IntList
 */
class IntList {

  public int first;
  public IntList rest;

  public IntList(int f, IntList r) {
    first = f;
    rest = r;
  }

  /* recursion */
  public int size() {
    /* assume rest is null at first */
    if (rest == null) { /* or rest == null */
      return 1; /* base case */
    }
    return 1 + rest.size();
  }

  /* you can't assign "this" in Java */
  public int iterativeSize() {
    int totalSize = 0;
    IntList p = this; /* better to name it as "p" */
    while (p != null) {  /* just "p" ? */
      totalSize += 1;
      p = p.rest;
    }
    return totalSize;
  }

  /* get - recursive version */
  public int get_recursive(int index) {
    if (index == 0) {
      return first;
    }
    return rest.get_recursive(index - 1);
  }

  /* get - iterative version */
  public int get(int index) {
    int count = 0;
    IntList p = this;
    while (p != null) {
      if (count == index) {
        return p.first;
      }
      count += 1;
      p = p.rest;
    }
    return -1;
  }
}