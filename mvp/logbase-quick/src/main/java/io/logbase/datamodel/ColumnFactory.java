package io.logbase.datamodel;

public interface ColumnFactory {

  public <T extends ColumnType> Column createColumn(Class<T> type, String name, int numArrays);

}
