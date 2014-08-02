package io.logbase.column.types;

import io.logbase.column.ColumnType;

public class DoubleType implements ColumnType<Double> {
  public double value;

  @Override
  public void set(Double value) {
    this.value = value;
  }

  @Override
  public Double get() {
    return new Double(value);
  }

  @Override
  public Class<Double> getNativeJavaClass() {
    return Double.class;
  }

  @Override
  public String toString() {
    return Double.toString(value);
  }

  @Override
  public int compareTo(Double o) {
    return value==o ? 0 : (value>o ? 1 : -1);
  }
}
