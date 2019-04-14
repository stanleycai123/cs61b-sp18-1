package creatures;

import huglife.*;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;

import static org.junit.Assert.*;


/**
 * Tests the plip class
 *
 * @authr FIXME
 */

public class TestPlip {

  /* Replace with the magic word given in lab.
   * If you are submitting early, just put in "early" */
  public static final String MAGIC_WORD = "";

  @Test
  public void testBasics() {
    Plip p = new Plip(2);
    assertEquals(2, p.energy(), 0.01);
    assertEquals(new Color(99, 255, 76), p.color());
    p.move();
    assertEquals(1.85, p.energy(), 0.01);
    p.move();
    assertEquals(1.70, p.energy(), 0.01);
    p.stay();
    assertEquals(1.90, p.energy(), 0.01);
    p.stay();
    assertEquals(2.00, p.energy(), 0.01);
  }

  @Test
  public void testReplicate() {
    Plip p = new Plip(2);
    p.move();
    p.move();
    p.stay();
    p.stay();
    Plip newP = p.replicate();
    assertNotSame(p, newP);
    assertEquals(Double.valueOf(p.energy()), Double.valueOf(newP.energy()));
  }

  @Test
  public void testChoose() {
    Plip p = new Plip(1.2);
    HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
    surrounded.put(Direction.TOP, new Impassible());
    surrounded.put(Direction.BOTTOM, new Impassible());
    surrounded.put(Direction.LEFT, new Impassible());
    surrounded.put(Direction.RIGHT, new Impassible());

    Action actual = p.chooseAction(surrounded);
    Action expected = new Action(Action.ActionType.STAY);
    assertEquals(expected, actual);

    /** Energy > 1.0 */
    p = new Plip(1.5);
    surrounded = new HashMap<Direction, Occupant>();
    surrounded.put(Direction.TOP, new Empty());
    surrounded.put(Direction.BOTTOM, new Empty());
    surrounded.put(Direction.LEFT, new Impassible());
    surrounded.put(Direction.RIGHT, new Impassible());

    actual = p.chooseAction(surrounded);
    Action expected1 = new Action(Action.ActionType.REPLICATE, Direction.TOP);
    Action expected2 = new Action(Action.ActionType.REPLICATE, Direction.BOTTOM);

    assertTrue(actual.equals(expected1) || actual.equals(expected2));
  }

  public static void main(String[] args) {
    System.exit(jh61b.junit.textui.runClasses(TestPlip.class));
  }
} 
