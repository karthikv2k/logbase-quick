package io.logbase.collections;

import io.logbase.column.ColumnIterator;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public interface ReadonlyList<E> {

  public void append(E value);

  public void optimize();

  public ColumnIterator<E> getIterator();
}
