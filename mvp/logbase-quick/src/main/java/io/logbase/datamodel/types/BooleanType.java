package io.logbase.datamodel.types;

import io.logbase.datamodel.ColumnType;

public class BooleanType implements ColumnType<Boolean> {
  public boolean value;

  @Override
  public void set(Boolean value) {
    this.value = value;
  }

  @Override
  public Boolean get() {
    return new Boolean(value);
  }

  @Override
  public Class<Boolean> getNativeJavaClass() {
    return Boolean.class;
  }

  @Override
  public String toString() {
    return Boolean.toString(value);
  }
}
