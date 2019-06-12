import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver {
  List<Bear> bearList = new ArrayList<>();
  List<Bed> bedList = new ArrayList<>();

  public BnBSolver(List<Bear> bears, List<Bed> beds) {
    Map<Integer, Bear> bearMap = new HashMap<>();
    Map<Integer, Bed> bedMap = new HashMap<>();

    for (Bear b : bears) { // N
      bearMap.put(b.getSize(), b);
    }

    for (Bed b : beds) { // N
      bedMap.put(b.getSize(), b);
    }

    for (Integer i : bearMap.keySet()) { // N
      bearList.add(bearMap.get(i));
      bedList.add(bedMap.get(i));
    }
  }

  /**
   * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
   */
  public List<Bear> solvedBears() {
    return bearList;
  }

  /**
   * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
   */
  public List<Bed> solvedBeds() {
    return bedList;
  }
}
