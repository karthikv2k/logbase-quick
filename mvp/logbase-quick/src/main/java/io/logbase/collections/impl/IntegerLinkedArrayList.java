package io.logbase.collections.impl;

import io.logbase.collections.BatchIterator;
import io.logbase.collections.BatchList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class IntegerLinkedArrayList implements BatchList<Integer> {
  private List<int[]> blocks = new ArrayList<int[]>();
  private int blockSize = 1024;
  private int blockIndex = 0;
  int[] tail = null;
  boolean isClosed = false;

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
  public long size() {
    long size = 0;
    Iterator<int[]> iterator = blocks.iterator();
    for (int i = 0; i < blocks.size() - 1; i++) {
      size = size + iterator.next().length;
    }
    return size;
  }

  @Override
  public boolean primitiveTypeSupport() {
    return true;
  }

  @Override
  public void addPrimitiveArray(Object values, int offset, int length) {
    checkNotNull(values, "Null vales are not permitted");
    checkArgument(values instanceof int[], "values must be int[], found " + values.getClass().getSimpleName());
    int[] intValues = (int[]) values;
    //TBA optimize
    for (int i = 0; i < length; i++) {
      add(intValues[offset + i]);
    }
  }

  @Override
  public BatchIterator<Integer> batchIterator(long maxIndex) {
    return new ListIterator(maxIndex);
  }

  @Override
  public boolean close() {
    isClosed = true;
    return isClosed;
  }

  @Override
  public void add(Integer integer) {
    add(integer.intValue());
  }

  @Override
  public void addAll(BatchIterator<Integer> iterator) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  public void add(int value) {
    if (blockIndex >= tail.length) {
      addBlock();
    }
    tail[blockIndex] = value;
    blockIndex++;
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

  public class ListIterator implements BatchIterator<Integer> {
    long index = 0;
    int blockIndex = 0;
    int blockLocalIndex = 0;

    long maxIndex = 0;

    ListIterator(long maxIndex) {
      this.maxIndex = maxIndex;
    }

    @Override
    public java.util.Iterator<Integer> iterator() {
      return this;
    }

    @Override
    public boolean hasNext() {
      return index < maxIndex;
    }

    @Override
    public Integer next() {
      return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }

    @Override
    public int read(Integer[] buffer, int offset, int count) {
      return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean primitiveTypeSupport() {
      return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int readNative(Object buffer, int offset, int count) {
      return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
  }

}
