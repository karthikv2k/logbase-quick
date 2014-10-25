package io.logbase.column;

/**
 * Created by Kousik on 25/10/14.
 */
public interface ColumnReader<E> {

  /**
   * Returns the value at the given index
   *
   * @param index
   * @return
   */
  public E get(long index);
}
