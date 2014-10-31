package io.logbase.collections.impl;

import io.logbase.buffer.BufferFactory;
import io.logbase.collections.BatchListIterator;
import io.logbase.collections.BatchListReader;
import io.logbase.collections.BatchListWriter;
import io.logbase.collections.nativelists.*;
import io.logbase.functions.predicates.LBPredicate;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class IntegerArrayList implements IntList {

  private List<ByteBuffer> blocks = new ArrayList<>();
  private int defaultBlockSize = 1024;
  private long size = 0;
  private long memSize = 0;

  public IntegerArrayList() {
  }

  public IntegerArrayList(int defaultBlockSize) {
    this.defaultBlockSize = defaultBlockSize;
  }

  public ByteBuffer addBlock() {
    return addBlock(defaultBlockSize);
  }

  public ByteBuffer addBlock(int blockSize) {
    ByteBuffer tail = BufferFactory.newBufWithIntCapacity(blockSize);
    memSize = memSize + tail.capacity();
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
  public IntListIterator primitiveIterator(long maxIndex) {
    return new IntegerArrayListIterator(this, blocks, maxIndex);
  }

  @Override
  public IntListReader primitiveReader(long maxIndex) {
    return new IntegerArrayListReader(blocks, size);
  }

  @Override
  public IntListWriter primitiveWriter() {
    return new IntegerArrayListWriter(this);
  }

  @Override
  public BatchListIterator<Integer> iterator(long maxIndex) {
    return primitiveIterator(maxIndex);
  }

  @Override
  public BatchListReader<Integer> reader(long maxIndex) {
    return primitiveReader(maxIndex);
  }

  @Override
  public BatchListWriter<Integer> writer() {
    return primitiveWriter();
  }

  @Override
  public long memSize() {
    return memSize;
  }

  @Override
  public void execute(LBPredicate predicate, BooleanList booleanList) {
    IntListIterator iterator = this.primitiveIterator(this.size());
    BooleanListWriter booleanWriter = booleanList.primitiveWriter();
    int[] buffer = new int[1024];
    int i, count;

    while(iterator.hasNext()) {
      count = iterator.nextPrimitive(buffer, 0, buffer.length);
      for(i=0; i< count; i++) {
        booleanWriter.add(predicate.apply(buffer[i]));
      }
    }
  }

}
