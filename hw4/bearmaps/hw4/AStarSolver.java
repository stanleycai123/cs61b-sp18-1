/**
 * @author Junhao Wang
 * @date 06/07/2019
 */
package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import bearmaps.proj2ab.ExtrinsicMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
  private SolverOutcome outcome;
  private double solutionWeight;
  private List<Vertex> solution;
  private double timeSpent;
  private Map<Vertex, Double> distTo;

  private Double getDist(Map<Vertex, Double> D, Vertex v) {
    if (D.containsKey(v)) {
      return D.get(v);
    }
    return Double.MAX_VALUE;
  }

  public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
    Stopwatch sw = new Stopwatch(); // time
    boolean reachGoal = false;
    solutionWeight = 0.0;
    solution = new LinkedList<>();
    distTo = new HashMap<>();

    ExtrinsicMinPQ<Vertex> pq = new ArrayHeapMinPQ<>();
    pq.add(start, input.estimatedDistanceToGoal(start, end));
    distTo.put(start, 0.0);

    while (pq.size() != 0 || sw.elapsedTime() < timeout) {
      Vertex v = pq.removeSmallest();
      solution.add(v);

      // reach goal condition
      if (v.equals(end)) {
        reachGoal = true;
        break;
      }

      // relax
      relax(input, pq, v, end);
    }

    // Set information
    timeSpent = sw.elapsedTime(); // time
    if (reachGoal) {
      outcome = SolverOutcome.SOLVED;
      solutionWeight = getDist(distTo, end);
    } else {
      if (timeSpent <= timeout) {
        outcome = SolverOutcome.UNSOLVABLE;
      } else {
        outcome = SolverOutcome.TIMEOUT;
      }
      solution.clear();
      solutionWeight = 0;
    }
  }

  private void relax(AStarGraph<Vertex> G, ExtrinsicMinPQ<Vertex> pq, Vertex v, Vertex goal) {
    List<WeightedEdge<Vertex>> edges = G.neighbors(v);
    for (WeightedEdge<Vertex> e : edges) {
      Vertex p = e.from();
      Vertex q = e.to();
      double w = e.weight();
      if (getDist(distTo, p) + w < getDist(distTo, q)) {
        distTo.put(q, getDist(distTo, p) + w);
        double newDist = getDist(distTo, q) + G.estimatedDistanceToGoal(q, goal);
        if (pq.contains(q)) { // change
          pq.changePriority(q, newDist);
        } else { // add
          pq.add(q, newDist);
        }
      }
    }
  }

  @Override
  public SolverOutcome outcome() {
    return outcome;
  }

  /** Should be empty if result was TIMEOUT or UNSOLVABLE */
  @Override
  public List<Vertex> solution() {
    return solution;
  }

  /** Should be 0 if result was TIMEOUT or UNSOLVABLE */
  @Override
  public double solutionWeight() {
    return solutionWeight;
  }

  /** The total number of priority queue dequeue operations */
  @Override
  public int numStatesExplored() {
    return solution.size() - 1;
  }

  /** Time spent in seconds by the constructor */
  @Override
  public double explorationTime() {
    return timeSpent;
  }
}
