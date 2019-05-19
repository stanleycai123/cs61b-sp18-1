import java.util.LinkedList;

/**
 * A String-like class that allows users to add and remove characters in the String
 * in constant time and have a constant-time hash function. Used for the Rabin-Karp
 * string-matching algorithm.
 */
class RollingString {

  private int cachedHash;
  private LinkedList<Character> charData;

  /**
   * Number of total possible int values a character can take on.
   * DO NOT CHANGE THIS.
   */
  static final int UNIQUECHARS = 128;

  /**
   * The prime base that we are using as our mod space. Happens to be 61B. :)
   * DO NOT CHANGE THIS.
   */
  static final int PRIMEBASE = 6113;

  /**
   * Initializes a RollingString with a current value of String s.
   * s must be the same length as the maximum length.
   */
  public RollingString(String s, int length) {
    assert (s.length() == length);

    charData = new LinkedList<>();
    for (int i = 0; i < s.length(); i += 1) {
      char ch = s.charAt(i);
      charData.add(ch);
    }
    // Init a hash code
    cachedHash = hash(charData);
  }

  /**
   * Calculate the hash code
   */
  public static int hash(LinkedList<Character> L) {
    int hash = 0;
    for (char c : L) {
      int ic = (int) c;
      hash = ((UNIQUECHARS * hash + ic) & 0x7FFFFFFF) % PRIMEBASE;
    }
    return hash;
  }

  /**
   * Update the hash code
   * @param c the new char
   */
  private void updateCachedHash(char c) {
    int oldChar = (int) charData.getFirst();
    int minus =  (((int) Math.pow(UNIQUECHARS, length() - 1) * oldChar) & 0x7FFFFFFF) % PRIMEBASE;
    cachedHash -= minus;
    cachedHash = ((cachedHash * UNIQUECHARS + (int) c) & 0x7FFFFFFF) % PRIMEBASE;
  }

  /**
   * Adds a character to the back of the stored "string" and
   * removes the first character of the "string".
   * Should be a constant-time operation.
   */
  public void addChar(char c) {
    updateCachedHash(c);
    charData.removeFirst();
    charData.addLast(c);
  }


  /**
   * Returns the "string" stored in this RollingString, i.e. materializes
   * the String. Should take linear time in the number of characters in
   * the string.
   */
  public String toString() {
    StringBuilder strb = new StringBuilder();
    for (char c : charData) {
      strb.append(c);
    }
    return strb.toString();
  }

  /**
   * Returns the fixed length of the stored "string".
   * Should be a constant-time operation.
   */
  public int length() {
    return charData.size();
  }


  /**
   * Checks if two RollingStrings are equal.
   * Two RollingStrings are equal if they have the same characters in the same
   * order, i.e. their materialized strings are the same.
   */
  @Override
  public boolean equals(Object o) {
    RollingString that = (RollingString) o;
    if (this.length() != that.length()) return false;
    String str1 = this.toString();
    String str2 = that.toString();
    return str1.equals(str2);
  }

  /**
   * Returns the hashcode of the stored "string".
   * Should take constant time.
   */
  @Override
  public int hashCode() {
    return cachedHash;
  }


}
