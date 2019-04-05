import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Performs some basic linked list tests.
 */
public class LinkedListDequeTest {

  /* Utility method for printing out empty checks. */
  public static boolean checkEmpty(boolean expected, boolean actual) {
    if (expected != actual) {
      System.out.println("isEmpty() returned " + actual + ", but expected: " + expected);
      return false;
    }
    return true;
  }

  /* Utility method for printing out empty checks. */
  public static boolean checkSize(int expected, int actual) {
    if (expected != actual) {
      System.out.println("size() returned " + actual + ", but expected: " + expected);
      return false;
    }
    return true;
  }

  /* Prints a nice message based on whether a test passed.
   * The \n means newline. */
  public static void printTestStatus(boolean passed) {
    if (passed) {
      System.out.println("Test passed!\n");
    } else {
      System.out.println("Test failed!\n");
    }
  }

  /**
   * Adds a few things to the list, checking isEmpty() and size() are correct,
   * finally printing the results.
   * <p>
   * && is the "and" operation.
   */
  public static void addIsEmptySizeTest() {
    System.out.println("Running add/isEmpty/Size test.");
		// /*
		LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

		boolean passed = checkEmpty(true, lld1.isEmpty());

		lld1.addFirst("front");
		
		// The && operator is the same as "and" in Python.
		// It's a binary operator that returns true if both arguments true, and false otherwise.
		passed = checkSize(1, lld1.size()) && passed;
		passed = checkEmpty(false, lld1.isEmpty()) && passed;

		lld1.addLast("middle");
		passed = checkSize(2, lld1.size()) && passed;

		lld1.addLast("back");
		passed = checkSize(3, lld1.size()) && passed;

		System.out.println("Printing out deque: ");
		lld1.printDeque();

		printTestStatus(passed);
		// */
  }

  /**
   * Adds an item, then removes an item, and ensures that dll is empty afterwards.
   */
  public static void addRemoveTest() {

    System.out.println("Running add/remove test.");

    // System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
		// /*
		LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
		// should be empty 
		boolean passed = checkEmpty(true, lld1.isEmpty());

		lld1.addFirst(10);
		// should not be empty 
		passed = checkEmpty(false, lld1.isEmpty()) && passed;

		lld1.removeFirst();
		// should be empty 
		passed = checkEmpty(true, lld1.isEmpty()) && passed;

    lld1.addLast(10);
    // should not be empty
    passed = checkEmpty(false, lld1.isEmpty()) && passed;

    lld1.removeLast();
    // should be emptys
    passed = checkEmpty(true, lld1.isEmpty()) && passed;

		printTestStatus(passed);
		// */
  }


  @Test
  public void testGet() {
    LinkedListDeque<Integer> L = new LinkedListDeque<>();
    assertEquals(null, L.get(0));
    L.addFirst(3);
    assertEquals(Integer.valueOf(3), L.get(0));
    L.addFirst(2);
    L.addFirst(1);
    assertEquals(Integer.valueOf(3), L.get(2));
    assertEquals(null, L.get(3));
    assertEquals(null, L.get(4));
  }

  @Test
  public void testGetRecursive() {
    LinkedListDeque<Integer> L = new LinkedListDeque<>();
    assertEquals(null, L.getRecursive(0));
    L.addFirst(3);
    assertEquals(Integer.valueOf(3), L.getRecursive(0));
    L.addFirst(2);
    L.addFirst(1);
    assertEquals(Integer.valueOf(3), L.getRecursive(2));
    assertEquals("hello", null, L.getRecursive(3));
    assertEquals("hi", null, L.getRecursive(4));
  }

  @Test
  public void testGet2() {
    LinkedListDeque<Integer> L = new LinkedListDeque<>();
    L.addFirst(2);
    assertEquals(Integer.valueOf(2), L.removeLast());
    L.addFirst(4);
    assertEquals(Integer.valueOf(4), L.removeLast());
    L.addFirst(6);
    assertEquals(Integer.valueOf(6), L.removeLast());
    L.addLast(8);
    L.addFirst(9);
    assertEquals(Integer.valueOf(8), L.removeLast());
    L.addLast(11);
    assertEquals(Integer.valueOf(9), L.get(0));

  }


  // public static void main(String[] args) {
  //   System.out.println("Running tests.\n");
  //   addIsEmptySizeTest();
  //   addRemoveTest();
  // }
}