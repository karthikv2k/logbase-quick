package io.logbase.collections.impl;

import io.logbase.collections.nativelists.IntListReader;

import java.nio.IntBuffer;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class IntegerArrayListReader implements IntListReader {
  private final IntBuffer[] blocks;
  private final long[] offset;

  IntegerArrayListReader(List<IntBuffer> blocksList, long size) {
    synchronized (blocksList) {
      this.blocks = blocksList.toArray(new IntBuffer[0]);
    }
    offset = new long[blocks.length];
    long min;
    long offset1 = 0;
    for (int i = 0; i < blocks.length; i++) {
      min = Math.min((long) blocks[i].capacity(), size);
      offset1 += min;
      offset[i] = offset1;
      size -= min;
    }
  }

  @Override
  public Integer get(long index) {
    return getAsInt(index);
  }

  @Override
  public int getAsInt(long index) {
    long runningOffset = 0;
    //for small arrays linear search should be faster than binary search
    for (int i = 0; i < offset.length; i++) {
      runningOffset = runningOffset + offset[i];
      if (index < runningOffset) {
        return blocks[i].get((int) (index - (runningOffset - offset[i])));
      }
    }
    throw new ArrayIndexOutOfBoundsException("input (" + index + ") exceeds list size (" + offset[offset.length] + ")");
  }

}
