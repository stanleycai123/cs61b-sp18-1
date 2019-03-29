/**
 * Planet
 */
public class Planet {

  private final static double G = 6.67e-11;

  public double xxPos;
  public double yyPos;
  public double xxVel;
  public double yyVel;
  public double mass;
  public String imgFileName;
  
  public Planet(double xP, double yP, double xV,
                double yV, double m, String img) {
    this.xxPos = xP;
    this.yyPos = yP;
    this.xxVel = xV;
    this.yyVel = yV;
    this.mass = m;
    this.imgFileName = img;
  }

  public Planet(Planet p) {
    this.xxPos = p.xxPos;
    this.yyPos = p.yyPos;
    this.xxVel = p.xxVel;
    this.yyVel = p.yyVel;
    this.mass = p.mass;
    this.imgFileName = p.imgFileName;
  }

  public double calcDistance(Planet other) {
    double dx = this.xxPos - other.xxPos;
    double dy = this.yyPos - other.yyPos;
    return Math.sqrt(dx * dx + dy * dy);  /* pow() is slower */
  }

  public double calcForceExertedBy(Planet other) {
    double dis = this.calcDistance(other);
    return G * this.mass * other.mass / (dis * dis);
  }

  /* NOTE: Do not use Math.abs to fix sign issues with these methods. This will cause issues later when drawing planets. */
  public double calcForceExertedByX(Planet other) {
    double dx = other.xxPos - this.xxPos;
    double r = this.calcDistance(other);
    double f = this.calcForceExertedBy(other);
    return f * dx / r;
  }

  public double calcForceExertedByY(Planet other) {
    double dy = other.yyPos - this.yyPos;
    double r = this.calcDistance(other);
    double f = this.calcForceExertedBy(other);
    return f * dy / r;
  }

  public void update(double dt, double xF, double yF) {
    double ax = xF / this.mass;
    double ay = yF / this.mass;
    this.xxVel += dt * ax;
    this.yyVel += dt * ay;
    this.xxPos += dt * this.xxVel;
    this.yyPos += dt * this.yyVel;
  }

  public void draw() {
    StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
    StdDraw.show();
  }
}