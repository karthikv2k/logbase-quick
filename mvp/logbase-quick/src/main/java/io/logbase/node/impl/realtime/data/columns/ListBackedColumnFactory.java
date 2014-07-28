package io.logbase.node.impl.realtime.data.columns;

import io.logbase.datamodel.Column;
import io.logbase.datamodel.ColumnFactory;
import io.logbase.datamodel.ColumnType;

public class ListBackedColumnFactory implements ColumnFactory {

  @Override
  public <T extends ColumnType> Column createColumn(Class<T> type, String name, int numArrays) {
    return new ListBackedColumn<T>(name, numArrays);
  }

}
