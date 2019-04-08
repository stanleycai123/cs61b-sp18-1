import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by JunhaoW on 04/08/2019
 */

public class TestArrayDequeGold {

  @Test
  public void testSad() {
    StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
    ArrayDequeSolution<Integer> ans = new ArrayDequeSolution<>();

    String output = "";

    int count = 0;
    while (true) {
      double randNum = StdRandom.uniform();

      if (randNum < 0.25) {
        sad.addFirst(count);
        ans.addFirst(count);
        Integer expected = ans.get(0);
        Integer actual = sad.get(0);
        output += "\naddFirst(" + count + ")";
        assertEquals(output, expected, actual);

      } else if (randNum < 0.5) {
        sad.addLast(count);
        ans.addLast(count);
        Integer expected = ans.get(sad.size() - 1);
        Integer actual = sad.get(sad.size() - 1);
        output += "\naddLast(" + count + ")";
        assertEquals(output, expected, actual);

      } else if (randNum < 0.75) {
        Integer x1 = sad.removeFirst();
        Integer x2 = ans.removeFirst();
        if (x1 == null || x2 == null) {
          continue;
        }
        Integer expected = x1;
        Integer actual = x2;
        output += "\nremoveFirst()";
        assertEquals(output, expected, actual);

      } else {
        Integer x1 = sad.removeLast();
        Integer x2 = ans.removeLast();
        if (x1 == null || x2 == null) {
          continue;
        }
        Integer expected = x1;
        Integer actual = x2;
        output += "\nremoveLast()";
        assertEquals(output, expected, actual);
      }
      count += 1;
    }

  }
}
