package io.logbase.collections.impl;

import io.logbase.collections.BatchListIterator;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Kousik on 15/08/14.
 */
public class BitsetListIterator implements BatchListIterator<Boolean> {
  private final long maxIndex;
  private BitsetList list;
  private int max_size;
  private int totalRead = 0;

  BitsetListIterator(BitsetList list, long maxIndex) {
    this.list = list;
    this.max_size = (int) list.size();
    this.maxIndex = Math.min(max_size, maxIndex);
  }

  @Override
  public long remaining() {
    return maxIndex - totalRead;
  }

  private int readInternal(boolean[] out, int offset, int count) {
        /*
         * return -1 if there are not more elements to read from list.
         */
    if (totalRead >= max_size) {
      return -1;
    }

    int idx;
    for (idx = offset;
         totalRead < max_size && idx < offset + count;
         idx++, totalRead++) {

      out[offset + idx] = list.bits.get(totalRead);
    }
    return idx - offset;
  }

  @Override
  public int next(Object buffer, int offset, int rows) throws ClassCastException {
    checkNotNull(buffer, "Null buffer is not permitted");
    checkArgument(buffer instanceof boolean[], "buffer must be boolean[], found " +
      buffer.getClass().getSimpleName());
    boolean[] nativeBuffer = (boolean[]) buffer;
    checkArgument(offset + rows <= nativeBuffer.length, "length of buffer should be greater than offset+rows.");
    return readInternal(nativeBuffer, offset, rows);
  }

  @Override
  public void rewind() {
    totalRead = 0;
  }

  @Override
  public Class getPrimitiveType() {
    return boolean.class;
  }

  @Override
  public boolean hasNext() {
    return remaining()>0;
  }

}
