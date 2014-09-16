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
    int start = 1;
    int end = lengthIterator.optimumBufferSize();
    while(lengthIterator.hasNext()){
      cnt = lengthIterator.nextPrimitive(offset, start, end);
      if(cnt>0){
        start += cnt;
        end += end;
        for()
      }
    }
    offsetReader = offsetList.primitiveReader(offsetList.size());
  }

  @Override
  public CharBuffer get(long index) {
    return
  }

}
