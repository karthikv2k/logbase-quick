package io.logbase.column.types;

import io.logbase.column.ColumnType;

public class IntType implements ColumnType<Integer> {
  public int value;

  @Override
  public void set(Integer value) {
    this.value = value;
  }

  @Override
  public Integer get() {
    return new Integer(value);
  }

  @Override
  public Class<Integer> getNativeJavaClass() {
    return Integer.class;
  }

  @Override
  public String toString() {
    return Integer.toString(value);
  }

  @Override
  public int compareTo(Integer o) {
    return value==o ? 0 : (value>o ? 1 : -1);
  }
}
