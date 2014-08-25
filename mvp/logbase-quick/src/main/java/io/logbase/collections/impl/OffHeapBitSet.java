package io.logbase.collections.impl;

import io.logbase.collections.LBBitSet;

import java.nio.*;

/**
 * This class implements an off heap bitset. Each entry in the bitset has a boolean value.
 * Each bit is indexed and can be set or cleared using the index.
 *
 * Created by kousik on 21/08/14.
 */
public class OffHeapBitSet implements LBBitSet {
  private final IntBuffer buffer;
  private final int size;
  private final static int BITS_PER_ENTRY = Integer.BYTES * 8;

  public OffHeapBitSet(int entries) {
    int bytes = size = (entries / 8) + 1;
    buffer = ByteBuffer.allocateDirect(bytes).asIntBuffer();
  }

  public void set(int index) {
    int bufIndex = index / BITS_PER_ENTRY;
    int bitPosition = index % BITS_PER_ENTRY;
    int value = buffer.get(bufIndex);
    value |= 1 << (bitPosition);
    buffer.put(bufIndex, value);
  }

  public boolean get(int index) {
    int bufIndex = index / BITS_PER_ENTRY;
    int bitPosition = index % BITS_PER_ENTRY;
    int value = buffer.get(bufIndex);
    if ((value & (1 << (bitPosition))) != 0) {
      return true;
    }
    return false;
  }

  public int size() {
    return size;
  }
}
