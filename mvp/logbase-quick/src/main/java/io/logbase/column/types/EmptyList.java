package io.logbase.column.types;

import io.logbase.column.ColumnType;

import java.util.LinkedList;
import java.util.List;

public class EmptyList implements ColumnType<List> {

  public static List value = new LinkedList();
  public static EmptyList INSTANCE = new EmptyList();


  @Override
  public Class<List> getNativeJavaClass() {
    return List.class;
  }

  @Override
  public void set(List value) {
  }

  @Override
  public List get() {
    return value;
  }

  @Override
  public String toString() {
    return "[]";
  }

  @Override
  public int compareTo(List o) {
    return 0;
  }
}
