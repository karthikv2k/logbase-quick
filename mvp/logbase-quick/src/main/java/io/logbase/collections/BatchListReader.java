package io.logbase.collections;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public interface BatchListReader<E> {

  /**
   * Gets the value at the specified index. Throws exception when the index is out of bounds.
   *
   * @param index
   * @return
   */
  public E get(long index);

}
