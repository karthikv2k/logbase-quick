package io.logbase.collections;

import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public interface ReadonlyListReader<E> extends Iterator<E> {

  /**
   * Reads up to rows values of data from the column into the buffer array. An attempt is made to read as many as
   * rows values, but a smaller number may be read. The number of values actually read is returned as an integer.
   * If the column has no values left then -1 is returned.
   * @param buffer - the buffer into which the values are read.
   * @param offset -  the start offset in array buffer at which the values are written.
   * @param rows - number of desired values to be read.
   * @return
   */
  public int read(E[] buffer, int offset, int rows);

  /**
   * Reads up to rows values of data from the column into the buffer array. An attempt is made to read as many as
   * rows values, but a smaller number may be read. The number of values actually read is returned as an integer.
   * If the column has no values left then -1 is returned. Here a native array is used instead of Object array. This
   * increases performance and memory size.  The buffer object should be an native array object that can store the List
   * type or a class cast exception is thrown.
   * Native array mapping:
   * Integer - int[]
   * Long - long[]
   * Double  - double[]
   * Float - float[]
   * Boolean - byte[] 0 - false, 1 - true
   * @param buffer - the buffer into which the values are read.
   * @param offset -  the start offset in array buffer at which the values are written.
   * @param rows - number of desired values to be read.
   * @return
   */
  public int read(Object buffer, int offset, int rows) throws ClassCastException;

}
