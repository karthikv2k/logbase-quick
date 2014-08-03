package io.logbase.collections;

import io.logbase.column.ColumnIterator;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public interface ReadonlyListWriter<E> {

  public void append(E value);

  public void append(E[] values);

  public void appendNativeArray(Object values);

  public void close();

}
