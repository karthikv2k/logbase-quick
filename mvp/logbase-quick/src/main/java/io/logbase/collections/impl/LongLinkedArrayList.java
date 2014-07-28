package io.logbase.collections.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public class LongLinkedArrayList extends BaseList<Long> {
  private List<long[]> blocks = new ArrayList<long[]>();
  private int blockSize = 1024;
  private int blockIndex = 0;
  long[] tail = null;

  public LongLinkedArrayList() {
    addBlock();
  }

  public LongLinkedArrayList(int blockSize) {
    this.blockSize = blockSize;
    addBlock();
  }

  private void addBlock() {
    addBlock(blockSize);
  }

  private void addBlock(int blockSize) {
    checkArgument(tail == null || blockIndex >= tail.length,
      "New block can't be added when the current block is not full");
    tail = new long[blockSize];
    blocks.add(tail);
    blockIndex = 0;
  }

  @Override
  public int size() {
    long size = longSize();
    checkArgument(size <= Integer.MAX_VALUE, "List size is more than Integer.MAX_VALUE. Call longSize()");
    return (int) size;
  }

  public long longSize() {
    long size = 0;
    Iterator<long[]> iterator = blocks.iterator();
    for (int i = 0; i < blocks.size() - 1; i++) {
      size = size + iterator.next().length;
    }
    return size;
  }

  @Override
  public boolean isEmpty() {
    return longSize() == 0 ? true : false;
  }

  @Override
  public boolean add(Long integer) {
    add(integer.intValue());
    return true;
  }

  public void add(int value) {
    if (blockIndex >= tail.length) {
      addBlock();
    }
    tail[blockIndex] = value;
    blockIndex++;
  }

  @Override
  public void clear() {
    blocks.clear();
    tail = null;
    blockIndex = 0;
  }

  @Override
  public Long get(int i) {
    return get((long) i);
  }

  public Long get(long l) {
    long j = 0;
    for (long[] item : blocks) {
      j = j + item.length;
      if (l < j) {
        return item[(int) (l - (j - (long) item.length))];
      }
    }
    throw new UnsupportedOperationException("input i(" + l + ") exceeds list size (" + j + ")");
  }

}
