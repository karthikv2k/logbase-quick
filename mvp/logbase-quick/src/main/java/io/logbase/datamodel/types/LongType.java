package io.logbase.datamodel.types;

import io.logbase.datamodel.ColumnType;

public class LongType implements ColumnType<Long> {
  public long value;

  @Override
  public void set(Long value) {
    this.value = value;
  }

  @Override
  public Long get() {
    return new Long(value);
  }

  @Override
  public Class<Long> getNativeJavaClass() {
    return Long.class;
  }

  @Override
  public String toString() {
    return Long.toString(value);
  }
}
