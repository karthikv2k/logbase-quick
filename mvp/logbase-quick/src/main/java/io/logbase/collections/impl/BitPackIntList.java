package io.logbase.collections.impl;

import io.logbase.collections.BatchIterator;
import io.logbase.collections.BatchList;
import io.logbase.collections.BatchListReader;
import io.logbase.collections.BatchListWriter;

import java.nio.ByteBuffer;
import java.util.IntSummaryStatistics;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class BitPackIntList implements BatchList<Integer> {
  public final int minValue;
  public final int maxValue;
  public final int width;
  public final long listSize;
  private final ByteBuffer buf;
  private long size = 0;

  public BitPackIntList(IntSummaryStatistics summaryStatistics) {
    this(summaryStatistics.getMin(), summaryStatistics.getMax(), summaryStatistics.getCount());
  }

  public BitPackIntList(int minValue, int maxValue, long listSize) {
    checkArgument(minValue<maxValue, "maxValue should be a greater than minValue.");
    this.minValue = minValue;
    this.maxValue = maxValue;
    this.width = getWidthFromMaxInt(maxValue-minValue);
    this.listSize = listSize;
    int arraySize = (int) Math.ceil(((double)(listSize*width))/64)+1;
    buf = ByteBuffer.allocateDirect(arraySize*(Long.SIZE/8));
  }

  public BitPackIntList(BatchIterator<Integer> source) {
    this(getStats(source));
  }

  private  static IntSummaryStatistics getStats(BatchIterator<Integer> source){
    checkArgument(source != null, "source can't be null");
    checkArgument(source.hasNext(), "source can't be empty");

    IntSummaryStatistics summaryStatistics = new IntSummaryStatistics();
    if (source.primitiveTypeSupport()) {
      int[] holder = new int[1024];
      int count;
      while (source.hasNext()) {
        count = source.readNative(holder, 0, holder.length);
        for (int i = 0; i < count; i++) {
          summaryStatistics.accept(holder[i]);
        }
      }
    } else {
      while (source.hasNext()) {
        summaryStatistics.accept(source.next());
      }
    }
    return summaryStatistics;
  }

  /**
   * give the number of bits needed to encode an int given the max value
   * @param bound max int that we want to encode
   * @return the number of bits required
   */
  private static int getWidthFromMaxInt(int bound) {
    return 32 - Integer.numberOfLeadingZeros(bound);
  }

  public void incSize() {
    size++;
  }

  public ByteBuffer writeBuffer(){
    return buf.duplicate();
  }

  public ByteBuffer readBuffer(){
    return buf.asReadOnlyBuffer();
  }

  @Override
  public long size() {
    return size;
  }

  @Override
  public BatchIterator<Integer> batchIterator(long maxIndex) {
    return new BitPackIntListIterator(this);
  }

  @Override
  public BatchListReader<Integer> reader(long maxIndex) {
    return new BitPackIntListReader(this, maxIndex);
  }

  @Override
  public BatchListWriter<Integer> writer() {
    return new BitPackIntListWriter(this);
  }
}
