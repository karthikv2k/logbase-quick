package io.logbase.collections.impl;

import io.logbase.buffer.BufferFactory;
import io.logbase.collections.BatchListIterator;
import io.logbase.collections.BatchListReader;
import io.logbase.collections.BatchListWriter;
import io.logbase.collections.nativelists.FloatList;
import io.logbase.collections.nativelists.FloatListIterator;
import io.logbase.collections.nativelists.FloatListReader;
import io.logbase.collections.nativelists.FloatListWriter;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA File Template.
 * User: Kousik
 */
public class FloatArrayList implements FloatList {

  private List<ByteBuffer> blocks = new ArrayList<>();
  private int defaultBlockSize = 1024;
  private long size = 0;

  public FloatArrayList() {
  }

  public FloatArrayList(int defaultBlockSize) {
    this.defaultBlockSize = defaultBlockSize;
  }

  public ByteBuffer addBlock() {
    return addBlock(defaultBlockSize);
  }

  public ByteBuffer addBlock(int blockSize) {
    ByteBuffer tail = BufferFactory.newBufWithFloatCapacity(blockSize);
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
  public FloatListIterator primitiveIterator(long maxIndex) {
    return (FloatListIterator) iterator(maxIndex);
  }

  @Override
  public FloatListReader primitiveReader(long maxIndex) {
    return (FloatListReader) reader(maxIndex);
  }

  @Override
  public FloatListWriter primitiveWriter() {
    return (FloatListWriter) writer();
  }

  @Override
  public BatchListIterator<Float> iterator(long maxIndex) {
    return new FloatArrayListIterator(this, blocks, maxIndex);
  }

  @Override
  public BatchListReader<Float> reader(long maxIndex) {
    return new FloatArrayListReader(blocks, size);
  }

  @Override
  public BatchListWriter<Float> writer() {
    return new FloatArrayListWriter(this);
  }

}