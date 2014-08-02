package io.logbase.column;

import java.util.Iterator;

/**
 * This interface is used to iterate through values of a Column in a efficient manner than using point
 * get method @Column.getValue(int rowNum)
 */
public interface ColumnIterator<E> extends Iterator<E> {

  /**
   * skips @rows from the current position
   *
   * @param rows Number of rows to skip. This should be non-negative.
   * @return
   */
  public boolean skip(long rows);
}
