package io.logbase.collections.impl;

import io.logbase.collections.*;

/**
 * Created by Kousik on 15/08/14
 */
public class BitsetList implements BatchList<Boolean> {
  private int arrayIndex = 0;
  LBBitSet bits;

  public BitsetList() {
    this.bits = new NativeBitSet();
  }

  public BitsetList(int size) {
    this.bits = new OffHeapBitSet(size);
  }

  public void incrSize() {
    arrayIndex++;
  }

  @Override
  public long size() {
    return arrayIndex;
  }

  @Override
  public BatchListIterator<Boolean> iterator(long maxIndex) {
    return new BitsetListIterator(this, maxIndex);
  }

  @Override
  public BatchListReader<Boolean> reader(long maxIndex) {
    return new BitsetListReader(this, maxIndex);
  }

  @Override
  public BatchListWriter<Boolean> writer() {
    return new BitsetListWriter(this);
  }

}
