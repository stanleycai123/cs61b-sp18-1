import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by JunhaoW on 04/03/2019
 */

public class FlikTest {

  @Test
  public void testIsSameNumber() {
    assertTrue(Flik.isSameNumber(3, 3));
    assertFalse(Flik.isSameNumber(1, 3));
  }
}
