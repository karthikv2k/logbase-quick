package io.logbase.collections.nativelists;

import io.logbase.collections.BatchListWriter;

/**
 * Created with IntelliJ IDEA File Template.
 * User: Kousik
 */
public interface FloatListWriter extends BatchListWriter<Float> {

  /**
   * Add all elements in the given primitive array (values) to the list.
   *
   * @param buffer - primitive array that contains values to be added to the list
   * @param offset - array index of values array from which values to be read
   * @param length - number of values to be read
   */
  public void addPrimitive(float[] buffer, int offset, int length);

  /**
   * Add the value to the list. The value is added at the end of the list.
   *
   * @param value
   */
  public void addPrimitive(float value);

  public default void accept(float value) {
    addPrimitive(value);
  }
}
