package io.logbase.datamodel.types;

import io.logbase.datamodel.ColumnType;

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
}
