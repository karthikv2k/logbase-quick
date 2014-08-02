package io.logbase.column.appendonly;

import io.logbase.column.Column;
import io.logbase.column.ColumnFactory;
import io.logbase.column.ColumnType;

public class ListBackedColumnFactory implements ColumnFactory {

  @Override
  public <T extends ColumnType> Column createColumn(Class<T> type, String name, int numArrays) {
    return new ListBackedColumn<T>(name, numArrays);
  }

}
