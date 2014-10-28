package io.logbase.collections.impl;

import io.logbase.buffer.BufferFactory;
import io.logbase.collections.BatchListIterator;
import io.logbase.collections.BatchListReader;
import io.logbase.collections.BatchListWriter;
import io.logbase.collections.nativelists.*;
import io.logbase.exceptions.UnsupportedFunctionPredicateException;
import io.logbase.functions.Predicates.FunctionPredicate;

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
  private long memSize;

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

  @Override
  public long memSize() {
    return memSize;
  }

  @Override
  public void execute(FunctionPredicate predicate, BooleanList booleanList) {
    FloatListIterator iterator = this.primitiveIterator(this.size());
    BooleanListWriter booleanWriter = booleanList.primitiveWriter();
    float[] buffer = new float[1024];

    while(iterator.hasNext()) {
      int count = iterator.nextPrimitive(buffer, 0, buffer.length);
      for(int i=0; i< count; i++) {
        booleanWriter.add(predicate.apply(buffer[i]));
      }
    }
  }

}
