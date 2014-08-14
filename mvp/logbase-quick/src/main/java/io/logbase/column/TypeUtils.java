package io.logbase.column;

public class TypeUtils {

  public static Class getType(Object value) {
    if (value == null) {
      return NullType.class;
    } else {
      return value.getClass();
    }
  }

  public static Object cast(Object value) {
    return value == null ? Column.NULL : value;
  }

}
