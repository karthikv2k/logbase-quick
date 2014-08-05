package io.logbase.column.appendonly;

import io.logbase.column.Column;
import io.logbase.column.ColumnFactory;

public class AppendOnlyColumnFactory implements ColumnFactory {

  @Override
  public <T> Column createColumn(Class<T> type, String name, int numArrays) {
    return new ListBackedColumn<T>(name, numArrays);
  }

}
