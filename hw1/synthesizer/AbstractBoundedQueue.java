/**
 * Created by JunhaoW on 04/11/2019
 */
package synthesizer;

public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {

  protected int fillCount;
  protected int capacity;

  public int capacity() {
    return capacity;
  }

  public int fillCount() {
    return fillCount;
  }
}
