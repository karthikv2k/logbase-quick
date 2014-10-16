package io.logbase.collections.impl;

import io.logbase.collections.nativelists.LongListReader;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.util.List;

/**
 * Created with IntelliJ IDEA File Template.
 * User: Kousik
 */
public class LongArrayListReader implements LongListReader {
  private final LongBuffer[] blocks;
  private final long[] offset;

  LongArrayListReader(List<ByteBuffer> blocksList, long size) {
    synchronized (blocksList) {
      blocks = new LongBuffer[blocksList.size()];
      int i = 0;
      for (ByteBuffer item : blocksList) {
        blocks[i] = item.asLongBuffer();
        blocks[i].rewind();
        i++;
      }
    }
    offset = new long[blocks.length];
    long min;
    long offset1 = 0;
    for (int i = 0; i < blocks.length; i++) {
      min = Math.min((long) blocks[i].remaining(), size);
      offset1 += min;
      offset[i] = offset1;
      size -= min;
    }
  }

  @Override
  public Long get(long index) {
    return new Long(getAsLong(index));
  }

  @Override
  public long getAsLong(long index) {
    //for small arrays linear search should be faster than binary search
    long prev = 0;
    for (int i = 0; i < offset.length; i++) {
      if (index < offset[i]) {
        try {
          return blocks[i].get((int) (index - prev));
        } catch (IndexOutOfBoundsException e) {
          e.printStackTrace();
          System.out.println("Index out of bounds. Size:" + blocks[i].limit() + " index:" + (index - prev));
          throw e;
        }
      }
      prev = offset[i];
    }
    throw new ArrayIndexOutOfBoundsException("input (" + index + ") exceeds list size (" + offset[offset.length] + ")");
  }

}
