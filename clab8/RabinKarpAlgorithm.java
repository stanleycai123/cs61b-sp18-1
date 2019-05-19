public class RabinKarpAlgorithm {



  public static int bruteForce(String input, String pattern) {
    if (pattern.length() > input.length()) return -1;
    int n = input.length();
    int m = pattern.length();
    for (int i = 0; i < n - m + 1; i += 1) {
      boolean found = true;
      for (int j = 0; j < m; j += 1) {
        if (input.charAt(i + j) != pattern.charAt(j)) {
          found = false;
          break;
        }
      }
      if (found) return i;
    }
    return -1;
  }

  /**
   * This algorithm returns the starting index of the matching substring.
   * This method will return -1 if no matching substring is found, or if the input is invalid.
   */
  public static int rabinKarp(String input, String pattern) {
    if (pattern.length() > input.length()) return -1;
    int n = input.length();
    int m = pattern.length();

    RollingString patternRoll = new RollingString(pattern, m);
    RollingString inputRoll = new RollingString(input.substring(0, m), m);
    int patternHash = patternRoll.hashCode();
    // 0 1 2 3 4
    // a b c d e
    for (int i = 0; i < n - m + 1; i += 1) {
      int inputHash = inputRoll.hashCode();   // constant
      if (inputHash == patternHash) {
        // match
        if (inputRoll.equals(patternRoll)) {
          return i;
        }
      }
      if (i + m < n)  inputRoll.addChar(input.charAt(i + m));   // constant
    }
    return -1;
  }

}
