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

  /**
   * Reads up to rows values of data from the column into the buffer array. An attempt is made to read as many as
   * rows values, but a smaller number may be read. The number of values actually read is returned as an integer.
   * If the column has no values left then -1 is returned.
   * @param buffer - the buffer into which the values are read.
   * @param offset -  the start offset in array buffer at which the values are written.
   * @param rows - number of desired values to be read.
   * @return
   *
  public int read(E[] buffer, int offset, int rows);*/

}
