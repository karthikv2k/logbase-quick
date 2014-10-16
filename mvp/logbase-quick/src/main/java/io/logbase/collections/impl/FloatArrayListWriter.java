package io.logbase.collections.impl;

import io.logbase.collections.nativelists.FloatListWriter;

import java.nio.FloatBuffer;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created with IntelliJ IDEA File Template.
 * User: Kousik
 */
public class FloatArrayListWriter implements FloatListWriter {
  private boolean isClosed;
  private FloatBuffer buf;
  private final FloatArrayList list;

  FloatArrayListWriter(FloatArrayList list) {
    this.list = list;
    buf = list.addBlock().asFloatBuffer();
  }

  @Override
  public void add(Object values, int offset, int length) {
    checkNotNull(values, "Null vales are not permitted");
    checkArgument(values instanceof float[], "values must be float[], found " + values.getClass().getSimpleName());
    addPrimitive((float[]) values, offset, length);
  }

  @Override
  public boolean close() {
    isClosed = true;
    return isClosed;
  }

  @Override
  public void add(Float value) {
    addPrimitive(value.floatValue());
  }

  @Override
  public void addPrimitive(float[] buffer, int offset, int length) {
    //TBA optimize
    for (int i = 0; i < length; i++) {
      addPrimitive(buffer[offset + i]);
    }
  }

  @Override
  public void addPrimitive(float value) {
    if (!buf.hasRemaining()) {
      buf = list.addBlock().asFloatBuffer();
    }
    buf.put(value);
    list.incSize();
  }

}
