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
    drawBackground(R);

    /* Draw Planets */
    drawPlanets(planets);

    /* Show */
    StdDraw.show();

    /* Animations */
    StdDraw.enableDoubleBuffering();
    double t = 0;
    while (t < T) {
      /* calc forces */
      double[] xForces = new double[planets.length];
      double[] yForces = new double[planets.length];
      for (int i = 0; i < planets.length; ++i) {
        Planet p1 = planets[i];
        for (int j = 0; j < planets.length; ++j) {
          Planet p2 = planets[j];
          /* don't calc the forces exerted by itself */
          if (p1.equals(p2)) {
            continue;
          }
          xForces[i] += p1.calcForceExertedByX(p2);
          yForces[i] += p1.calcForceExertedByY(p2);
        }
      }

      /* update */
      for (int i = 0; i < planets.length; ++i) {
        Planet p = planets[i];
        p.update(dt, xForces[i], yForces[i]);
        drawBackground(R);
        drawPlanets(planets);
        StdDraw.show();
        StdDraw.pause(1);
      }

      t += dt;
    }

    /* Printing the Universe */
    StdOut.printf("%d\n", planets.length);
    StdOut.printf("%.2e\n", R);
    for (int i = 0; i < planets.length; i++) {
      StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n", planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
          planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
    }
  }

  private static void drawBackground(double R) {
    StdDraw.setScale(-R, R);
    StdDraw.clear();
    StdDraw.picture(0, 0, "images/starfield.jpg");
  }

  private static void drawPlanets(Planet[] planets) {
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
    while (!in.isEmpty() && count < planets.length) {
      planets[count] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(),
          in.readString());
      ++count;
    }
    return planets;
  }
}