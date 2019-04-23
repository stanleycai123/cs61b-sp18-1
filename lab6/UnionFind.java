public class UnionFind {

  private int[] parent;

  /* Creates a UnionFind data structure holding n vertices. Initially, all
     vertices are in disjoint sets. */
  public UnionFind(int n) {
    parent = new int[n];
    for (int i = 0; i < n; i += 1) {
      parent[i] = -1;
    }
  }

  /* Throws an exception if v1 is not a valid index. */
  private void validate(int vertex) {
    if (vertex < 0 || vertex > parent.length - 1) {
      throw new IllegalArgumentException();
    }
  }

  /* Returns the size of the set v1 belongs to. */
  public int sizeOf(int v1) {
    int root = find(v1);
    return -parent(root);
  }

  /* Returns the parent of v1. If v1 is the root of a tree, returns the
     negative size of the tree for which v1 is the root. */
  public int parent(int v1) {
    validate(v1);
    return parent[v1];
  }

  /* Returns true if nodes v1 and v2 are connected. */
  public boolean connected(int v1, int v2) {
    return find(v1) == find(v2);
  }

  /* Connects two elements v1 and v2 together. v1 and v2 can be any valid
     elements, and a union-by-size heuristic is used. If the sizes of the sets
     are equal, tie break by connecting v1's root to v2's root. Unioning a
     vertex with itself or vertices that are already connected should not
     change the sets but may alter the internal structure of the data. */
  public void union(int v1, int v2) {
    int r1 = find(v1);
    int r2 = find(v2);
    int s1 = sizeOf(r1);
    int s2 = sizeOf(r2);
    if (s1 <= s2) {
      int size = sizeOf(r1);
      parent[r1] = r2;
      parent[r2] += -size;
    } else {
      int size = sizeOf(r2);
      parent[r2] = r1;
      parent[r1] += -size;
    }
  }

  /* Returns the root of the set V belongs to. Path-compression is employed
     allowing for fast search-time. */
  /* Actually, I can write two finds to do PC. */
  public int find(int vertex) {
    validate(vertex);
    int p = vertex;
    while (parent(p) >= 0) {
      p = parent(p);
    }

    /** Path Compression */
    int root = p;
    p = vertex; /* reset */
    while (parent(p) >= 0) {
      int temp = p;
      p = parent(p); /* next */
      parent[temp] = root;
    }

    return p;
  }
  /** Without Path Compression */
  //    5  (-> -6)
  //  /  \  \
  //  2   3  9
  //  |      |
  //  1      8

  public static void main(String[] args) {
    UnionFind uf = new UnionFind(10);
    uf.union(1, 2);
    uf.union(3, 5);
    uf.union(2, 3);
    uf.union(8, 9);
    uf.union(1, 8);
  }
  /** With Path Compression */
  //   5  (-> -6)
  //  / | \ \
  // 1  2  3 9
  //         |
  //         8
}
