package io.logbase.collections.impl;

import io.logbase.collections.BatchListReader;
import io.logbase.collections.nativelists.*;

import java.nio.CharBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class StringBufListReader implements StringListReader {
  private final CharBuffer stringBuf;
  private final int[] offset;

  StringBufListReader(CharBuffer stringBuf, IntListIterator lengthIterator) {
    this.stringBuf = stringBuf;

    IntList offsetList = new IntegerArrayList();
    IntListWriter writer = offsetList.primitiveWriter();
    offset = new int[(int) lengthIterator.remaining() + 1];
    System.out.println("Size : " + offset.length);

    int cnt;
    int start = 1;
    int bufSize = lengthIterator.optimumBufferSize();
    while(lengthIterator.hasNext()){
      cnt = lengthIterator.nextPrimitive(offset, start, bufSize);
      if(cnt>0){
        for(int i=start; i< (start+cnt); i++){
          offset[i] += offset[i-1];
        }
        start += cnt;
        bufSize = Integer.min(lengthIterator.optimumBufferSize(), (int) lengthIterator.remaining());
      }
    }
  }

  @Override
  public CharBuffer get(long index) {
    return stringBuf.subSequence(offset[(int)index], offset[(int)index + 1]);
  }

}
