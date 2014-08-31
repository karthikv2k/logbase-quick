package io.logbase.collections.impl;

import io.logbase.collections.BatchListReader;

/**
 * Created by Kousik on 25/08/14.
 */
public class BitsetListReader implements BatchListReader<Boolean> {
  private BitsetList list;

  BitsetListReader(BitsetList list, long maxIndex) {
    this.list = list;
  }

  @Override
  public Boolean get(long index) {
    return list.bits.get((int) index);
  }

}
