import java.util.ArrayList;
import java.util.Comparator;

/**
 * Solver for the Flight problem (#9) from CS 61B Spring 2018 Midterm 2.
 * Assumes valid input, i.e. all Flight start times are >= end times. (it should be "<=")
 * If a flight starts at the same time as a flight's end time, they are
 * considered to be in the air at the same time.
 */
public class FlightSolver {

  private ArrayList<Flight> flights;

  public FlightSolver(ArrayList<Flight> flights) {
    this.flights = flights;
  }

  public int solve() {
    // return slowSolution();
    return betterSolution();
  }

  /**
   * O(N^2)
   */
  public int slowSolution() {
    Comparator<Flight> comp = (o1, o2) -> (o1.startTime() - o2.startTime());
    flights.sort(comp); // sorted by startTime

    int N = flights.size();
    int maxVal = 0;
    for (int i = 0; i < N; i += 1) {
      Flight f1 = flights.get(i);
      int val = f1.passengers();
      for (int j = i + 1; j < N; j += 1) { // As for i = N - 1, no loop
        Flight f2 = flights.get(j);
        // f1 and f2 overlap
        if (f2.startTime() < f1.endTime()) {
          val += f2.passengers();
        }
        // f1 and future f2 won't overlap
        if (f2.startTime() >= f1.endTime()) {
          break;
        }
      }
      maxVal = (val > maxVal) ? val : maxVal; // update maxVal
    }

    return maxVal;
  }

  /**
   * O(N logN)
   */
  public int betterSolution() {
    Comparator<Flight> minStartComp = (o1, o2) -> (o1.startTime() - o2.startTime());
    Comparator<Flight> minEndComp = (o1, o2) -> (o1.endTime() - o2.endTime());
    HeapPQ<Flight> minStartPQ = new HeapPQ<>(flights.size(), minStartComp);
    HeapPQ<Flight> minEndPQ = new HeapPQ<>(flights.size(), minEndComp);

    for (Flight f : flights) { // Pointing to the same copy. That's fine.
      minStartPQ.add(f);
      minEndPQ.add(f);
    }

    int maxVal = 0;
    int tally = 0;

    while (minStartPQ.size() > 0 && minEndPQ.size() > 0) {
      int startTime = minStartPQ.get().startTime(); // just peek
      int endTime = minEndPQ.get().endTime();

      if (startTime <= endTime) {
        Flight f = minStartPQ.remove();
        tally += f.passengers();
      } else { // including endTime <= startTime
        Flight f = minEndPQ.remove();
        tally -= f.passengers();
      }

      maxVal = (tally > maxVal) ? tally : maxVal;
    }
    return maxVal;
  }


  /**
   * Test
   */
  public static void main(String[] args) {
    test1();
    test2();
  }

  public static void test1() {
    Flight f1 = new Flight(2, 4, 1);
    Flight f2 = new Flight(1, 3, 1);
    Flight f3 = new Flight(5, 10, 1);
    Flight f4 = new Flight(0, 7, 1);
    ArrayList<Flight> L = new ArrayList<>();
    L.add(f1);
    L.add(f2);
    L.add(f3);
    L.add(f4);
    FlightSolver fs = new FlightSolver(L);
    System.out.println(fs.solve());
  }


  public static void test2() {
    Flight f1 = new Flight(1, 3, 1);
    Flight f2 = new Flight(3, 5, 1);
    Flight f3 = new Flight(5, 7, 1);
    Flight f4 = new Flight(7, 9, 1);
    ArrayList<Flight> L = new ArrayList<>();
    L.add(f1);
    L.add(f2);
    L.add(f3);
    L.add(f4);
    FlightSolver fs = new FlightSolver(L);
    System.out.println(fs.solve());
  }
}
