/**
 * Created by JunhaoW on 04/01/2019
 */

public class Arrrrghrays {

  public static void main(String[] args) {
    Piece[] pieces = new Piece[]{
            new Piece(0, 20),
            new Piece(10, 0),
            new Piece(20, 0),
            new Piece(10, 20),
            new Piece(10, 10),
            new Piece(0, 10),
            new Piece(0, 0),
            new Piece(20, 20),
            new Piece(20, 10),
    };
    print(pieces);
    Piece[][] lat = solvePuzzle(pieces);
    print2D(lat);
  }

  public static void print2D(Piece[][] pieces) {
    System.out.println("Array:");
    for (Piece[] ps : pieces) {
      print(ps);
    }
  }

  public static void print(Piece[] ps) {
    for (Piece p : ps) {
      System.out.print("(" + p.longitude + ", " + p.latitude + ")  ");
    }
    System.out.println();
  }

  public static Piece[][] groupByLat(Piece[] p) {
    int width = (int) Math.sqrt(p.length);
    Piece[][] latGroup = new Piece[width][width];
    for (int i = 0; i < p.length; i++) {
      for (int j = 0; j < latGroup.length; j++) {
        if (latGroup[j][0] == null) { /* no candidate */
          latGroup[j][0] = p[i];
          break;
        } else if (latGroup[j][0].latitude == p[i].latitude) { /* find a spot */
          int counter; /* locate */
          for (counter = 0; counter < width; counter++) {
            if (latGroup[j][counter] == null) {
              break;
            }
          }
          latGroup[j][counter] = p[i];
          break;
        }
      }
    }
    return latGroup;
  }

  public static void sortByLat(Piece[][] p) {
    for (Piece[] pi : p) {
      for (int i = 0; i < pi.length - 1; i++) {
        for (int j = i + 1; j < pi.length; j++) {
          if (pi[i].latitude > pi[j].latitude) {
            Piece temp = pi[i];
            pi[i] = pi[j];
            pi[j] = temp;
          }
        }
      }
    }
  }

  public static void sortHalfLong(Piece[] p) {
    /** solution in my blog */
  }

  public static Piece[][] solvePuzzle(Piece[] scattered) {
    /** solution in my blog */
    return null;
  }
}


class Piece {
  public int longitude;
  public int latitude;

  public Piece(int longitude, int latitude) {
    this.longitude = longitude;
    this.latitude = latitude;
  }
}
