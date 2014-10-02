package io.logbase.collections.impl;

import io.logbase.collections.BatchListReader;
import io.logbase.collections.nativelists.BooleanListReader;

/**
 * Created by Kousik on 25/08/14.
 */
public class BitsetListReader implements BooleanListReader {
  private BitsetList list;

  BitsetListReader(BitsetList list, long maxIndex) {
    this.list = list;
  }

  @Override
  public Boolean get(long index) {
    return getAsBoolean(index);
  }

  @Override
  public boolean getAsBoolean(long index) {
    return list.bits.get((int) index);
  }
}
