package io.logbase.collections.impl;

import io.logbase.collections.BatchIterator;

import java.nio.CharBuffer;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class StringListIterator implements BatchIterator<CharBuffer> {
  private final CharBuffer stringBuffer;
  private final BatchIterator<Integer> lengthIterator;
  private int offset = 0;


  public StringListIterator(StringListBuffer listBuffer){
    this.stringBuffer = listBuffer.getReadBuffer();
    lengthIterator = listBuffer.lengthList.batchIterator(listBuffer.lengthList.size());
  }
  @Override
  public Iterator<CharBuffer> iterator() {
    return this;
  }

  @Override
  public void forEach(Consumer<? super CharBuffer> action) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Spliterator<CharBuffer> spliterator() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public boolean hasNext() {
    return lengthIterator.hasNext();
  }

  @Override
  public CharBuffer next() {
    int length = lengthIterator.next();
    offset += length;
    return stringBuffer.subSequence(offset-length, offset);
  }

  @Override
  public void remove() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void forEachRemaining(Consumer<? super CharBuffer> action) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public int read(CharBuffer[] buffer, int offset, int count) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public boolean primitiveTypeSupport() {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public int readNative(Object buffer, int offset, int count) {
    return -1;  //To change body of implemented methods use File | Settings | File Templates.
  }
}
