package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

  private final static int BLOCKED = 0;
  private final static int OPEN = 1;

  private final static int[][] DIRECTION = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

  private int N;
  private int siteCount;
  private int[][] grid;
  private WeightedQuickUnionUF uf_top;
  private WeightedQuickUnionUF uf_top_bottom;

  public Percolation(int N) {
    if (N <= 0) throw new IllegalArgumentException();;
    this.N = N;
    this.siteCount = 0;
    this.grid = new int[N][N];
    this.uf_top = new WeightedQuickUnionUF(N * N + 1);
    this.uf_top_bottom = new WeightedQuickUnionUF(N * N + 2);
    /* 2 extra points - start & end for speeding */
    setupStartEnd();
  }

  private int getTop() {
    return N * N;
  }
  private int getBottom() {
    return N * N + 1;
  }
  private void setupStartEnd() {
    int start = getTop();
    int end = getBottom();
    for (int j = 0; j < N; j += 1) {
      uf_top.union(ToIndex(0, j), start);
      uf_top_bottom.union(ToIndex(0, j), start);
      uf_top_bottom.union(ToIndex(N - 1, j), end);
    }
  }

  /**
   * BLOCKED: 0
   * OPEN: 1
   * FULL: 2
   */
  public void open(int row, int col) {
    checkIndex(row, col);
    if (isOpen(row, col) == true) return;
    grid[row][col] = OPEN; /* just open */
    siteCount += 1;

    // up / down / left / right
    for (int[] dir : DIRECTION) {
      int r = row + dir[0]; int c = col + dir[1];
      if (validIndex(r, c) && isOpen(r, c)) {
        int ind1 = ToIndex(row, col);
        int ind2 = ToIndex(r, c);
        uf_top.union(ind1, ind2);
        uf_top_bottom.union(ind1, ind2);
      }
    }
  }

  public boolean isOpen(int row, int col) {
    checkIndex(row, col);
    return grid[row][col] == OPEN;
  }

  public boolean isFull(int row, int col) {
    checkIndex(row, col);
    if (N == 1) {
      return isOpen(0, 0);
    } else {
      return isOpen(row, col) && uf_top.connected(getTop(), ToIndex(row, col));
    }

    // if (row == 0) {
    //   return isOpen(row, col); /* open -> FULL; blocked -> Not FULL */
    // }
    // if (row == N - 1) {
    //   return uf.connected(getTop(), ToIndex(row, col));
    // }
    //
    // return uf.connected(getBottom(), ToIndex(row, col));
  }

  public int numberOfOpenSites() {
    return siteCount;
  }

  public boolean percolates() {
    if (N == 1) {
      return isOpen(0, 0);
    } else {
      return uf_top_bottom.connected(getTop(), getBottom());
    }
  }

  private int ToIndex(int row, int col) {
    return N * row + col;
  }

  private boolean validIndex(int row, int col) {
    if (row < 0 || row > N - 1) return false;
    if (col < 0 || col > N - 1) return false;
    return true;
  }

  private void checkIndex(int row, int col) {
    if (!validIndex(row, col)) throw new IndexOutOfBoundsException();
    if (!validIndex(row, col)) throw new IndexOutOfBoundsException();
  }

  private void print() {
    for (int i = 0; i < N; i += 1) {
      for (int j = 0; j < N; j += 1) {
        if (isFull(i, j)) {
          System.out.print(2 + "  ");
        } else {
          System.out.print(grid[i][j] + "  ");
        }

      }
      System.out.println();
    }
  }

  public static void main(String[] args) {
    // test1();
    // test2();
    test3();
  }

  private static void test1() {
    Percolation p = new Percolation(5);
    p.print();
    testOpen(p, 3, 4);
    testOpen(p, 4, 4);
    testOpen(p, 3, 3);
    testOpen(p, 2, 3);
    testOpen(p, 1, 3);
    testOpen(p, 0, 3);
    testOpen(p, 2, 2);
    testOpen(p, 4, 2);
  }

  private static void test2() {  // test N = 1
    Percolation p = new Percolation(1);
    p.print();
    System.out.println(p.percolates());
    testOpen(p, 0, 0);
  }

  private static void test3() { // test N = 2
    Percolation p = new Percolation(2);
    p.print();
    System.out.println(p.percolates());
    // testOpen(p, 0, 0);
    testOpen(p, 0, 1);
    testOpen(p, 1, 1);
    testOpen(p, 1, 0);
  }

  private static void testOpen(Percolation p, int row, int col) {
    p.open(row, col);
    System.out.println("Opened: " + "( " + row + ", " + col + " )");
    System.out.println("#site opened: " + p.numberOfOpenSites());
    p.print();
    System.out.println("Percolated: " + p.percolates());
  }
}
