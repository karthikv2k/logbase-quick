package io.logbase.collections.impl;

import io.logbase.collections.nativelists.IntListIterator;

import java.nio.LongBuffer;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class BitPackIntListIterator implements IntListIterator {
  private final BitPackIntList listBuffer;
  private final LongBuffer longBuffer;
  private int bitIndex; //index (1 indexed) where MSB of the input goes
  private int arrayIndex;
  private long totalReads;
  private final long maxIndex;


  public BitPackIntListIterator(BitPackIntList listBuffer, long maxIndex) {
    this.listBuffer = listBuffer;
    this.longBuffer = listBuffer.readBuffer().asLongBuffer();
    this.maxIndex = Math.min(listBuffer.size(), maxIndex);
    reset();
  }

  @Override
  public int nextPrimitive(int[] out, int offset, int count) {
    count = (int) Math.min(count, remaining());

    long cur;
    long cur1, cur2;
    long next;
    int i;
    for (i = offset; totalReads < listBuffer.size() && i < offset + count; i++, totalReads++) {
      cur = longBuffer.get(arrayIndex);
      if (bitIndex >= listBuffer.width) {
        cur1 = cur << (64 - bitIndex);
        cur2 = cur1 >>> (64 - listBuffer.width);
        bitIndex = bitIndex - listBuffer.width;
      } else {
        if (bitIndex > 0) {
          cur1 = cur << (64 - bitIndex);
          cur2 = cur1 >>> (64 - listBuffer.width);
        } else {
          cur2 = 0;
        }
        arrayIndex++;
        next = longBuffer.get(arrayIndex);
        bitIndex = 64 - (listBuffer.width - bitIndex);
        next = next >>> bitIndex;
        cur2 |= next;
      }
      out[offset + i] = (int) cur2 + listBuffer.minValue;
    }
    return i - offset;
  }

  private long remaining() {
    return maxIndex-totalReads;
  }

  @Override
  public int next(Object buffer, int offset, int rows) throws ClassCastException {
    checkNotNull(buffer, "Null buffer is not permitted");
    checkArgument(buffer instanceof int[], "buffer must be int[], found " + buffer.getClass().getSimpleName());
    int[] nativeBuffer = (int[]) buffer;
    checkArgument(offset + rows <= nativeBuffer.length, "length of buffer should be greater than offset+rows.");
    return nextPrimitive(nativeBuffer, offset, rows);
  }

  @Override
  public void reset() {
    arrayIndex = 0;
    totalReads = 0;
    bitIndex = 64;
  }

  @Override
  public boolean hasNext() {
    return totalReads < maxIndex;
  }


}
