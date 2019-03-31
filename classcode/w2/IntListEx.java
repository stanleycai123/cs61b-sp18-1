/**
 * IntListEx
 */
public class IntListEx {
  public static void main(String[] args) {
    IntList L = new IntList(5, null);
    L.rest = new IntList(10, null);
    L.rest.rest = new IntList(15, null);
    // System.out.println(L.iterativeSize());
    // System.out.println(L.get_recursive(3));
    IntList l = IntList.incrList_recursive(L, 2);
    System.out.println(l.first);
    System.out.println(l.rest.first);
    System.out.println(l.rest.rest.first);
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
    while (p != null) { /* just "p" ? */
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

  /** 
   * Extra Exercise (iterative & recursive)
  */
  public static IntList incrList(IntList L, int x) {
    /* or you can just use L */
    if (L == null) {
      return null;
    }
    /* Head to the new list */
    IntList M = new IntList(p1.first + x, null);
    IntList p = M; /* p to M */
    /* ahead */
    L = L.rest;
    while (L != null) {
      /* L is one ahead p */
      p.rest = new IntList(L.first + x, null); /* null, becasue we don't know the next one yet */
      p = p.rest; /* p points to what is just created */
      L = L.rest; /* update L too */
    }
    return M;
  }

  /* Recursive Version */
  public static IntList incrList_recursive(IntList L, int x) {
    if (L == null) { /* base case */
      return null;
    }
    return new IntList(L.first + x, incrList(L.rest, x));
  }

  /**
   * Returns an IntList identical to L, but with each element incremented by x.
   * Not allowed to use the 'new' keyword.
   */
  public static IntList dincrList(IntList L, int x) {
    IntList p = L;
    while (p != null) {
      p.first += x;
      p = p.rest;
    }
    return L;
  }
}