package io.logbase.collections.impl;

import io.logbase.collections.BatchListIterator;

import java.lang.reflect.Array;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class BatchListIteratorWrapper<E> implements BatchListIterator<E> {
  private final Iterator<E> iterator;
  private final long maxIndex;
  private final Class primitiveType;
  private int index;

  public BatchListIteratorWrapper(Iterator<E> iterator, long maxIndex, Class primitiveType) {
    this.iterator = iterator;
    this.maxIndex = maxIndex;
    this.primitiveType = primitiveType;
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext() && index < maxIndex;
  }

  @Override
  public int next(Object buffer, int offset, int count) {
    int i = 0;
    for (; i < count && iterator.hasNext() && index < maxIndex; i++) {
      Array.set(buffer, i + offset, iterator.next());
      index++;
    }
    return i;
  }

  @Override
  public void rewind() {
    throw new UnsupportedOperationException();
  }

  @Override
  public long remaining() {
    return -1;
  }

  @Override
  public Class getPrimitiveType() {
    return primitiveType;
  }

}
