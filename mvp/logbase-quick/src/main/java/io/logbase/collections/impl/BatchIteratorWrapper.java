package io.logbase.collections.impl;

import io.logbase.collections.BatchIterator;

import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class BatchIteratorWrapper<E> implements BatchIterator<E> {
  Iterator<E> iterator;

  public BatchIteratorWrapper(Iterator<E> iterator) {
    this.iterator = iterator;
  }

  @Override
  public Iterator<E> iterator() {
    return iterator;
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  public E next() {
    return iterator.next();
  }

  @Override
  public void remove() {
    iterator.remove();
  }

  @Override
  public int read(E[] buffer, int offset, int count) {
    int i = 0;
    for (; i < count && iterator.hasNext(); i++) {
      buffer[i + offset] = iterator.next();
    }
    return i;
  }

  @Override
  public boolean primitiveTypeSupport() {
    return false;
  }

  @Override
  public int readNative(Object buffer, int offset, int count) {
    throw new UnsupportedOperationException();
  }
}
