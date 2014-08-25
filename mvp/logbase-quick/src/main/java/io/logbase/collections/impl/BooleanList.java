package io.logbase.collections.impl;

import io.logbase.collections.*;

/**
 * Created by Kousik on 15/08/14
 */
public class BooleanList implements BatchList<Boolean> {
  private int arrayIndex = 0;
  LBBitSet bits;

  public BooleanList() {
    this.bits = new NativeBitSet();
  }

  public BooleanList(OffHeapBitSet bits, int size) {
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
  public BatchIterator<Boolean> batchIterator(long maxIndex) {
    return new BooleanListIterator(this);
  }

  @Override
  public BatchListReader<Boolean> reader(long maxIndex) {
    return new BooleanListReader(this, maxIndex);
  }

  @Override
  public BatchListWriter<Boolean> writer() {
    return new BooleanListWriter(this);
  }

}
