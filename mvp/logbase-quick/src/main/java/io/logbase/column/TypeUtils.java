package io.logbase.column;

import io.logbase.column.Column;

public class TypeUtils {

  public static Class getType(Object value) {
    if (value == null) {
      return Object.class;
    } else{
      return value.getClass();
    }
  }

  public static Object cast(Object value) {
    return value==null? Column.NULL:value;
  }

}
