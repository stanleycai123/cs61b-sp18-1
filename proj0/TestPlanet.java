/**
 * TestPlanet
 */
public class TestPlanet {
  public static void main(String[] args) {
    checkPlanet();
}

  private static void checkPlanet() {
    Planet p1 = new Planet(1, 2, 3, 4, 10, "p1");
    Planet p2 = new Planet(11, 12, 13, 14, 20, "p2");
    System.out.println(p1.calcForceExertedBy(p2));
  }
}