package io.logbase.collections;

import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public interface BatchIterator<E> extends Iterator<E>, Iterable<E>{

  @Override
  public Iterator<E> iterator();

  @Override
  public abstract boolean hasNext();

  @Override
  public abstract E next();

  @Override
  public void remove();

  /**
   * Reads up to rows values of data from the column into the buffer array. An attempt is made to read as many as
   * rows values, but a smaller number may be read. The number of values actually read is returned as an integer.
   * If the column has no values left then -1 is returned.
   * @param buffer - the buffer into which the values are read.
   * @param offset -  the start offset in array buffer at which the values are written.
   * @param count - number of desired values to be read.
   * @return
   */
  public int read(E[] buffer, int offset, int count);

  /**
   * Does this list support iterating using primitive arrays. Using primitive arrays, e.g. int[], is efficient
   * than using Object array, like Integer[].
   * @return true if the list supports primitive arrays on read()
   */
  public boolean primitiveTypeSupport();

  /**
   * Reads up to rows values of data from the column into the buffer array. An attempt is made to read as many as
   * rows values, but a smaller number may be read. The number of values actually read is returned as an integer.
   * If the column has no values left then -1 is returned.
   * @param buffer - the buffer into which the values are read. The object should be an array of native type compatible
   *               with type of the iterator.
   * @param offset -  the start offset in array buffer at which the values are written.
   * @param count - number of desired values to be read.
   * @return
   */
  public int readNative(Object buffer, int offset, int count);


}
