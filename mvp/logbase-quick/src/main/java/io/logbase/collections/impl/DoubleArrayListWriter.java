package io.logbase.collections.impl;

import io.logbase.collections.BatchListIterator;
import io.logbase.collections.BatchListWriter;
import io.logbase.collections.nativelists.DoubleListWriter;

import java.nio.DoubleBuffer;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created with IntelliJ IDEA File Template.
 * User: Kousik
 */
public class DoubleArrayListWriter implements DoubleListWriter {
  private boolean isClosed;
  private DoubleBuffer buf;
  private final DoubleArrayList list;


  DoubleArrayListWriter(DoubleArrayList list) {
    this.list = list;
    buf = list.addBlock().asDoubleBuffer();
  }

  @Override
  public void add(Object values, int offset, int length) {
    checkNotNull(values, "Null vales are not permitted");
    checkArgument(values instanceof double[], "values must be double[], found " + values.getClass().getSimpleName());
    addPrimitive((double[]) values, offset, length);
  }

  @Override
  public boolean close() {
    isClosed = true;
    return isClosed;
  }

  @Override
  public void add(Double value) {
    addPrimitive(value.doubleValue());
  }

  @Override
  public void addPrimitive(double[] buffer, int offset, int length) {
    //TBA optimize
    for (int i = 0; i < length; i++) {
      addPrimitive(buffer[offset + i]);
    }
  }

  @Override
  public void addPrimitive(double value) {
    if (!buf.hasRemaining()) {
      buf = list.addBlock().asDoubleBuffer();
    }
    buf.put(value);
    list.incSize();
  }

}
