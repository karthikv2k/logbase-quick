package io.logbase.datamodel.types;

import io.logbase.datamodel.ColumnType;

public class FloatType implements ColumnType<Float> {
  public float value;

  @Override
  public void set(Float value) {
    this.value = value;
  }

  @Override
  public Float get() {
    return new Float(value);
  }

  @Override
  public Class<Float> getNativeJavaClass() {
    return Float.class;
  }

  @Override
  public String toString() {
    return Float.toString(value);
  }

  @Override
  public int compareTo(Float o) {
    return value==o ? 0 : (value>o ? 1 : -1);
  }
}
