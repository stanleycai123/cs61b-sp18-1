import org.junit.Test;

/**
 * Created by JunhaoW on 04/06/2019
 */

public class TestOffByN {

  @Test
  public void testEqualChars() {
    OffByN offBy5 = new OffByN(5);
    offBy5.equalChars('a', 'f'); // true
    offBy5.equalChars('f', 'a'); // true
    offBy5.equalChars('f', 'h'); // false
  }
}
