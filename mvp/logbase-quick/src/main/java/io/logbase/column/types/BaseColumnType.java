package io.logbase.column.types;

import io.logbase.column.ColumnType;

public abstract class BaseColumnType implements ColumnType {
  public int[] arrayIdx;

  public BaseColumnType(int numArrays) {
    if (numArrays > 0) {
      arrayIdx = new int[numArrays];
    }
  }
}
