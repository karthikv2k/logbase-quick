package io.logbase.collections;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public interface BatchList<E> extends List<E> {

  /**
   * Gives size of the list in long. Some list implementations can have more than Integer.MAX_VALUE.
   *
   * @return
   */
  public long sizeAsLong();

  /**
   * Does this list support adding and iterating using primitive arrays. Using primitive arrays, e.g. int[], is efficient
   * than using Object array, like Integer[].
   *
   * @return true if the list supports primitive arrays on add() and batchIterator.read()
   */
  public boolean primitiveTypeSupport();

  /**
   * Add all elements in the given primitive array (values) to the list.
   *
   * @param values - primitive array that contains values to be added to the list
   * @param offset - array index of values array from which values to be read
   * @param length - number of values to be read
   */
  public void addPrimitiveArray(Object values, int offset, int length);

  public BatchIterator<E> batchIterator(long maxIndex);

  /**
   * Closing a list will make it immutable and prevent any future additions or modifications.
   *
   * @return true - if the list is closeable, false otherwise
   */
  public boolean close();


  public void addAll(BatchIterator<E> iterator);

}
