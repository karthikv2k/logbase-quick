package io.logbase.collections.impl;

import io.logbase.collections.BatchList;
import io.logbase.collections.BatchListIterator;
import io.logbase.collections.BatchListReader;
import io.logbase.collections.BatchListWriter;
import io.logbase.collections.nativelists.IntList;
import io.logbase.collections.nativelists.IntListIterator;
import io.logbase.collections.nativelists.IntListReader;
import io.logbase.collections.nativelists.IntListWriter;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class IntegerArrayList implements BatchList<Integer>, IntList {

  private List<IntBuffer> blocks = new ArrayList<>();
  private int defaultBlockSize = 100*1000*1024;
  private long size = 0;

  public IntegerArrayList() {
  }

  public IntegerArrayList(int defaultBlockSize) {
    this.defaultBlockSize = defaultBlockSize;
  }

  public IntBuffer addBlock() {
    return addBlock(defaultBlockSize);
  }

  public IntBuffer addBlock(int blockSize) {
    IntBuffer tail = ByteBuffer.allocateDirect(blockSize * (Integer.SIZE / 8)).asIntBuffer();
    synchronized (blocks) {
      blocks.add(tail.asReadOnlyBuffer());
    }
    return tail;
  }

  public void incSize() {
    size++;
  }

  @Override
  public long size() {
    return size;
  }

  @Override
  public IntListIterator primitiveIterator(long maxIndex) {
    return (IntListIterator) iterator(maxIndex);
  }

  @Override
  public IntListReader primitiveReader(long maxIndex) {
    return (IntListReader) reader(maxIndex);
  }

  @Override
  public IntListWriter primitiveWriter() {
    return (IntListWriter) writer();
  }

  @Override
  public BatchListIterator<Integer> iterator(long maxIndex) {
    return new IntegerArrayListIterator(this, blocks, maxIndex);
  }

  @Override
  public BatchListReader<Integer> reader(long maxIndex) {
    return new IntegerArrayListReader(blocks, size);
  }

  @Override
  public BatchListWriter<Integer> writer() {
    return new IntegerArrayListWriter(this);
  }

}
