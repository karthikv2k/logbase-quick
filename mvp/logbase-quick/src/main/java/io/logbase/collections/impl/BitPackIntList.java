package io.logbase.collections.impl;

import io.logbase.buffer.BufferFactory;
import io.logbase.collections.BatchListIterator;
import io.logbase.collections.BatchListReader;
import io.logbase.collections.BatchListWriter;
import io.logbase.collections.nativelists.IntList;
import io.logbase.collections.nativelists.IntListIterator;
import io.logbase.collections.nativelists.IntListReader;
import io.logbase.collections.nativelists.IntListWriter;

import java.nio.ByteBuffer;
import java.util.IntSummaryStatistics;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class BitPackIntList implements IntList {
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
    checkArgument(minValue < maxValue, "maxValue should be a greater than minValue.");
    this.minValue = minValue;
    this.maxValue = maxValue;
    this.width = getWidthFromMaxInt(maxValue - minValue);
    this.listSize = listSize;
    int arraySize = (int) Math.ceil(((double) (listSize * width)) / 64) + 1;
    buf = BufferFactory.newBufWithLongCapacity(arraySize);
  }

  private static IntSummaryStatistics getStats(BatchListIterator<Integer> source) {
    checkArgument(source != null, "source can't be null");
    checkArgument(source.hasNext(), "source can't be empty");
    IntSummaryStatistics summaryStatistics = new IntSummaryStatistics();
    IntListIterator intIT = (IntListIterator) source;
    intIT.supplyTo(summaryStatistics);
    return summaryStatistics;
  }

  /**
   * give the number of bits needed to encode an int given the max value
   *
   * @param bound max int that we want to encode
   * @return the number of bits required
   */
  private static int getWidthFromMaxInt(int bound) {
    return 32 - Integer.numberOfLeadingZeros(bound);
  }

  public void incSize() {
    size++;
  }

  public ByteBuffer writeBuffer() {
    return buf.duplicate();
  }

  public ByteBuffer readBuffer() {
    return buf.asReadOnlyBuffer();
  }

  @Override
  public long size() {
    return size;
  }

  @Override
  public IntListIterator primitiveIterator(long maxIndex) {
    return new BitPackIntListIterator(this, maxIndex);
  }

  @Override
  public IntListReader primitiveReader(long maxIndex) {
    return new BitPackIntListReader(this, maxIndex);
  }

  @Override
  public IntListWriter primitiveWriter() {
    return new BitPackIntListWriter(this);
  }

  @Override
  public BatchListIterator<Integer> iterator(long maxIndex) {
    return primitiveIterator(maxIndex);
  }

  @Override
  public BatchListReader<Integer> reader(long maxIndex) {
    return primitiveReader(maxIndex);
  }

  @Override
  public BatchListWriter<Integer> writer() {
    return primitiveWriter();
  }
}
