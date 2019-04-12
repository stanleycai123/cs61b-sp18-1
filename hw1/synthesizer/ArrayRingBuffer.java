
package synthesizer;

import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
  /* Index for the next dequeue or peek. */
  private int first;            // index for the next dequeue or peek
  /* Index for the next enqueue. */
  private int last;
  /* Array for storing the buffer data. */
  private T[] rb;

  /**
   * Create a new ArrayRingBuffer with the given capacity.
   */
  public ArrayRingBuffer(int capacity) {
    first = 0;
    last = 0;
    fillCount = 0;
    this.capacity = capacity;
    rb = (T[]) new Object[capacity];
  }

  /**
   * Adds x to the end of the ring buffer. If there is no room, then
   * throw new RuntimeException("Ring buffer overflow"). Exceptions
   * covered Monday.
   */
  public void enqueue(T x) {
    // [x][ ][ ]
    //  F  L
    if (isFull()) {
      throw new RuntimeException("Ring buffer overflow");
    }
    rb[last % capacity] = x;
    last += 1;
    fillCount += 1;
  }

  /**
   * Dequeue oldest item in the ring buffer. If the buffer is empty, then
   * throw new RuntimeException("Ring buffer underflow"). Exceptions
   * covered Monday.
   */
  public T dequeue() {
    if (isEmpty()) {
      throw new RuntimeException("Ring buffer underflow");
    }
    T ret = rb[first % capacity];
    first += 1;
    fillCount -= 1;
    return ret;
  }

  /**
   * Return oldest item, but don't remove it.
   */
  public T peek() {
    if (isEmpty()) {
      throw new RuntimeException("Ring buffer underflow");
    }
    return rb[first % capacity];
  }

  /**
   * Iterator
   */
  @Override
  public Iterator<T> iterator() {
    return new MyIterator();
  }

  private class MyIterator implements Iterator<T> {

    private int current;
    private int count;

    public MyIterator() {
      current = first;
      count = 0;
    }

    @Override
    public boolean hasNext() {
      return count < capacity;
    }

    @Override
    public T next() {
      T ret = rb[current];
      current += 1;
      count += 1;
      return ret;
    }
  }
}
