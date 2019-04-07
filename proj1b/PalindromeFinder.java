/**
 * This class outputs all palindromes in the words file in the current directory.
 */
public class PalindromeFinder {

  public static void main(String[] args) {
    int minLength = 4;
    // In in = new In("../library-sp18/data/words.txt");
    In in;
    Palindrome palindrome = new Palindrome();

    int max = 0;
    int n = 0;
    for (int i = 0; i < 26; i++) {
      in = new In("../library-sp18/data/words.txt");
      int count = 0;
      while (!in.isEmpty()) {
        String word = in.readString();
        if (word.length() >= minLength && palindrome.isPalindrome(word, new OffByN(i))) {
          System.out.println(word);
          count++;
        }
      }
      if (count > max) {
        max = count;
        n = i;
      }
    }
    System.out.println(n + ": " + max);
  }
}
