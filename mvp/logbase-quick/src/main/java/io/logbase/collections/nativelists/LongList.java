package io.logbase.collections.nativelists;

import io.logbase.collections.BatchList;

/**
 * Created with IntelliJ IDEA File template.
 * User: Kousik
 */
public interface LongList extends BatchList<Long> {

  /**
   * Get a batch iterator that scans up to maxIndex rows.
   *
   * @param maxIndex - Largest row number where the iterator ends. If the maxIndex greater than list size then the
   *                 iterator ends at end of the list.
   * @return
   */
  public LongListIterator primitiveIterator(long maxIndex);

  public LongListReader primitiveReader(long maxIndex);

  public LongListWriter primitiveWriter();

  @Override
  public default Class<Long> type() {
    return Long.class;
  }

  /**
   * Returns the approximate memory used by this list in bytes.
   * @return
   */
  public long memSize();

}
