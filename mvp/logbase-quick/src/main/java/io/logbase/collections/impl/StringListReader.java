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
    int bufSize = lengthIterator.optimumBufferSize();
    int end = bufSize;
    while(lengthIterator.hasNext()){
      cnt = lengthIterator.nextPrimitive(offset, start, end);
      if(cnt>0){
        for(int i=start; i<cnt; i++){
          offset[i] += offset[i-1];
        }
        start += cnt;
        end = start + bufSize;
      }
    }
  }

  @Override
  public CharBuffer get(long index) {
    return stringBuf.subSequence(offset[(int)index], offset[(int)index]);
  }

}
