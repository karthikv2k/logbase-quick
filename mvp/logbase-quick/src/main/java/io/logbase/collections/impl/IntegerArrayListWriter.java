package io.logbase.collections.impl;

import io.logbase.collections.BatchListIterator;
import io.logbase.collections.BatchListWriter;
import io.logbase.collections.nativelists.IntListWriter;

import java.nio.IntBuffer;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class IntegerArrayListWriter implements IntListWriter {
  private boolean isClosed;
  private IntBuffer buf;
  private final IntegerArrayList list;


  IntegerArrayListWriter(IntegerArrayList list) {
    this.list = list;
    buf = list.addBlock().asIntBuffer();
  }

  @Override
  public void add(Object values, int offset, int length) {
    checkNotNull(values, "Null vales are not permitted");
    checkArgument(values instanceof int[], "values must be int[], found " + values.getClass().getSimpleName());
    addPrimitive((int[]) values, offset, length);
  }

  @Override
  public boolean close() {
    isClosed = true;
    return isClosed;
  }

  @Override
  public void add(Integer integer) {
    addPrimitive(integer.intValue());
  }

  @Override
  public BatchListWriter<Integer> addAll(BatchListIterator<Integer> iterator) {
    return this;
  }

  @Override
  public void addPrimitive(int[] buffer, int offset, int length) {
    //TBA optimize
    for (int i = 0; i < length; i++) {
      addPrimitive(buffer[offset + i]);
    }
  }

  @Override
  public void addPrimitive(int value) {
    if (!buf.hasRemaining()) {
      buf = list.addBlock().asIntBuffer();
    }
    buf.put(value);
    list.incSize();
  }

}
