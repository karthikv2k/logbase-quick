package io.logbase.collections.impl;

import io.logbase.collections.BatchIterator;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class IntToBooleanIterator implements BatchIterator<Boolean> {
  private final BatchIterator<Integer> it;

  public IntToBooleanIterator(BatchIterator<Integer> it){
    this.it = it;
  }

  @Override
  public Iterator<Boolean> iterator() {
    return this;
  }

  @Override
  public boolean hasNext() {
    return it.hasNext();
  }

  @Override
  public Boolean next() {
    return it.next()>0;
  }

  @Override
  public void remove() {
    it.remove();
  }

  @Override
  public int read(Boolean[] buffer, int offset, int count) {
    int i;
    for(i=offset; i<count; i++){
      if(hasNext()){
        buffer[i] = next();
      }
    }
    return i-offset;
  }

  @Override
  public boolean primitiveTypeSupport() {
    return false;
  }

  @Override
  public int readNative(Object buffer, int offset, int count) {
    return -1;
  }
}
