package io.logbase.datamodel.types;

import io.logbase.datamodel.ColumnType;

public class StringType implements ColumnType<String> {
  public String value;


  @Override
  public void set(String value) {
    this.value = value;
  }

  @Override
  public String get() {
    return value;
  }

  @Override
  public Class<String> getNativeJavaClass() {
    return String.class;
  }

  @Override
  public String toString() {
    return value;
  }

  @Override
  public int compareTo(String o) {
    return value.compareTo(o);
  }
}
