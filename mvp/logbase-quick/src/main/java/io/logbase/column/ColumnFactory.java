package io.logbase.column;

public interface ColumnFactory {

  default public <T> Column createColumn(Class<T> type, String name, int numArrays){
    return null;
  }

}
