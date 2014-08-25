package io.logbase.collections.impl;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.IntSummaryStatistics;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class StringListBuffer {
  private final CharBuffer stringBuf;
  public final BitPackIntListWriter lengthList;

  public StringListBuffer(IntSummaryStatistics stats) {
    this(stats.getMin(), stats.getMax(), stats.getCount(), stats.getSum());
  }

  public StringListBuffer(int min, int max, long count, long sum) {
    //tba remove (int)
    stringBuf = ByteBuffer.allocateDirect((int)sum*2).asCharBuffer();
    lengthList = new BitPackIntListWriter(new BitPackIntList(min, max, count));
  }

  public CharBuffer getWriteBuffer(){
    return stringBuf.duplicate();
  }

  public CharBuffer getReadBuffer(){
    return stringBuf.asReadOnlyBuffer();
  }

}