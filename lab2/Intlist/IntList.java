import java.util.Formatter;

/**
 * A naked recursive list of integers, similar to what we saw in lecture 3, but
 * with a large number of additional methods.
 *
 * @author P. N. Hilfinger, with some modifications by Josh Hug and melaniecebula
 * [Do not modify this file.]
 */
public class IntList {
  /**
   * First element of list.
   */
  public int first;
  /**
   * Remaining elements of list.
   */
  public IntList rest;

  /**
   * A List with first FIRST0 and rest REST0.
   */
  public IntList(int first0, IntList rest0) {
    first = first0;
    rest = rest0;
  }

  /**
   * A List with null rest, and first = 0.
   */
  public IntList() {
    /* NOTE: public IntList () { }  would also work. */
    this(0, null);
  }

  /**
   * Returns a list equal to L with all elements squared. Destructive.
   */
  public static void dSquareList(IntList L) {
    while (L != null) {
      L.first = L.first * L.first;
      L = L.rest;
    }
  }

  /**
   * Returns a list equal to L with all elements squared. Non-destructive.
   */
  public static IntList squareListIterative(IntList L) {
    if (L == null) {
      return null;
    }
    IntList res = new IntList(L.first * L.first, null);
    IntList ptr = res;
    L = L.rest;
    while (L != null) {
      ptr.rest = new IntList(L.first * L.first, null);
      L = L.rest;
      ptr = ptr.rest;
    }
    return res;
  }

  /**
   * Returns a list equal to L with all elements squared. Non-destructive.
   */
  public static IntList squareListRecursive(IntList L) {
    if (L == null) {
      return null;
    }
    return new IntList(L.first * L.first, squareListRecursive(L.rest));
  }

  /** DO NOT MODIFY ANYTHING ABOVE THIS LINE! */


  /**
   * Returns a list consisting of the elements of A followed by the
   * *  elements of B.  May modify items of A. Don't use 'new'.
   */
  public static IntList dcatenate(IntList A, IntList B) {
    IntList p = A;
    if (A == null) {
      return B;
    }
    while (p.rest != null) {
      p = p.rest;
    }
    /* now, p points to the last IntList */
    p.rest = B;
    return A;
  }

  /**
   * dcatenate - recursive version
   */
  public static IntList dcatenate_re(IntList A, IntList B) {
    if (A == null) {
      return B;
    }
    if (A.rest == null) {
      A.rest = B;
      return null;
    }
    /* A */
    dcatenate_re(A.rest, B);
    return A;
  }

  /**
   * Returns a list consisting of the elements of A followed by the
   * * elements of B.  May NOT modify items of A.  Use 'new'.
   */
  public static IntList catenate(IntList A, IntList B) {
    IntList M = null;
    IntList p = null;
    if (A != null) {
      M = new IntList(A.first, null);
      p = M;
      A = A.rest;
      while (A != null) {
        p.rest = new IntList(A.first, null);
        p = p.rest;
        A = A.rest;
      } /* now p points to the last element of A */
    } else if (B != null) { /* A == null */
      // if (A == null && B != null) {  /* this line is bug. A is changed */
      M = new IntList(B.first, null);
      p = M;
      B = B.rest;
    }
    while (B != null) { /* can't put this block inside the above block */
      p.rest = new IntList(B.first, null);
      p = p.rest;
      B = B.rest;
    }
    return M;
  }

  /**
   * catenate - recursive
   */
  public static IntList catenate_re(IntList A, IntList B) {
    if (A == null) {
      /* Enter B mode! later A should be null always */
      if (B == null) {
        return null;
      }
      return new IntList(B.first, catenate_re(null, B.rest));
    } else { /* A != null */
      /* Enter A mode! later B stays the same */
      return new IntList(A.first, catenate_re(A.rest, B));
    }
  }

  /**
   * Osmosis - Practice
   * addAjacent()
   */
  /**
   * L.addAdjacent()
   */
  public void addAdjacent() {
    IntList p = this;
    /* if p == null, p.next will no longer execute */
    if (p.rest == null) {
      /* size <= 1 */
      return;
    }
    while (p.rest != null) { /* p ends at the last 2nd node */
      if (p.first == p.rest.first) {
        /* merge */
        p.first *= 2;
        p.rest = p.rest.rest; /* it's okay if it is null */
        /* Notice: no ++ here */
      } else {
        p = p.rest;
      }
    }
  }

  /**
   * main
   */
  public static void main(String[] args) {
    IntList L1 = IntList.of(1, 2, 3, 4, 5);
    IntList L2 = IntList.of(6, 7);
    // System.out.println(IntList.catenate(null, L2));
    /* dcatenate - recursive */
    // System.out.println(dcatenate_re(null, L2));
    // System.out.println(L1);
    // TestCatenate();
    TestAddAdjacent();
  }

  public static void TestCatenate() {
    /* catenate - recursive */
    IntList L1 = IntList.of(1, 2, 3, 4, 5);
    IntList L2 = IntList.of(6, 7);
    // L1 = null;
    L2 = null;
    System.out.println("catenate_re(L1, L2) = " + catenate_re(L1, L2));
    System.out.println("L1 = " + L1); /* check if it stay the same */
    System.out.println("L2 = " + L2);
  }

  public static void TestAddAdjacent() {
    // IntList L = IntList.of(1, 1, 2, 3);
    IntList L = IntList.of(1, 1, 1, 2, 3, 3, 6);
    L.addAdjacent();
    System.out.println(L);
  }


  /**
   * DO NOT MODIFY ANYTHING BELOW THIS LINE! Many of the concepts below here
   * will be introduced later in the course or feature some form of advanced
   * trickery which we implemented to help make your life a little easier for
   * the lab.
   */

  @Override
  public int hashCode() {
    return first;
  }

  /**
   * Returns a new IntList containing the ints in ARGS. You are not
   * expected to read or understand this method.
   */
  public static IntList of(Integer... args) {
    IntList result, p;

    if (args.length > 0) {
      result = new IntList(args[0], null);
    } else {
      return null;
    }

    int k;
    for (k = 1, p = result; k < args.length; k += 1, p = p.rest) {
      p.rest = new IntList(args[k], null);
    }
    return result;
  }

  /**
   * Returns true iff X is an IntList containing the same sequence of ints
   * as THIS. Cannot handle IntLists with cycles. You are not expected to
   * read or understand this method.
   */
  public boolean equals(Object x) {
    if (!(x instanceof IntList)) {
      return false;
    }
    IntList L = (IntList) x;
    IntList p;

    for (p = this; p != null && L != null; p = p.rest, L = L.rest) {
      if (p.first != L.first) {
        return false;
      }
    }
    if (p != null || L != null) {
      return false;
    }
    return true;
  }

  /**
   * If a cycle exists in the IntList, this method
   * returns an integer equal to the item number of the location where the
   * cycle is detected.
   * <p>
   * If there is no cycle, the number 0 is returned instead. This is a
   * utility method for lab2. You are not expected to read, understand, or
   * even use this method. The point of this method is so that if you convert
   * an IntList into a String and that IntList has a loop, your computer
   * doesn't get stuck in an infinite loop.
   */

  private int detectCycles(IntList A) {
    IntList tortoise = A;
    IntList hare = A;

    if (A == null) {
      return 0;
    }

    int cnt = 0;


    while (true) {
      cnt++;
      if (hare.rest != null) {
        hare = hare.rest.rest;
      } else {
        return 0;
      }

      tortoise = tortoise.rest;

      if (tortoise == null || hare == null) {
        return 0;
      }

      if (hare == tortoise) {
        return cnt;
      }
    }
  }

  @Override
  /** Outputs the IntList as a String. You are not expected to read
   * or understand this method. */
  public String toString() {
    Formatter out = new Formatter();
    String sep;
    sep = "(";
    int cycleLocation = detectCycles(this);
    int cnt = 0;

    for (IntList p = this; p != null; p = p.rest) {
      out.format("%s%d", sep, p.first);
      sep = ", ";

      cnt++;
      if ((cnt > cycleLocation) && (cycleLocation > 0)) {
        out.format("... (cycle exists) ...");
        break;
      }
    }
    out.format(")");
    return out.toString();
  }
}

