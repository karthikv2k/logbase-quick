package io.logbase.collections.impl;

import io.logbase.collections.BatchListReader;
import io.logbase.collections.nativelists.IntList;
import io.logbase.collections.nativelists.IntListIterator;
import io.logbase.collections.nativelists.IntListReader;
import io.logbase.collections.nativelists.IntListWriter;

import java.nio.CharBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class StringListReader implements BatchListReader<CharBuffer> {
  private final CharBuffer stringBuf;
  private final int[] offset;

  StringListReader(CharBuffer stringBuf, IntListIterator lengthIterator) {
    this.stringBuf = stringBuf;

    IntList offsetList = new IntegerArrayList();
    IntListWriter writer = offsetList.primitiveWriter();
    offset = new int[(int) lengthIterator.remaining()];

    int cnt;
    int prev = 0;
    int start = 0;
    int bufSize = lengthIterator.optimumBufferSize();
    while(lengthIterator.hasNext()){
      cnt = lengthIterator.nextPrimitive(offset, start, bufSize);
      start += cnt;
      bufSize = Integer.min(lengthIterator.optimumBufferSize(), (int) lengthIterator.remaining());
    }
  }

  @Override
  public CharBuffer get(long index) {
    return stringBuf.subSequence(offset[(int)index], offset[(int)index]);
  }

}
