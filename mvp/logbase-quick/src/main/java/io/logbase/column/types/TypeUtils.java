package io.logbase.column.types;

import io.logbase.column.ColumnType;

public class TypeUtils {

  public static Class<? extends ColumnType> getType(Object value) {
    if (value == null) {
      return NullType.class;
    } else if (value instanceof String) {
      return StringType.class;
    } else if (value instanceof Double) {
      return DoubleType.class;
    } else if (value instanceof Long) {
      return LongType.class;
    } else if (value instanceof Integer) {
      return IntType.class;
    } else if (value instanceof Boolean) {
      return BooleanType.class;
    } else {
      throw new UnsupportedOperationException("Cannot map " + value.getClass() + " to a data type");
    }
  }

  public static ColumnType cast(Object value) {
    if (value == null) {
      NullType casted = new NullType();
      return casted;
    } else if (value instanceof String) {
      StringType casted = new StringType();
      casted.set((String) value);
      return casted;
    } else if (value instanceof Double) {
      DoubleType casted = new DoubleType();
      casted.set((Double) value);
      return casted;
    } else if (value instanceof Long) {
      LongType casted = new LongType();
      casted.set((Long) value);
      return casted;
    } else if (value instanceof Integer) {
      IntType casted = new IntType();
      casted.set((Integer) value);
      return casted;
    } else if (value instanceof Boolean) {
      BooleanType casted = new BooleanType();
      casted.set((Boolean) value);
      return casted;
    } else {
      throw new UnsupportedOperationException("Cannot map " + value.getClass() + " to a data type");
    }
  }

}
