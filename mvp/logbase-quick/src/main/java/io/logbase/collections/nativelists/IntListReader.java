package io.logbase.collections.nativelists;

import io.logbase.collections.BatchListReader;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public interface IntListReader extends BatchListReader<Integer> {

  /**
   * Gets the value at the specified index. Throws exception when the index is out of bounds.
   *
   * @param index
   * @return
   */
  public int getAsInt(long index);

}
