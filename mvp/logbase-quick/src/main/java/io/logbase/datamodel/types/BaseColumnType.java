package io.logbase.datamodel.types;

import io.logbase.datamodel.ColumnType;

public abstract class BaseColumnType implements ColumnType {
  public int[] arrayIdx;

  public BaseColumnType(int numArrays) {
    if (numArrays > 0) {
      arrayIdx = new int[numArrays];
    }
  }
}
