/**
 * Created by hug.
 */
public class ExperimentHelper {

  /**
   * Returns the internal path length for an optimum binary search tree of
   * size N. Examples:
   * N = 1, OIPL: 0
   * N = 2, OIPL: 1
   * N = 3, OIPL: 2
   * N = 4, OIPL: 4
   * N = 5, OIPL: 6
   * N = 6, OIPL: 8
   * N = 7, OIPL: 10
   * N = 8, OIPL: 13
   */
  public static int optimalIPL(int N) {
    if (N == 1 || N == 0) {
      return 0;
    }
    int plus = (int) log(2, N);
    return plus + optimalIPL(N - 1);
  }

  private static double log(int base, int x) {
    return Math.log(x) / Math.log(base);
  }

  // main
  public static void main(String[] args) {
    for (int i = 1; i <= 8; i += 1) {
      System.out.println("N = " + i + ", OIPL: " + optimalIPL(i) + ", OAD: " + optimalAverageDepth(i));
    }
  }


  /**
   * Returns the average depth for nodes in an optimal BST of
   * size N.
   * Examples:
   * N = 1, OAD: 0
   * N = 5, OAD: 1.2
   * N = 8, OAD: 1.625
   *
   * @return
   */
  public static double optimalAverageDepth(int N) {
    return (double) optimalIPL(N) / N;
  }
}
