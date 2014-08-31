package io.logbase.collections.impl;

import io.logbase.collections.BatchListIterator;
import io.logbase.collections.nativelists.IntListIterator;

import java.nio.CharBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class StringListIterator implements BatchListIterator<CharBuffer> {
  private final CharBuffer stringBuffer;
  private final StringList list;
  private IntListIterator lengthIterator;
  private int offset = 0;
  private int[] lengthBuf;

  public StringListIterator(StringList list) {
    this.stringBuffer = list.getReadBuffer();
    this.list = list;
    reset();
  }

  @Override
  public boolean hasNext() {
    return lengthIterator.hasNext();
  }

  @Override
  public int next(Object buffer, int offset, int count) {
    /*CharBuffer
    int curCount = 0;
    while(curCount<count){
      lengthIterator
      lengthIterator.nextPrimitive()
      offset += length;
    }
    return stringBuffer.subSequence(offset-length, offset);*/
    return -1;
  }

  @Override
  public void reset() {
    lengthIterator = list.lengthList.primitiveIterator(list.lengthList.size());
    lengthBuf = new int[lengthIterator.optimumBufferSize()];
  }

  @Override
  public Class getPrimitiveType() {
    return CharBuffer.class;
  }

}
