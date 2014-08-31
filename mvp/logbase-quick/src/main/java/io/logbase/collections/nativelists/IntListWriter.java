package io.logbase.collections.nativelists;

import io.logbase.collections.BatchListWriter;

import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public interface IntListWriter extends IntConsumer, BatchListWriter<Integer> {

  /**
   * Add all elements in the given primitive array (values) to the list.
   *
   * @param buffer - primitive array that contains values to be added to the list
   * @param offset - array index of values array from which values to be read
   * @param length - number of values to be read
   */
  public void addPrimitive(int[] buffer, int offset, int length);

  /**
   * Add the value to the list. The value is added at the end of the list.
   *
   * @param value
   */
  public void addPrimitive(int value);

  @Override
  public default void accept(int value) {
    addPrimitive(value);
  }

  /**
   * Add #count values from the int supplier to the list.
   *
   * @param supplier
   * @param count
   */
  public default void accept(IntSupplier supplier, long count) {
    for (long i = 0; i < count; i++) {
      addPrimitive(supplier.getAsInt());
    }
  }

}
