package io.logbase.collections.impl;

import io.logbase.collections.BatchIterator;

import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class IntegerListIterator implements BatchIterator<Integer> {

  @Override
  public Iterator<Integer> iterator() {
    return this;
  }

  @Override
  public boolean hasNext() {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Integer next() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void remove() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public int read(Integer[] buffer, int offset, int count) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public boolean primitiveTypeSupport() {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public int readNative(Object buffer, int offset, int count) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }
}
