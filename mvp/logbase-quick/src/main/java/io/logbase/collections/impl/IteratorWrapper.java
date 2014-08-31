package io.logbase.collections.impl;

import io.logbase.collections.BatchListIterator;

import java.lang.reflect.Array;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class IteratorWrapper<E> implements Iterator<E> {
  private final BatchListIterator<E> batchIterator;
  private final Object buffer;
  private final int bufferSize;
  private int index = 0;
  private int limit = 0;

  public IteratorWrapper(BatchListIterator<E> batchListIterator) {
    this.batchIterator = batchListIterator;
    bufferSize = batchListIterator.optimumBufferSize();
    buffer = Array.newInstance(batchListIterator.getPrimitiveType(), bufferSize);
  }

  @Override
  public boolean hasNext() {
    return batchIterator.hasNext() || index < limit;
  }

  @Override
  public E next() {
    if (index < limit) {
      return (E) Array.get(buffer, index);
    } else {
      limit = batchIterator.next(buffer, 0, bufferSize);
      return next();
    }
  }
}
