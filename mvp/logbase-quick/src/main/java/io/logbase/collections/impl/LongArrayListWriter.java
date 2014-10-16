package io.logbase.collections.impl;

import io.logbase.collections.BatchListIterator;
import io.logbase.collections.BatchListWriter;
import io.logbase.collections.nativelists.LongListWriter;

import java.nio.LongBuffer;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created with IntelliJ IDEA File Template.
 * User: Kousik
 */
public class LongArrayListWriter implements LongListWriter {
  private boolean isClosed;
  private LongBuffer buf;
  private final LongArrayList list;


  LongArrayListWriter(LongArrayList list) {
    this.list = list;
    buf = list.addBlock().asLongBuffer();
  }

  @Override
  public void add(Object values, int offset, int length) {
    checkNotNull(values, "Null vales are not permitted");
    checkArgument(values instanceof long[], "values must be long[], found " + values.getClass().getSimpleName());
    addPrimitive((long[]) values, offset, length);
  }

  @Override
  public boolean close() {
    isClosed = true;
    return isClosed;
  }

  @Override
  public void add(Long value) {
    addPrimitive(value.longValue());
  }

  @Override
  public void addPrimitive(long[] buffer, int offset, int length) {
    //TBA optimize
    for (int i = 0; i < length; i++) {
      addPrimitive(buffer[offset + i]);
    }
  }

  @Override
  public void addPrimitive(long value) {
    if (!buf.hasRemaining()) {
      buf = list.addBlock().asLongBuffer();
    }
    buf.put(value);
    list.incSize();
  }

}
