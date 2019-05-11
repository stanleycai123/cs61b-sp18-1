package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

  private final static int BLOCKED = 0;
  private final static int OPEN = 1;
  // private final static int FULL = 2;

  private final static int[][] DIRECTION = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

  private int N;
  private int siteCount;
  private int[][] grid;
  private WeightedQuickUnionUF uf;

  public Percolation(int N) {
    if (N <= 0) throw new IllegalArgumentException();;
    this.N = N;
    this.siteCount = 0;
    this.grid = new int[N][N];
    this.uf = new WeightedQuickUnionUF(N * N + 2);
    /* 2 extra points - start & end for speeding */
    setupStartEnd();
  }

  private int getStart() {
    return N * N;
  }
  private int getEnd() {
    return N * N + 1;
  }
  private void setupStartEnd() {
    int start = getStart();
    int end = getEnd();
    for (int j = 0; j < N; j += 1) {
      uf.union(ToIndex(0, j), start);
      uf.union(ToIndex(N - 1, j), end);
    }
  }

  /**
   * BLOCKED: 0
   * OPEN: 1
   * FULL: 2
   */
  public void open(int row, int col) {
    checkIndex(row, col);
    grid[row][col] = OPEN; /* just open */
    siteCount += 1;

    // up / down / left / right
    for (int[] dir : DIRECTION) {
      int r = row + dir[0]; int c = col + dir[1];
      if (validIndex(r, c) && isOpen(r, c)) {
        int ind1 = ToIndex(row, col);
        int ind2 = ToIndex(r, c);
        uf.union(ind1, ind2);
      }
    }
  }

  public boolean isOpen(int row, int col) {
    checkIndex(row, col);
    return grid[row][col] == OPEN;
  }

  public boolean isFull(int row, int col) {
    checkIndex(row, col);
    if (row == 0 || row == N - 1) {
      return isOpen(row, col); /* open -> FULL; blocked -> Not FULL */
    }
    // slow approach
    /*
    for (int j = 0; j < N; j++) {
      int ind1 = ToIndex(0, j);
      int ind2 = ToIndex(row, col);
      if (uf.connected(ind1, ind2)) {
        return true;
      }
    } */
    return uf.connected(getStart(), ToIndex(row, col));
  }

  public int numberOfOpenSites() {
    return siteCount;
  }

  public boolean percolates() {
    return uf.connected(getStart(), getEnd());
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
    Percolation p = new Percolation(5);
    p.print();
    testOpen(p, 3, 4);
    testOpen(p, 4, 4);
    testOpen(p, 3, 3);
    testOpen(p, 2, 3);
    testOpen(p, 1, 3);
    testOpen(p, 0, 3);
    testOpen(p, 2, 2);
  }

  private static void testOpen(Percolation p, int row, int col) {
    p.open(row, col);
    System.out.println("Opened: " + "( " + row + ", " + col + " )");
    p.print();
    System.out.println("Percolated: " + p.percolates());
  }
}
