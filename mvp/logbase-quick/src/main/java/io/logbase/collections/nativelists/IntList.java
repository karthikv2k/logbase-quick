package io.logbase.collections.nativelists;

import io.logbase.collections.BatchList;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public interface IntList extends BatchList<Integer> {

  /**
   * Get a batch iterator that is scans up to maxIndex rows.
   *
   * @param maxIndex - Largest row number where the iterator ends. If the maxIndex greater than list size then the
   *                 iterator ends at end of the list.
   * @return
   */
  public IntListIterator primitiveIterator(long maxIndex);

  public IntListReader primitiveReader(long maxIndex);

  public IntListWriter primitiveWriter();

  @Override
  public default Class<Integer> type(){
    return Integer.class;
  }

}
