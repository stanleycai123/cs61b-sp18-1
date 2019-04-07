import org.junit.Test;

import static org.junit.Assert.*;

public class TestPalindrome {
  // You must use this palindrome, and not instantiate
  // new Palindromes, or the autograder might be upset.
  static Palindrome palindrome = new Palindrome();

  @Test
  public void testWordToDeque() {
    Deque d = palindrome.wordToDeque("persiflage");
    String actual = "";
    for (int i = 0; i < "persiflage".length(); i++) {
      actual += d.removeFirst();
    }
    assertEquals("persiflage", actual);
  }

  @Test
  public void testPalindrome() {
    assertTrue(palindrome.isPalindrome(""));
    assertTrue(palindrome.isPalindrome("a"));
    assertFalse(palindrome.isPalindrome("abc"));
    assertTrue(palindrome.isPalindrome("aacaa"));
  }

  @Test
  public void testPalindromeCCbyOne() {
    CharacterComparator cc = new OffByOne();
    assertTrue(palindrome.isPalindrome("", cc));
    assertTrue(palindrome.isPalindrome("a", cc));
    assertFalse(palindrome.isPalindrome("abc", cc));
    assertTrue(palindrome.isPalindrome("flake", cc));
    assertFalse(palindrome.isPalindrome("aacaa", cc));
  }

  @Test
  public void testPalindromeCCbyN() {
    CharacterComparator cc = new OffByN(3);
    assertTrue(palindrome.isPalindrome("", cc));
    assertTrue(palindrome.isPalindrome("a", cc));
    assertFalse(palindrome.isPalindrome("abc", cc));
    assertTrue(palindrome.isPalindrome("abaed", cc));
    assertFalse(palindrome.isPalindrome("aacaa", cc));
  }
}



