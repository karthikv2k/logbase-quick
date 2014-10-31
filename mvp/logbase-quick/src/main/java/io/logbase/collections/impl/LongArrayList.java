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
 * Created with IntelliJ IDEA File Template.
 * User: Kousik
 */
public class LongArrayList implements LongList {

  private List<ByteBuffer> blocks = new ArrayList<>();
  private int defaultBlockSize = 1024;
  private long size = 0;
  private long memSize = 0;

  public LongArrayList() {
  }

  public LongArrayList(int defaultBlockSize) {
    this.defaultBlockSize = defaultBlockSize;
  }

  public ByteBuffer addBlock() {
    return addBlock(defaultBlockSize);
  }

  public ByteBuffer addBlock(int blockSize) {
    ByteBuffer tail = BufferFactory.newBufWithLongCapacity(blockSize);
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
  public LongListIterator primitiveIterator(long maxIndex) {
    return (LongListIterator) iterator(maxIndex);
  }

  @Override
  public LongListReader primitiveReader(long maxIndex) {
    return (LongListReader) reader(maxIndex);
  }

  @Override
  public LongListWriter primitiveWriter() {
    return (LongListWriter) writer();
  }

  @Override
  public BatchListIterator<Long> iterator(long maxIndex) {
    return new LongArrayListIterator(this, blocks, maxIndex);
  }

  @Override
  public BatchListReader<Long> reader(long maxIndex) {
    return new LongArrayListReader(blocks, size);
  }

  @Override
  public BatchListWriter<Long> writer() {
    return new LongArrayListWriter(this);
  }

  @Override
  public long memSize() {
    return memSize;
  }

  @Override
  public void execute(LBPredicate predicate, BooleanList booleanList) {
    LongListIterator iterator = this.primitiveIterator(this.size());
    BooleanListWriter booleanWriter = booleanList.primitiveWriter();
    long[] buffer = new long[1024];
    int i, count;

    while(iterator.hasNext()) {
      count = iterator.nextPrimitive(buffer, 0, buffer.length);
      for(i=0; i< count; i++) {
        booleanWriter.add(predicate.apply(buffer[i]));
      }
    }
  }

}
