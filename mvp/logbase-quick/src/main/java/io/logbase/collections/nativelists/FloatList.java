package io.logbase.collections.nativelists;

import io.logbase.collections.BatchList;

/**
 * Created with IntelliJ IDEA File template.
 * User: Kousik
 */
public interface FloatList extends BatchList<Float> {

  /**
   * Get a batch iterator that scans up to maxIndex rows.
   *
   * @param maxIndex - Largest row number where the iterator ends. If the maxIndex greater than list size then the
   *                 iterator ends at end of the list.
   * @return
   */
  public FloatListIterator primitiveIterator(long maxIndex);

  public FloatListReader primitiveReader(long maxIndex);

  public FloatListWriter primitiveWriter();

  @Override
  public default Class<Float> type() {
    return Float.class;
  }

}
