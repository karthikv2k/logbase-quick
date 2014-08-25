package io.logbase.collections;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public interface BatchListReader<E> {

  /**
   * Gets the value at the specified index. Throws exception when the index is out of bounds.
   * @param index
   * @return
   */
  public E get(long index);

  /**
   * Gets whether it can support operations on primitive data types.
   * @return
   */
  public boolean primitiveTypeSupport();

  /**
   * Reads all the values for given indices and stores it in the given primitive array.
   * @param values - The primitive array where the read values will be stored
   * @param offset - Offset in the values array where the read values will be stored
   * @param index - contains all indices to read
   * @param idxOffset - Offset in the index array from where the indices are stored
   * @param length - Number of indices to read from the index array
   */
  public void getPrimitiveArray(Object values, int offset, long[] index, int idxOffset, int length);
}
