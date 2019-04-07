/**
 * Created by JunhaoW on 04/06/2019
 */

public class OffByN implements CharacterComparator {

  private int n;

  public OffByN(int N) {
    n = N;
  }

  @Override
  public boolean equalChars(char x, char y) {
    return Math.abs(x - y) == n;
  }
}
