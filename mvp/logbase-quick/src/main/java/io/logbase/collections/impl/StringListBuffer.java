package io.logbase.collections.impl;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.LongBuffer;
import java.util.IntSummaryStatistics;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class StringListBuffer {
  private final CharBuffer stringBuf;
  public final BitPackIntList lengthList;

  public StringListBuffer(IntSummaryStatistics stats) {
    this(stats.getMin(), stats.getMax(), stats.getCount(), stats.getSum());
  }

  public StringListBuffer(int min, int max, long count, long sum) {
    //tba remove (int)
    stringBuf = ByteBuffer.allocateDirect((int)sum*2).asCharBuffer();
    lengthList = new BitPackIntList(new BitPackIntBuffer(min, max, count));
  }

  public CharBuffer getWriteBuffer(){
    return stringBuf.duplicate();
  }

  public CharBuffer getReadBuffer(){
    return stringBuf.asReadOnlyBuffer();
  }

}