package io.logbase.collections.nativelists;

import io.logbase.collections.BatchListIterator;

import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public interface IntListIterator extends BatchListIterator<Integer> {

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
  public int nextPrimitive(int[] buffer, int offset, int count);


  /**
   * iterates over the int consumer.
   *
   * @param consumer
   */
  public default void supplyTo(IntConsumer consumer) {
    int[] buf = new int[optimumBufferSize()];
    int cnt;
    while (hasNext()) {
      cnt = nextPrimitive(buf, 0, buf.length);
      for (int i = 0; i < buf.length; i++) {
        consumer.accept(buf[i]);
      }
    }
  }

  /**
   * iterates over the long consumer.
   *
   * @param consumer
   */
  public default void supplyTo(LongConsumer consumer) {
    int[] buf = new int[optimumBufferSize()];
    int cnt;
    while (hasNext()) {
      cnt = nextPrimitive(buf, 0, buf.length);
      for (int i = 0; i < buf.length; i++) {
        consumer.accept(buf[i]);
      }
    }
  }

  @Override
  public default Class getPrimitiveType() {
    return int.class;
  }

}
