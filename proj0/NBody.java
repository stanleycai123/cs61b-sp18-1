/**
 * NBody
 */
public class NBody {
  public static void main(String[] args) {
    /* Loading Resources */
    double T = Double.parseDouble(args[0]);
    double dt = Double.parseDouble(args[1]);
    String filename = args[2];
    double R = readRadius(filename);
    Planet[] planets = readPlanets(filename);

    /* Drawing the Background */
    StdDraw.setScale(-R, R);
    StdDraw.clear();
    StdDraw.picture(0, 0, "images/starfield.jpg");
    StdDraw.show();

    /* Draw Planets */
    for (Planet p : planets) {
      p.draw();
    }
  }

  public static double readRadius(String filename) {
    In in = new In(filename);
    in.readLine();
    return in.readDouble();
  }

  public static Planet[] readPlanets(String filename) {
    In in = new In(filename);
    in.readLine();
    in.readLine();

    int count = 0;
    Planet[] planets = new Planet[5];
    while (!in.isEmpty() && count < 5) {
      planets[count] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readString());
      ++count;
    }
    return planets;
  }
}