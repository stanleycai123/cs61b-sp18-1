package creatures;

import huglife.*;

import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * Created by JunhaoW on 04/14/2019
 */


public class Clorus extends Creature {

  private static final double moveDelta = -0.03;
  private static final double stayDelta = -0.01;

  private static final int r = 34;
  private static final int g = 0;
  private static final int b = 231;

  public Clorus(double e) {
    this("clorus");
    energy = e;
  }

  public Clorus(String n) {
    super(n);
  }

  @Override
  public void move() {
    energy += moveDelta;
  }

  @Override
  public void attack(Creature c) {
    energy += c.energy();
  }

  @Override
  public Creature replicate() {
    Clorus c = new Clorus(1);
    c.energy = energy * 0.5;

    energy = energy * 0.5;
    return c;
  }

  @Override
  public void stay() {
    energy += stayDelta;
  }

  @Override
  public Action chooseAction(Map<Direction, Occupant> neighbors) {
    List<Direction> empties = getNeighborsOfType(neighbors, "empty");
    List<Direction> plips = getNeighborsOfType(neighbors, "plip");

    if (empties.size() == 0) {
      return new Action(Action.ActionType.STAY);
    } else if (plips.size() > 0) {
      Direction moveDir = HugLifeUtils.randomEntry(plips);
      return new Action(Action.ActionType.ATTACK, moveDir);
    } else if (energy >= 1.0) {
      Direction moveDir = HugLifeUtils.randomEntry(empties);
      return new Action(Action.ActionType.REPLICATE, moveDir);
    } else {
      Direction moveDir = HugLifeUtils.randomEntry(empties);
      return new Action(Action.ActionType.MOVE, moveDir);
    }
  }

  @Override
  public Color color() {
    return color(r, g, b);
  }
}
