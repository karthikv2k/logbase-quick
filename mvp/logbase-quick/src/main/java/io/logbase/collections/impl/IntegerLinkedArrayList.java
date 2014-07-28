package io.logbase.collections.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public class IntegerLinkedArrayList extends BaseList<Integer> {
  private List<int[]> blocks = new ArrayList<int[]>();
  private int blockSize = 1024;
  private int blockIndex = 0;
  int[] tail = null;

  public IntegerLinkedArrayList() {
    addBlock();
  }

  public IntegerLinkedArrayList(int blockSize) {
    this.blockSize = blockSize;
    addBlock();
  }

  private void addBlock() {
    addBlock(blockSize);
  }

  private void addBlock(int blockSize) {
    checkArgument(tail == null || blockIndex >= tail.length,
      "New block can't be added when the current block is not full");
    tail = new int[blockSize];
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
    Iterator<int[]> iterator = blocks.iterator();
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
  public boolean add(Integer integer) {
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
  public Integer get(int i) {
    return get((long) i);
  }

  public Integer get(long l) {
    long j = 0;
    for (int[] item : blocks) {
      j = j + item.length;
      if (l < j) {
        return item[(int) (l - (j - (long) item.length))];
      }
    }
    throw new UnsupportedOperationException("input i(" + l + ") exceeds list size (" + j + ")");
  }

}
