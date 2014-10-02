package io.logbase.collections.impl;

import io.logbase.collections.*;
import io.logbase.collections.nativelists.BooleanList;
import io.logbase.collections.nativelists.BooleanListIterator;
import io.logbase.collections.nativelists.BooleanListReader;
import io.logbase.collections.nativelists.BooleanListWriter;

/**
 * Created by Kousik on 15/08/14
 */
public class BitsetList implements BooleanList {
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
    return primitiveIterator(maxIndex);
  }

  @Override
  public BatchListReader<Boolean> reader(long maxIndex) {
    return primitiveReader(maxIndex);
  }

  @Override
  public BatchListWriter<Boolean> writer() {
    return primitiveWriter();
  }

  @Override
  public BooleanListIterator primitiveIterator(long maxIndex) {
    return new BitsetListIterator(this, maxIndex);
  }

  @Override
  public BooleanListReader primitiveReader(long maxIndex) {
    return new BitsetListReader(this, maxIndex);
  }

  @Override
  public BooleanListWriter primitiveWriter() {
    return new BitsetListWriter(this);
  }
}
