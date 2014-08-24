package io.logbase.collections.impl;

import io.logbase.collections.BatchIterator;
import io.logbase.collections.BatchList;

import java.nio.LongBuffer;
import java.util.IntSummaryStatistics;
import java.util.Iterator;

import static com.google.common.base.Preconditions.*;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class BitPackIntList implements BatchList<Integer> {
  private final BitPackIntBuffer buffer;
  private final LongBuffer longBuffer;
  private int arrayIndex = 0;
  private int bitIndex = 64; //index (1 indexed) where MSB of the input goes
  boolean isClosed = false;
  private int[] holder = new int[1];

  public BitPackIntList(BitPackIntBuffer buffer) {
    checkArgument(buffer.getSize() == 0, "The list is not empty.");
    this.buffer = buffer;
    this.longBuffer = buffer.writeBuffer().asLongBuffer();
  }

  public BitPackIntList(BatchIterator<Integer> source) {
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

    this.buffer = new BitPackIntBuffer(summaryStatistics);
    this.longBuffer = buffer.writeBuffer().asLongBuffer();

  }

  private void write(int[] values, int offset, int length) {
    long cur;

    for (int i = offset; i < length + offset; i++) {
      checkArgument(values[i] >= buffer.minValue && values[i] <= buffer.maxValue, "Input value is out of range.");
    }

    long buf = longBuffer.get(arrayIndex);
    for (int i = 0; i < length; i++) {
      cur = values[offset + i] - buffer.minValue;
      if (bitIndex >= buffer.width) { //minimum free space required to insert a value
        bitIndex = bitIndex - buffer.width;
      } else {
        cur = cur >>> (buffer.width - bitIndex);
        buf |= cur;
        cur = values[offset + i] - buffer.minValue;
        bitIndex = 64 - (buffer.width - bitIndex);
        longBuffer.put(arrayIndex, buf);
        arrayIndex++;
        buf = 0;
      }
      cur = cur << bitIndex;
      buf |= cur;
      buffer.incSize();
    }
    longBuffer.put(arrayIndex, buf);
  }

  @Override
  public void add(Integer value) {
    checkNotNull(value, "Null vales are not permitted");
    checkState(!isClosed, "Attempting to modify a closed list.");
    holder[0] = value.intValue();
    write(holder, 0, 1);
  }

  @Override
  public void addPrimitiveArray(Object values, int offset, int length) {
    checkNotNull(values, "Null vales are not permitted");
    checkArgument(values instanceof int[], "values must be int[], found " + values.getClass().getSimpleName());
    checkState(!isClosed, "Attempting to modify a closed list.");
    write((int[]) values, offset, length);
  }

  @Override
  public long size() {
    return buffer.getSize();
  }

  @Override
  public boolean primitiveTypeSupport() {
    return true;
  }

  @Override
  public BatchIterator<Integer> batchIterator(long maxIndex) {
    return new BitPackIntListIterator(buffer);
  }

  @Override
  public boolean close() {
    checkArgument(getBuffer().getSize() == getBuffer().listSize, "list's final size doesn't match the " +
      "initial expected size");
    isClosed = true;
    return isClosed;
  }

  @Override
  public void addAll(BatchIterator<Integer> iterator) {
    int[] buffer = new int[1024];
    int cnt;
    if (iterator.primitiveTypeSupport()) {
      while (iterator.hasNext()) {
        cnt = iterator.readNative(buffer, 0, buffer.length);
        if (cnt > 0) {
          break;
        }
        write(buffer, 0, cnt);
      }
    } else {
      while (iterator.hasNext()) {
        for (cnt = 0; cnt < buffer.length && iterator.hasNext(); cnt++) {
          buffer[cnt] = iterator.next();
        }
        write(buffer, 0, cnt);
      }
    }
  }

  public BitPackIntBuffer getBuffer() {
    return buffer;
  }
}
