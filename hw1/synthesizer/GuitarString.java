
package synthesizer;

//Make sure this class is public
public class GuitarString {
  /**
   * Constants. Do not change. In case you're curious, the keyword final means
   * the values cannot be changed at runtime. We'll discuss this and other topics
   * in lecture on Friday.
   */
  private static final int SR = 44100;      // Sampling Rate
  private static final double DECAY = .996; // energy decay factor

  /* Buffer for storing sound data. */
  private BoundedQueue<Double> buffer;

  /* Create a guitar string of the given frequency.  */
  public GuitarString(double frequency) {
    int capacity =  (int) Math.round(SR / frequency);
    buffer = new ArrayRingBuffer<>(capacity);
    while (buffer.isFull() == false) {
      buffer.enqueue(0.0);
    }
  }


  /* Pluck the guitar string by replacing the buffer with white noise. */
  public void pluck() {

    int capacity = buffer.capacity();

    while (capacity > 0) {
      buffer.dequeue();
      buffer.enqueue(Math.random() - 0.5);
      capacity -= 1;
    }
  }

  /* Advance the simulation one time step by performing one iteration of
   * the Karplus-Strong algorithm.
   */
  public void tic() {
    buffer.enqueue((buffer.dequeue() + buffer.peek()) / 2.0 * DECAY);
    // TODO: Dequeue the front sample and enqueue a new sample that is
    //       the average of the two multiplied by the DECAY factor.
    //       Do not call StdAudio.play().
  }

  /* Return the double at the front of the buffer. */
  public double sample() {
    // TODO: Return the correct thing.
    if (buffer.isEmpty() == false) {
      return buffer.peek();
    }
    return 0;
  }
}
