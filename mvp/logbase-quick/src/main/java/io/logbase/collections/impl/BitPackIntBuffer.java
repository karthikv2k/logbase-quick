package io.logbase.collections.impl;

import java.nio.ByteBuffer;
import java.util.IntSummaryStatistics;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class BitPackIntBuffer {
  public final ByteBuffer buf;
  public final int minValue;
  public final int maxValue;
  public final int width;
  public final long listSize;
  public long size = 0;

  public BitPackIntBuffer(IntSummaryStatistics summaryStatistics) {
    this(summaryStatistics.getMin(), summaryStatistics.getMax(), summaryStatistics.getCount());
  }

  public BitPackIntBuffer(int minValue, int maxValue, long listSize) {
    checkArgument(minValue<maxValue, "maxValue should be a greater than minValue.");
    this.minValue = minValue;
    this.maxValue = maxValue;
    this.width = getWidthFromMaxInt(maxValue-minValue);
    this.listSize = listSize;
    int arraySize = (int) Math.ceil(((double)(listSize*width))/64)+1;
    buf = ByteBuffer.allocateDirect(arraySize*(Long.SIZE/8));
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
