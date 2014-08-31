package io.logbase.collections.impl;

import io.logbase.collections.nativelists.IntListReader;

import java.nio.LongBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class BitPackIntListReader implements IntListReader {
  private BitPackIntList list;
  private LongBuffer buffer;
  private final int width;

  BitPackIntListReader(BitPackIntList list, long maxIndex) {
    this.list = list;
    this.width = list.width;
    buffer = list.readBuffer().asLongBuffer();
  }

  @Override
  public Integer get(long index) {
    return getAsInt(index);
  }

  @Override
  public int getAsInt(long index) {
    int newIndex = (int)((index*width)/64);
    int bitIndex = 64-(int)((index*width) % 64);

    long buf =  buffer.get(newIndex);
    long cur1 = buf << (64 - bitIndex);
    long cur2 = cur1 >>> (64 - width);
    long next;

    if(bitIndex<width){
      next = buffer.get(newIndex+1);
      bitIndex = 64 - (width - bitIndex);
      next = next >>> bitIndex;
      cur2 |= next;
    }

    if(cur2<0){
      return  0;
    }

    return ((int) cur2)+list.minValue;
  }
}
