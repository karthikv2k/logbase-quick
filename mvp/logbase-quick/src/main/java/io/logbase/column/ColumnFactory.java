package io.logbase.column;

public interface ColumnFactory {

  public <T> Column createColumn(Class<T> type, String name, int numArrays);

}
