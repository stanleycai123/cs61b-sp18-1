/**
 * @date 05/19/2019
 * @author Junhao Wang
 */
public class KMP {

  //           1         2
  // m: 01234567890123456789012
  // S: ABC ABCDAB ABCDABCDABDE
  // W:     ABCDABD
  // i:     0123456

  // ABC ABCD AB EF ABCD GGG (input)
  //     ABCD AB EF ABCD EEE (pattern)
  //     0123 45 67 8901 234
  //          AB vs ABCD

  public static int kmp(String input, String pattern) {
    int n = input.length();    // 0 1 2 3 4 (n = 5, m = 3)
    int m = pattern.length();  // A B C
    if (m > n) return -1;
    int inputOffset = 0;       // potential input jump
    int patternOffset = 0;     // potential starting pattern offset
    for (int i = 0; i < n - m + 1; ) {
      inputOffset = 0;
      for (int j = patternOffset; j < m; j += 1) {
        if (j == patternOffset) patternOffset = 0;
        int currentPos = i + j;  // current position of input string
        // equal - match or move on and record potential
        if (input.charAt(currentPos) == pattern.charAt(j)) {
          if (j == m - 1) return i;  // match and return
          if (j > 0) {  // if j == 0 -> first time -> don't record
            if (input.charAt(currentPos) == pattern.charAt(patternOffset)) {
              inputOffset = (inputOffset == 0) ? currentPos : inputOffset;
              patternOffset += 1;
            } else {
              inputOffset = 0;    // reset
              patternOffset = 0;  // reset
            }
          }
        }
        // not equal - should stop
        else {
          if (inputOffset == 0 && patternOffset == 0) { // jump to the farthest
            i = (j > 0) ? currentPos : currentPos + 1;    // if stop initially
          } else { // jump to the potential matching position
            i = inputOffset;
          }
          break;  // back to outer loop
        }
      }
    }
    return -1;
  }

  public static void main(String[] args) {
    // test
    String input = "ABC ABCDAB ABCDABCDABDE";
    String pattern = "ABCDABD";
    int result = kmp(input, pattern);
    System.out.println(result);
  }
}