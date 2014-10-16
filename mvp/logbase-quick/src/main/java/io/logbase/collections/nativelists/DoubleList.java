package io.logbase.collections.nativelists;

import io.logbase.collections.BatchList;

/**
 * Created with IntelliJ IDEA File template.
 * User: Kousik
 */
public interface DoubleList extends BatchList<Double> {

  /**
   * Get a batch iterator that scans up to maxIndex rows.
   *
   * @param maxIndex - Largest row number where the iterator ends. If the maxIndex greater than list size then the
   *                 iterator ends at end of the list.
   * @return
   */
  public DoubleListIterator primitiveIterator(long maxIndex);

  public DoubleListReader primitiveReader(long maxIndex);

  public DoubleListWriter primitiveWriter();

  @Override
  public default Class<Double> type() {
    return Double.class;
  }

}
