package io.logbase.collections.impl;

import io.logbase.collections.LBBitSet;

import java.util.BitSet;

/**
 * Created by Kousik on 25/08/14.
 */
public class NativeBitSet implements LBBitSet {

  private BitSet bits = new BitSet();

  public void set (int index) {
    bits.set(index);
  }

  public boolean get(int index) {
    return bits.get(index);
  }

  public int size() {
    return bits.size();
  }
}
