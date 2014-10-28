package io.logbase.collections.impl;

import io.logbase.buffer.BufferFactory;
import io.logbase.collections.BatchList;
import io.logbase.collections.BatchListIterator;
import io.logbase.collections.BatchListReader;
import io.logbase.collections.BatchListWriter;
import io.logbase.collections.nativelists.*;
import io.logbase.exceptions.UnsupportedFunctionPredicateException;
import io.logbase.functions.Predicates.FunctionPredicate;
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
  private long memSize = 0;

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

  @Override
  public long memSize() {
    return memSize;
  }


  @Override
  public void execute(FunctionPredicate predicate, BooleanList booleanList) {
    DoubleListIterator iterator = this.primitiveIterator(this.size());
    BooleanListWriter booleanWriter = booleanList.primitiveWriter();
    double[] buffer = new double[1024];
    int i, count;

    while(iterator.hasNext()) {
      count = iterator.nextPrimitive(buffer, 0, buffer.length);
      for(i=0; i< count; i++) {
        booleanWriter.add(predicate.apply(buffer[i]));
      }
    }
  }


}
