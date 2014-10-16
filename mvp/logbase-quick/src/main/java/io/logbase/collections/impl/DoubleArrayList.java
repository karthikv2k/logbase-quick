package io.logbase.collections.impl;

import io.logbase.buffer.BufferFactory;
import io.logbase.collections.BatchList;
import io.logbase.collections.BatchListIterator;
import io.logbase.collections.BatchListReader;
import io.logbase.collections.BatchListWriter;
import io.logbase.collections.nativelists.DoubleList;
import io.logbase.collections.nativelists.DoubleListIterator;
import io.logbase.collections.nativelists.DoubleListReader;
import io.logbase.collections.nativelists.DoubleListWriter;
import sun.misc.Unsafe;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA File Template.
 * User: Kousik
 */
public class DoubleArrayList implements DoubleList {

  private List<ByteBuffer> blocks = new ArrayList<>();
  private int defaultBlockSize = 1024;
  private long size = 0;

  public DoubleArrayList() {
  }

  public DoubleArrayList(int defaultBlockSize) {
    this.defaultBlockSize = defaultBlockSize;
  }

  public ByteBuffer addBlock() {
    return addBlock(defaultBlockSize);
  }

  public ByteBuffer addBlock(int blockSize) {
    ByteBuffer tail = BufferFactory.newBufWithDoubleCapacity(blockSize);
    synchronized (blocks) {
      blocks.add(tail.asReadOnlyBuffer());
    }
    return tail.duplicate();
  }

  public void incSize() {
    size++;
  }

  @Override
  public long size() {
    return size;
  }

  @Override
  public DoubleListIterator primitiveIterator(long maxIndex) {
    return (DoubleListIterator) iterator(maxIndex);
  }

  @Override
  public DoubleListReader primitiveReader(long maxIndex) {
    return (DoubleListReader) reader(maxIndex);
  }

  @Override
  public DoubleListWriter primitiveWriter() {
    return (DoubleListWriter) writer();
  }

  @Override
  public BatchListIterator<Double> iterator(long maxIndex) {
    return new DoubleArrayListIterator(this, blocks, maxIndex);
  }

  @Override
  public BatchListReader<Double> reader(long maxIndex) {
    return new DoubleArrayListReader(blocks, size);
  }

  @Override
  public BatchListWriter<Double> writer() {
    return new DoubleArrayListWriter(this);
  }

}
