/**
 * @date 05/26/2019
 * @author Junhao Wang
 */
package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet {

  public static void main(String[] args) {
    Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
    Point p2 = new Point(3.3, 4.4);
    Point p3 = new Point(-2.9, 4.2);

    NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
    Point ret = nn.nearest(3.0, 4.0); // returns p2
    System.out.println(ret);
    ret.getX(); // evaluates to 3.3
    ret.getY(); // evaluates to 4.4
  }

  private List<Point> points;

  public NaivePointSet(List<Point> points) {
    this.points = points;
  }

  @Override
  public Point nearest(double x, double y) {
    double min = Double.POSITIVE_INFINITY;
    Point minP = null;

    Point p1 = new Point(x, y);
    for (Point p2 : points) {
      double dis = Point.distance(p1, p2);
      if (dis < min) {
        min = dis;
        minP = p2;
      }
    }
    return minP;
  }
}
