/**
 * Created by JunhaoW on 04/06/2019
 */

public class Palindrome {


  public Deque<Character> wordToDeque(String word) {

    LinkedListDeque<Character> charDeque = new LinkedListDeque<>();
    for (int i = 0; i < word.length(); i++) {
      charDeque.addLast(word.charAt(i));
    }

    return charDeque;
  }

  /**
   * OffByZero
   */
  public boolean isPalindrome(String word) {
    Deque<Character> deque = wordToDeque(word);
    return isPalindrome_re(deque); /* Don't use get */
  }
  private boolean isPalindrome_re(Deque<Character> deque) {
    if (deque.size() <= 1) {
      return true;
    }
    if (!deque.removeFirst().equals(deque.removeLast())) {
      return false;
    }
    return isPalindrome_re(deque);
  }

  /**
   * OffByOne/N
   */
  public boolean isPalindrome(String word, CharacterComparator cc) {
    Deque<Character> deque = wordToDeque(word);
    return isPalindrome_re(deque, cc);
  }

  private boolean isPalindrome_re(Deque<Character> deque, CharacterComparator cc) {
    if (deque.size() <= 1) {
      return true;
    }
    if (!cc.equalChars(deque.removeFirst(), deque.removeLast())) {
      return false;
    }
    return isPalindrome_re(deque, cc);
  }
}
