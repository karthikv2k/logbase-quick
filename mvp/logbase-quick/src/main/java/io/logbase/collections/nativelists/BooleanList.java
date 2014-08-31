package io.logbase.collections.nativelists;

import io.logbase.collections.BatchList;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public interface BooleanList extends BatchList<Boolean> {

  /**
   * Get a batch iterator that is scans up to maxIndex rows.
   *
   * @param maxIndex - Largest row number where the iterator ends. If the maxIndex greater than list size then the
   *                 iterator ends at end of the list.
   * @return
   */
  public BooleanListIterator primitiveIterator(long maxIndex);

  public BooleanListReader primitiveReader(long maxIndex);

  public BooleanListWriter primitiveWriter();

}
