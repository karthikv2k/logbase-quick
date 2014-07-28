package io.logbase.datamodel.types;

import io.logbase.datamodel.ColumnType;

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
}
