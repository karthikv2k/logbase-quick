package io.logbase.collections.impl;

import io.logbase.buffer.BufferFactory;
import io.logbase.collections.BatchListIterator;
import io.logbase.collections.BatchListReader;
import io.logbase.collections.BatchListWriter;
import io.logbase.collections.Utils;
import io.logbase.collections.nativelists.*;
import io.logbase.exceptions.UnsupportedFunctionPredicateException;
import io.logbase.functions.Predicates.FunctionPredicate;

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
    this.width = Utils.getWidthFromMaxInt(maxValue - minValue);
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

  @Override
  public long memSize() {
    return buf.capacity();
  }

  @Override
  public void execute(FunctionPredicate predicate, BooleanList booleanList) {
    IntListIterator iterator = this.primitiveIterator(this.size());
    BooleanListWriter booleanWriter = booleanList.primitiveWriter();
    int[] buffer = new int[1024];

    while(iterator.hasNext()) {
      int count = iterator.nextPrimitive(buffer, 0, buffer.length);
      for(int i=0; i< count; i++) {
        booleanWriter.add(predicate.apply(buffer[i]));
      }
    }
  }
}
