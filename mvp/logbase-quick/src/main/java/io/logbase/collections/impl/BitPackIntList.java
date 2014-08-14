package io.logbase.collections.impl;

import io.logbase.collections.BatchIterator;

import java.util.IntSummaryStatistics;
import java.util.Iterator;

import static com.google.common.base.Preconditions.*;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class BitPackIntList extends BaseList<Integer> {
  private BitPackIntBuffer buffer;
  private int arrayIndex = 0;
  private int bitIndex = 64; //index (1 indexed) where MSB of the input goes
  boolean isClosed = false;
  private int[] holder = new int[1];

  public BitPackIntList(BitPackIntBuffer buffer) {
    checkArgument(buffer.size == 0, "The list is not empty.");
    this.buffer = buffer;
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
  }

  public void writeAll(BatchIterator<Integer> source) {
    if (source.primitiveTypeSupport()) {
      int[] holder = new int[1024];
      int count;
      while (source.hasNext()) {
        count = source.readNative(holder, 0, holder.length);
        if (count > 0) {
          write(holder, 0, count);
        }
      }
    } else {
      while (source.hasNext()) {
        add(source.next());
      }
    }
  }

  public void write(int[] values, int offset, int length) {
    long cur;

    for (int i = offset; i < length + offset; i++) {
      checkArgument(values[i] >= buffer.minValue && values[i] <= buffer.maxValue, "Input value is out of range.");
    }

    long buf = buffer.buf.getLong(arrayIndex);
    for (int i = 0; i < length; i++) {
      cur = values[offset + i] - buffer.minValue;
      if (bitIndex >= buffer.width) { //minimum free space required to insert a value
        bitIndex = bitIndex - buffer.width;
      } else {
        cur = cur >>> (buffer.width - bitIndex);
        buf |= cur;
        cur = values[offset + i] - buffer.minValue;
        bitIndex = 64 - (buffer.width - bitIndex);
        buffer.buf.setLong(arrayIndex, buf);
        arrayIndex++;
        buf = 0;
      }
      cur = cur << bitIndex;
      buf |= cur;
      buffer.size++;
    }
    buffer.buf.setLong(arrayIndex, buf);
  }

  @Override
  public boolean add(Integer value) {
    checkNotNull(value, "Null vales are not permitted");
    checkState(!isClosed, "Attempting to modify a closed list.");
    holder[0] = value.intValue();
    write(holder, 0, 1);
    return true;
  }

  @Override
  public void addPrimitiveArray(Object values, int offset, int length) {
    checkNotNull(values, "Null vales are not permitted");
    checkArgument(values instanceof int[], "values must be int[], found " + values.getClass().getSimpleName());
    checkState(!isClosed, "Attempting to modify a closed list.");
    write((int[]) values, offset, length);
  }

  @Override
  public long sizeAsLong() {
    return buffer.size;
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

  @Override
  public boolean isEmpty() {
    return size() > 0;
  }

  @Override
  public Iterator<Integer> iterator() {
    return batchIterator(-1);
  }

  public BitPackIntBuffer getBuffer() {
    return buffer;
  }
}
