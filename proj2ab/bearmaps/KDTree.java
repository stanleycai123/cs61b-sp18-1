/**
 * @date 05/26/2019
 * @author Junhao Wang
 */
package bearmaps;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.List;

/**
 * KDTree class
 */
public class KDTree implements PointSet {

  public static void main(String[] args) {
    simpleTest(); // PASS
    randomTest(); // PASS (cool!)
  }

  private static void simpleTest() {
    Point A = new Point(2, 3);
    Point B = new Point(4, 2);
    Point C = new Point(4, 5);
    Point D = new Point(3, 3);
    Point E = new Point(1, 5);
    Point F = new Point(4, 4);
    KDTree kd = new KDTree(List.of(A, B, C, D, E, F));
    System.out.println(); // debug here 2 see if the tree is okay. PASS

    // nearest
    Point goal = new Point(0, 7);
    System.out.println(kd.nearest(goal.getX(), goal.getY())); // should be (1, 5) by respectively using naive and actual implementation
    // pass without pruning - PASS
    // pass with pruning - PASS
  }

  private static void randomTest() {
    int N = 1000000;

    KDTree kd = new KDTree(null);
    for (int i = 0; i < N; i += 1) {
      double x = StdRandom.uniform(N);
      double y = StdRandom.uniform(N);
      kd.insert(new Point(x, y));
    }

    Point goal = new Point(StdRandom.uniform(N), StdRandom.uniform(N));
    Stopwatch sw = new Stopwatch();
    Point p1 = kd.testNearest(goal.getX(), goal.getY(), false);
    System.out.println("Naive : " + p1 + " | with time: " + sw.elapsedTime() + " s");

    sw = new Stopwatch();
    Point p2 = kd.testNearest(goal.getX(), goal.getY(), true);
    System.out.println("Better: " + p2 + " | with time: " + sw.elapsedTime() + " s");

    System.out.println("Pass: " + p1.equals(p2));
  }

  /**
   * Node class
   */
  private class Node {
    Point point;
    int depth;
    Node left;
    Node right;

    Node(Point point, int depth) {
      this.point = point;
      this.depth = depth;
      left = null;
      right = null;
    }
  }

  /**
   * Field
   */
  private Node root;

  public KDTree(List<Point> points) {
    if (points == null || points.isEmpty()) return;
    for (Point p : points) {
      insert(p);
    }
  }

  /**
   * Insert
   */
  public void insert(Point p) {
    if (p == null) throw new IllegalArgumentException();
    root = insert(root, p, 0);
  }
  // helper
  public Node insert(Node n, Point p, int depth) {
    if (n == null) return new Node(p, depth);

    boolean useLeft = leftCoordinate(n.depth);
    double pPos = useLeft ? p.getX() : p.getY(); // determine using X or Y-axis
    double nPos = useLeft ? n.point.getX() : n.point.getY();

    if (pPos < nPos) {
      n.left = insert(n.left, p, depth + 1);
    } else {
      n.right = insert(n.right, p, depth + 1);
    }

    return n;
  }

  /**
   * nearest
   */
  private Point testNearest(double x, double y, boolean betterOpt) {
    Point goal = new Point(x, y);
    if (betterOpt == false) { // naive
      return naiveNearest(root, goal, root).point;
    } else {
      return betterNearest(root, goal, root).point;
    }
  }

  @Override
  public Point nearest(double x, double y) {
    Point goal = new Point(x, y);
    Node bestNode = betterNearest(root, goal, root);
    return bestNode.point;
  }

  private Node naiveNearest(Node n, Point goal, Node best) {
    if (n == null) return best;

    double nDis = Point.distance(n.point, goal);
    double gDis = Point.distance(best.point, goal);
    if (nDis < gDis) best = n; // update best
    best = naiveNearest(n.left, goal, best);
    best = naiveNearest(n.right, goal, best);

    return best;
  }

  private Node betterNearest(Node n, Point goal, Node best) {
    if (n == null) return best;

    double nDis = Point.distance(n.point, goal);
    double gDis = Point.distance(best.point, goal);
    if (nDis < gDis) best = n; // update best

    Node goodSide = null;
    Node badSide = null;

    boolean useLeft = leftCoordinate(n.depth); // use X or Y
    double goalPos = useLeft ? goal.getX() : goal.getY();
    double nPos = useLeft ? n.point.getX() : n.point.getY();

    if (goalPos < nPos) {
      goodSide = n.left; badSide = n.right;
    } else {
      goodSide = n.right; badSide = n.left;
    }

    best = betterNearest(goodSide, goal, best);

    if (needPruning(useLeft, n, goal, best)) { // pruning
      best = betterNearest(badSide, goal, best);
    }

    return best;
  }

  private boolean needPruning(boolean useLeft, Node n, Point goal, Node best) {
    Point virtual = useLeft ? new Point(n.point.getX(), goal.getY()) : new Point(goal.getX(), n.point.getY());

    double vDis = Point.distance(virtual, goal);
    double bDis = Point.distance(best.point, goal);
    return (vDis < bDis); // possibly need pruning
  }


  private boolean leftCoordinate(int depth) {
    return depth % 2 == 0;
  }
}
