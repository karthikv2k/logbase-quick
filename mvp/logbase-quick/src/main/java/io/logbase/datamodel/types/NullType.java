package io.logbase.datamodel.types;

import io.logbase.datamodel.ColumnType;

public class NullType implements ColumnType<Object> {
  public final Object value = null;

  @Override
  public void set(Object value) {
  }

  @Override
  public Object get() {
    return value;
  }

  @Override
  public Class<Object> getNativeJavaClass() {
    return Object.class;
  }

  @Override
  public String toString() {
    return "null";
  }

  @Override
  public int compareTo(Object o) {
    return 0;
  }
}
