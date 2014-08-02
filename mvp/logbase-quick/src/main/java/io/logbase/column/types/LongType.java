package io.logbase.column.types;

import io.logbase.column.ColumnType;

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

  @Override
  public int compareTo(Long o) {
    return value==o ? 0 : (value>o ? 1 : -1);
  }
}
