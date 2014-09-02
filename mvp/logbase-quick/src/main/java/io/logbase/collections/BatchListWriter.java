package io.logbase.collections;

import java.lang.reflect.Array;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public interface BatchListWriter<E> {

  /**
   * Add all elements in the given primitive array (values) to the list.
   *
   * @param values - primitive array that contains values to be added to the list
   * @param offset - array index of values array from which values to be read
   * @param length - number of values to be read
   */
  public void add(Object values, int offset, int length);

  /**
   * Add the value to the list. The value is added at the end of the list.
   *
   * @param value
   */
  public void add(E value);

  /**
   * Add all the values from the iterator to the list.
   *
   * @param iterator
   */
  public default BatchListWriter<E> addAll(BatchListIterator<E> iterator){
    int bufSize = iterator.optimumBufferSize();
    Object buffer = Array.newInstance(iterator.getPrimitiveType(), bufSize);
    int cnt;
    while(iterator.hasNext()){
      cnt = iterator.next(buffer, 0, bufSize);
      if(cnt > 0){
        add(buffer, 0, cnt);
      }
    }
    return this;
  }

  /**
   * Closing a list will make it immutable and prevent any future additions or modifications.
   *
   * @return true - if the list is closeable, false otherwise
   */
  public boolean close();
}
