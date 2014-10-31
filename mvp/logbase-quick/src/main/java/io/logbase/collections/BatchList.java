package io.logbase.collections;

import io.logbase.functions.predicates.PredicateExecutor;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public interface BatchList<E> extends PredicateExecutor {

  /**
   * Gives size of the list in long. Some list implementations can have more than Integer.MAX_VALUE.
   *
   * @return
   */
  public long size();

  /**
   * Get a batch iterator that is scans up to maxIndex rows.
   *
   * @param maxIndex - Largest row number where the iterator ends. If the maxIndex greater than list size then the
   *                 iterator ends at end of the list.
   * @return
   */
  public BatchListIterator<E> iterator(long maxIndex);

  public BatchListReader<E> reader(long maxIndex);

  public BatchListWriter<E> writer();

  public Class<E> type();

  /**
   * Returns the approximate memory used by this list in bytes.
   * @return
   */
  public long memSize();

}
