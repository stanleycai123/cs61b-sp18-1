import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

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
        xForces[i] = p1.calcNetForceExertedByX(planets);
        yForces[i] = p1.calcNetForceExertedByY(planets);
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

  // public static Planet[] readPlanets(String filename) {
  //   In in = new In(filename);
  //   in.readLine();
  //   in.readLine();

  //   int count = 0;
  //   Planet[] planets = new Planet[5];
  //   // List<Planet> planets = new ArrayList<>();
  //   while (!in.isEmpty() && count < planets.length) {
  //     planets[count] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(),
  //         in.readString());
  //     ++count;
  //   }
  //   return planets;
  // }

  /* it do not assume the size is 5 */
  /* I have to use List, then convert it back to array XD */
  public static Planet[] readPlanets(String filename) {
    In in = new In(filename);
    in.readLine();
    in.readLine();

    List<Planet> planets = new ArrayList<>();
    while (!in.isEmpty()) {
      double xxPos;
      try {
        xxPos = in.readDouble();
      } catch (InputMismatchException e) {
        /* EOF */
        break;
      }
      double yyPos = in.readDouble();
      double xxVel = in.readDouble();
      double yyVel = in.readDouble();
      double mass = in.readDouble();
      String imgFileName = in.readString();

      Planet p = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
      planets.add(p);
    }

    /* Init size can be stored in a variable */
    /* convert it back to array type */
    Planet[] results = new Planet[planets.size()];
    for (int i = 0; i < planets.size(); ++i) {
      results[i] = planets.get(i);
    }

    return results;
  }
}