package io.logbase.collections.nativelists;

import io.logbase.collections.BatchListIterator;

/**
 * Created with IntelliJ IDEA File Template.
 * User: kousik
 */
public interface LongListIterator extends BatchListIterator<Long> {

  /**
   * Reads up to rows values of data from the column into the buffer array. An attempt is made to read as many as
   * rows values, but a smaller number may be read. The number of values actually read is returned as an integer.
   * If the column has no values left then -1 is returned.
   *
   * @param buffer - the buffer into which the values are read. The object should be an array of native type compatible
   *               with type of the iterator.
   * @param offset -  the start offset in array buffer at which the values are written.
   * @param count  - number of desired values to be read.
   * @return
   */
  public int nextPrimitive(long[] buffer, int offset, int count);

  @Override
  public default Class getPrimitiveType() {
    return long.class;
  }
}
