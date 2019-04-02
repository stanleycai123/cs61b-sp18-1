/**
 * Created by JunhaoW on 04/01/2019
 */

interface NullSafeStringComparator {
  /**
   * Return a negative numebr if s1 is 'less than' s2,
   * 0 if 'equal', and a positive number otherwise.
   * Null is considered less than any String.
   * If both inputs are null, return 0.
   */
  public int compare(String s1, String s2);
}

class LengthComparator implements NullSafeStringComparator {
  @Override
  public int compare(String s1, String s2) {
    // if (s1 == null && s2 != null) {
    //   return -1;
    // } else if (s1 != null && s2 == null) {
    //   return 1;
    // }
    //
    // if (s1 == s2 || s1.equals(s2)) {
    //   /** If both are null s1.equals(s2) won't be run */
    //   /** In case
    //    *  String s1 = "123";
    //    *  String s2 = "123";
    //    *  s1 == s2 | true
    //    *  String s1 = new String("123");
    //    *  String s2 = new String("123");
    //    *  s1 == s2 | false
    //    *  Also, null == null is true
    //    */
    //   return 0;
    // } else if (s1.length() < s2.length()) {
    //   return -1;
    // } else {
    //   return 1;
    // }
    if ((s1 == null) && (s2 == null)) return 0;
    if (s1 == null) return -1;
    if (s2 == null) return 1;
    return s1.length() - s2.length();
  }
}


public class HigherOrderFunctionsAndArrays {

  public static String max(String[] a, NullSafeStringComparator sc) {
    String maxStr = a[0];
    for (int i = 1; i < a.length; i++) {
      if (sc.compare(maxStr, a[i]) < 0) {
        /** (null, ""), (null, "hah"), ("", "ahha"), ("", null) */
        maxStr = a[i];
      }
    }
    return maxStr;
  }


  public static String[][] step(String[][] arr) {
    /* Recall: All String references in stepped are null by
    default, so the edges are correct on initialization */
    String[][] stepped = new String[arr.length][arr[0].length];
    for (int i = 1; i < arr.length - 1; i += 1) {
      for (int j = 1; j < arr[0].length - 1; j += 1) {
        String[] temp = new String[9];
        // LengthComparator lc = new LengthComparator();
        int count = 0;
        for (int k = -1; k <= 1; k += 1) {
          for (int m = -1; m <= 1; m += 1) {
            // int index = (k + 1) * 3 + (m + 1);
            // temp[index] = arr[i + k][j + m];
            temp[count] = arr[i + k][j + m];
            count += 1; /** nice! */
          }
        }
        // stepped[i][j] = max(temp, lc);
        stepped[i][j] = max(temp, new LengthComparator());
      }
    }
    return stepped;
  }


  public static void main(String[] args) {
    String[][] origin = {
            {null, null, null, null, null, null},
            {null, "a", "cat", "cat", "dogs", null},
            {null, "a", null, "cat", "a", null},
            {null, "a", "ca", "", "ca", null},
            {null, null, null, null, null, null}
    };
    String[][] newArr = step(origin);

    for (String[] ss : newArr) {
      for (String s : ss) {
        System.out.printf("%-6s", s == null ? "null" : s);
      }
      System.out.println();
    }
  }


}










