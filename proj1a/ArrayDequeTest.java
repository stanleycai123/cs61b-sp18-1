import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by JunhaoW on 04/04/2019
 */

public class ArrayDequeTest {

  @Test
  public void testResize() {
    ArrayDeque<Integer> D = new ArrayDeque<>();
    if (D.getLength() == 4) {
      /** Case 1 - addFirst only */
      // [2][1][4][3]
      D.addFirst(1);
      D.addFirst(2);
      D.addFirst(3);
      D.addFirst(4);
      assertEquals("Before resizing", Integer.valueOf(4), D.getItem(2));
      // [ ][5][2][1][4][3][6][ ]
      D.addFirst(5);
      assertEquals("After resizing - length", 8, D.getLength());
      assertEquals("After resizing - size", 5, D.size());
      assertEquals("After resizing - 5", Integer.valueOf(5), D.getItem(1));
      D.addLast(6);
      assertEquals("After resizing - 6", Integer.valueOf(6), D.getItem(6));

      /** Case 2 - addLast only */
      D = new ArrayDeque<>();
      // [3][4][1][2]
      D.addLast(1);
      D.addLast(2);
      D.addLast(3);
      D.addLast(4);
      assertEquals("Before resizing", Integer.valueOf(4), D.getItem(1));
      // [ ][6][3][4][1][2][5][ ]
      D.addLast(5);
      assertEquals("After resizing - length", 8, D.getLength());
      assertEquals("After resizing - size", 5, D.size());
      assertEquals("After resizing - 5", Integer.valueOf(5), D.getItem(6));
      D.addFirst(6);
      assertEquals("After resizing - 6", Integer.valueOf(6), D.getItem(1));

      /** Case 3 - Both */
      D = new ArrayDeque<>();
      // [4][1][2][3]
      D.addFirst(1);
      D.addLast(2);
      D.addLast(3);
      D.addFirst(4);
      assertEquals("Before resizing", Integer.valueOf(3), D.getItem(3));
      // [ ][5][4][1][2][3][6][ ]
      D.addFirst(5);
      assertEquals("After resizing - length", 8, D.getLength());
      assertEquals("After resizing - size", 5, D.size());
      assertEquals("After resizing - 5", Integer.valueOf(5), D.getItem(1));
      D.addLast(6);
      assertEquals("After resizing - 6", Integer.valueOf(6), D.getItem(6));
    }
  }

  @Test
  public void testAddFirstLast() {
    ArrayDeque<Integer> D1 = new ArrayDeque<>();
    if (D1.getLength() == 4) {
      // [9][1][7][8]
      D1.addFirst(1);
      assertEquals(Integer.valueOf(1), D1.getItem(1));
      D1.addFirst(9);
      assertEquals(Integer.valueOf(9), D1.getItem(0));
      D1.addFirst(8);
      assertEquals(Integer.valueOf(8), D1.getItem(3));
      D1.addFirst(7);
      assertEquals(Integer.valueOf(7), D1.getItem(2));

      ArrayDeque<Integer> D2 = new ArrayDeque<>();
      // [5][6][7][1]
      D2.addLast(7);
      assertEquals(Integer.valueOf(7), D2.getItem(2));
      D2.addLast(1);
      assertEquals(Integer.valueOf(1), D2.getItem(3));
      D2.addLast(5);
      assertEquals(Integer.valueOf(5), D2.getItem(0));
      D2.addLast(6);
      assertEquals(Integer.valueOf(6), D2.getItem(1));
    }
  }

  @Test
  public void testPrintDeque() {
    ArrayDeque<Integer> D = new ArrayDeque<>();
    if (D.getLength() == 4) {
      // [9][1][7][8]
      D.addFirst(1);
      D.addFirst(9);
      D.addFirst(8);
      D.addFirst(7);
      // change the return type of printDeque before testing
      // assertEquals("7 8 9 1", D.printDeque());
    }
  }
}
