package io.logbase.collections.nativelists;

import io.logbase.collections.BatchListReader;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public interface LongListReader extends BatchListReader<Long> {

  /**
   * Gets the value at the specified index. Throws exception when the index is out of bounds.
   *
   * @param index
   * @return
   */
  public long getAsLong(long index);

}
