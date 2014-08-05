package io.logbase.collections.impl;

import io.logbase.buffer.Buffer;
import io.logbase.buffer.LongHeapBuffer;
import io.logbase.buffer.LongOffheapBuffer;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class BitPackIntList {
  public final Buffer buf;
  public final int maxValue;
  public final int width;
  public final long listSize;
  public long size = 0;

  public BitPackIntList(int maxValue, long listSize){
    checkArgument(maxValue>0, "maxValue should be a positive number.");
    this.maxValue = maxValue;
    this.width = getWidthFromMaxInt(maxValue);
    this.listSize = listSize;
    int arraySize = (int) Math.ceil(((double)(listSize*width))/64)+1;
    buf = new LongOffheapBuffer(arraySize);
  }

  /**
   * give the number of bits needed to encode an int given the max value
   * @param bound max int that we want to encode
   * @return the number of bits required
   */
  public static int getWidthFromMaxInt(int bound) {
    return 32 - Integer.numberOfLeadingZeros(bound);
  }

}
