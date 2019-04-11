/**
 * Created by JunhaoW on 04/11/2019
 */
package synthesizer;

import java.util.Iterator;

public interface BoundedQueue<T> extends Iterable<T> {

  int capacity();

  int fillCount();

  void enqueue(T x);

  T dequeue();

  T peek();

  @Override
  Iterator<T> iterator();

  default boolean isEmpty() {
    return false;
  }

  default boolean isFull() {
    return false;
  }
}
