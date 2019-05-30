import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SeparableEnemySolver {

  public static void main(String[] args) {
    testThree();
    testFour();
    testFourFalse();
    testDisconnected();
  }

  private static void testThree() {
    Graph g = new Graph();
    g.connect("a", "b");
    g.connect("b", "c");
    test(g);
  }
  private static void testFour() {
    Graph g = new Graph();
    g.connect("a", "b");
    g.connect("b", "c");
    g.connect("c", "d");
    test(g);
  }
  private static void testFourFalse() {
    Graph g = new Graph();
    g.connect("a", "b");
    g.connect("b", "c");
    g.connect("c", "d");
    g.connect("b", "d"); // get false

    test(g);
  }
  private static void testDisconnected() {
    Graph g = new Graph();
    g.connect("a", "b");
    g.connect("c", "e");
    g.connect("e", "d");
    g.connect("c", "d");
    test(g);
  }


  private static void test(Graph g) {
    SeparableEnemySolver so = new SeparableEnemySolver(g);
    System.out.println("Is Separable: " + so.isSeparable());
  }

  // -------------------- Test End -------------------


  Graph g;

  /**
   * Creates a SeparableEnemySolver for a file with name filename. Enemy
   * relationships are biderectional (if A is an enemy of B, B is an enemy of A).
   */
  SeparableEnemySolver(String filename) throws java.io.FileNotFoundException {
    this.g = graphFromFile(filename);
  }

  /**
   * Alterntive constructor that requires a Graph object.
   */
  SeparableEnemySolver(Graph g) {
    this.g = g;
  }

  /**
   * Returns true if input is separable, false otherwise.
   * Problem Description:
   *   As the organizer, to ensure aggregate happiness,
   *   you’d like to separate the party-goers into [two groups]
   *   such that no two members of the same group are enemies.
   *   You’d like to know if this is possible for your party.
   */
  public boolean isSeparable() {
    Map<String, String> parties = new HashMap<>();
    boolean result = true;
    // Iterate every vertex (disconnected components)
    for (String v : g.labels()) {
      if (parties.containsKey(v) == false) {
        // not visited
        String marker = "U";
        result = dfs(this.g, v, parties, marker);
        if (result == false) break;
      }
    }

    // (Optional) Extra Challenge
    // Divide into three groups
    // When encounter V - V, we modify the target node to Q
    // When encounter Q - Q, we modify the source node to U
    // When encounter U - U, return false
    // Visual: https://bloggg-1254259681.cos.na-siliconvalley.myqcloud.com/b6cd9.png

    // print for testing
    System.out.println("Graph: ");
    print(g);
    System.out.println("Markers: " + parties.toString());

    return result;
  }

  private boolean dfs(Graph g, String v, Map<String, String> parties, String marker) {
    parties.put(v, marker); // mark

    for (String n : g.neighbors(v)) {
      if (parties.containsKey(n) == false) {
        // not visited
        boolean ret = dfs(g, n, parties, marker.equals("U") ? "V" : "U");
        if (ret == false) return false;
      }
      else {
        // visited
        String neighborMarker = parties.get(n);
        if (neighborMarker.equals(marker)) {
          return false; // not separable
        }
      }
    }
    return true;
  }

  private void print(Graph g) {
    Set<String> isVisited = new HashSet<>();
    for (String v : g.labels()) {
      isVisited.add(v);
      for (String neighbor : g.neighbors(v)) {
        if (isVisited.contains(neighbor) == false) {
          System.out.println(v + " - " + neighbor);
        }
      }
    }
  }


  /* HELPERS FOR READING IN CSV FILES. */

  /**
   * Creates graph from filename. File should be comma-separated. The first line
   * contains comma-separated names of all people. Subsequent lines each have two
   * comma-separated names of enemy pairs.
   */
  private Graph graphFromFile(String filename) throws FileNotFoundException {
    List<List<String>> lines = readCSV(filename);
    Graph input = new Graph();
    for (int i = 0; i < lines.size(); i++) {
      if (i == 0) {
        for (String name : lines.get(i)) {
          input.addNode(name);
        }
        continue;
      }
      assert (lines.get(i).size() == 2);
      input.connect(lines.get(i).get(0), lines.get(i).get(1));
    }
    return input;
  }

  /**
   * Reads an entire CSV and returns a List of Lists. Each inner
   * List represents a line of the CSV with each comma-seperated
   * value as an entry. Assumes CSV file does not contain commas
   * except as separators.
   * Returns null if invalid filename.
   *
   * @source https://www.baeldung.com/java-csv-file-array
   */
  private List<List<String>> readCSV(String filename) throws java.io.FileNotFoundException {
    List<List<String>> records = new ArrayList<>();
    Scanner scanner = new Scanner(new File(filename));
    while (scanner.hasNextLine()) {
      records.add(getRecordFromLine(scanner.nextLine()));
    }
    return records;
  }

  /**
   * Reads one line of a CSV.
   *
   * @source https://www.baeldung.com/java-csv-file-array
   */
  private List<String> getRecordFromLine(String line) {
    List<String> values = new ArrayList<String>();
    Scanner rowScanner = new Scanner(line);
    rowScanner.useDelimiter(",");
    while (rowScanner.hasNext()) {
      values.add(rowScanner.next().trim());
    }
    return values;
  }

  /* END HELPERS  FOR READING IN CSV FILES. */

}
