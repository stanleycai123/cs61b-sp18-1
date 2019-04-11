package synthesizer;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;

/**
 * Tests the ArrayRingBuffer class.
 *
 * @author Josh Hug
 */

public class TestArrayRingBuffer {
  @Test
  public void someTest() {
    BoundedQueue<Integer> arb = new ArrayRingBuffer<>(5);
    arb.enqueue(1);
    arb.enqueue(2);
    Integer ret = arb.dequeue();
    assertEquals(Integer.valueOf(1), ret);
    ret = arb.dequeue();
    assertEquals(Integer.valueOf(2), ret);

    arb.enqueue(1);
    arb.enqueue(2);
    arb.enqueue(3);
    arb.enqueue(4);
    arb.enqueue(5);
    ret = arb.dequeue();
    assertEquals(Integer.valueOf(1), ret);

    // while (true)
    //   StdAudio.play(0.9);
  }

  @Test
  public void testIterator() {
    BoundedQueue<Integer> arb = new ArrayRingBuffer<>(5);
    arb.enqueue(1);
    arb.enqueue(2);
    arb.enqueue(3);
    arb.enqueue(4);
    arb.enqueue(5);

    Iterator<Integer> iter = arb.iterator();

    while (iter.hasNext()) {
      System.out.println(iter.next());
    }

    // for (Integer i : arb) {
    //   System.out.println(i);
    // }
  }
} 
