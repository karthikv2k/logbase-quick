package io.logbase.collections.nativelists;

import io.logbase.collections.BatchListWriter;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public interface BooleanListWriter extends BatchListWriter<Boolean> {

  /**
   * Add all elements in the given primitive array (values) to the list.
   *
   * @param buffer - primitive array that contains values to be added to the list
   * @param offset - array index of values array from which values to be read
   * @param length - number of values to be read
   */
  public void addNative(boolean[] buffer, int offset, int length);

  /**
   * Add the value to the list. The value is added at the end of the list.
   *
   * @param value
   */
  public void accept(boolean value);

}
