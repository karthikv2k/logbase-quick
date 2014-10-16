package io.logbase.collections.nativelists;

import io.logbase.collections.BatchListWriter;

import java.util.function.LongConsumer;
import java.util.function.LongSupplier;

/**
 * Created with IntelliJ IDEA File Template.
 * User: Kousik
 */
public interface LongListWriter extends LongConsumer, BatchListWriter<Long> {

  /**
   * Add all elements in the given primitive array (values) to the list.
   *
   * @param buffer - primitive array that contains values to be added to the list
   * @param offset - array index of values array from which values to be read
   * @param length - number of values to be read
   */
  public void addPrimitive(long[] buffer, int offset, int length);

  /**
   * Add the value to the list. The value is added at the end of the list.
   *
   * @param value
   */
  public void addPrimitive(long value);

  @Override
  public default void accept(long value) {
    addPrimitive(value);
  }

  /**
   * Add #count values from the long supplier to the list.
   *
   * @param supplier
   * @param count
   */
  public default void accept(LongSupplier supplier, long count) {
    for (long i = 0; i < count; i++) {
      addPrimitive(supplier.getAsLong());
    }
  }

}
