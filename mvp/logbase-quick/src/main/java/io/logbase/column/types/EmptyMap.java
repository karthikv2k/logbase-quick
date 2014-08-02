package io.logbase.column.types;

import io.logbase.column.ColumnType;

import java.util.Map;
import java.util.TreeMap;

public class EmptyMap implements ColumnType<Map> {
  public static Map value = new TreeMap();
  public static EmptyMap INSTANCE = new EmptyMap();

  @Override
  public void set(Map value) {
  }

  @Override
  public Map get() {
    return value;
  }

  @Override
  public Class<Map> getNativeJavaClass() {
    return Map.class;
  }

  @Override
  public String toString() {
    return "{}";
  }

  @Override
  public int compareTo(Map o) {
    return 0;
  }
}
