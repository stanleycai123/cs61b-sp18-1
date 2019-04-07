/**
 * Created by JunhaoW on 04/06/2019
 */

public interface Deque<T> {

  public void addFirst(T item);

  public void addLast(T item);

  public boolean isEmpty();

  public int size();

  public void printDeque();

  public T removeFirst();

  public T removeLast();

  public T get(int index);
}
