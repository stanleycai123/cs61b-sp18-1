package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

  private final static int BLOCKED = 0;
  private final static int OPEN = 1;
  private final static int FULL = 2;

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
    this.uf = new WeightedQuickUnionUF(N * N);
  }

  /**
   * BLOCKED: 0
   * OPEN: 1
   * FULL: 2
   */
  public void open(int row, int col) {
    checkIndex(row, col);
    if (row == 0) {
      grid[row][col] = FULL; /* water */
    } else {
      grid[row][col] = OPEN; /* just open */
    }
    siteCount += 1;

    // up / down / left / right
    for (int[] dir : DIRECTION) {
      int r = row + dir[0]; int c = col + dir[1];
      if (validIndex(r, c) && isOpen(r, c)) {
        int ind1 = ToIndex(row, col);
        int ind2 = ToIndex(r, c);
        uf.union(ind1, ind2);
        /* FULL? */
        if (isFull(row, col) || isFull(r, c)) {
          grid[row][col] = grid[r][c] = FULL;
        }
      }
    }
  }

  public boolean isOpen(int row, int col) {
    checkIndex(row, col);
    return grid[row][col] != BLOCKED;
  }

  public boolean isFull(int row, int col) {
    checkIndex(row, col);
    return grid[row][col] == FULL;
  }

  public int numberOfOpenSites() {
    return siteCount;
  }

  public boolean percolates() {
    return false;
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
    System.out.println("Grid: " + N + " x " + N);
    for (int i = 0; i < N; i += 1) {
      for (int j = 0; j < N; j += 1) {
        System.out.print(grid[i][j] + "  ");
      }
      System.out.println();
    }
  }

  public static void main(String[] args) {
    Percolation p = new Percolation(5);
    p.print();
    testOpen(p, 3, 4);
  }

  private static void testOpen(Percolation p, int row, int col) {
    p.open(row, col);
    System.out.println("Opened: " + "( " + row + ", " + col + " )");
    p.print();
  }
}
