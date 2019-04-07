/**
 * Created by JunhaoW on 04/06/2019
 */

public class OffByOne implements CharacterComparator {

  @Override
  public boolean equalChars(char x, char y) {
    return Math.abs(x - y) == 1;
  }
}
