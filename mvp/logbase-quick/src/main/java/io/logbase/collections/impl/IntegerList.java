package io.logbase.collections.impl;

import io.logbase.collections.BatchIterator;
import io.logbase.collections.BatchList;
import io.logbase.collections.BatchListReader;
import io.logbase.collections.BatchListWriter;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class IntegerList implements BatchList<Integer>{

  private List<IntBuffer> blocks = new ArrayList<IntBuffer>();
  private int defaultBlockSize = 1024;

  public IntegerList() {
    addBlock();
  }

  public IntegerList(int defaultBlockSize) {
    this.defaultBlockSize = defaultBlockSize;
    addBlock();
  }

  public IntBuffer addBlock() {
    return addBlock(defaultBlockSize);
  }

  public IntBuffer addBlock(int blockSize) {
    IntBuffer tail = ByteBuffer.allocateDirect(blockSize * (Integer.SIZE / 8)).asIntBuffer();
    blocks.add(tail);
    return tail;
  }

  @Override
  public long size() {
    long size = 0;
    Iterator<IntBuffer> iterator = blocks.iterator();
    for (int i = 0; i < blocks.size() - 1; i++) {
      size = size + iterator.next().position();
    }
    return size;
  }

  @Override
  public BatchIterator<Integer> batchIterator(long maxIndex) {
    return null;
  }

  @Override
  public BatchListReader<Integer> reader(long maxIndex) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public BatchListWriter<Integer> writer() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

}
