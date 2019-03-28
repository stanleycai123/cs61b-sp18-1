public class SLMain {
  public static void main(String[] args) {
    StringList L = new StringList("eat", null);
    L = new StringList("shouldn't", L);
    L = new StringList("you", L);
    L = new StringList("sometimes", L);
    StringList M = L.rest; /* you */
    StringList R = new StringList("many", null);
    R = new StringList("potatoes", R);
    R.rest.rest = R; /* null = R("potatoes") */
    /* M.rest == L.rest.rest ("shouldn't") */
    /* R.rest == R("many") */
    // M.rest.rest.rest = R.rest;
    // L.rest.rest = L.rest.rest.rest;
    // L = M.rest;

    /* print */
    printList(L);
  }

  public static void printList(StringList list) {
    while (list != null) {
      System.out.print(list.first + " ");
      list = list.rest;
    }
  }
}

class StringList {
  public String first;
  public StringList rest;

  public StringList(String first, StringList rest) {
    this.first = first;
    this.rest = rest;
  }
  @Override
  public String toString() {
    return first;
  }
}