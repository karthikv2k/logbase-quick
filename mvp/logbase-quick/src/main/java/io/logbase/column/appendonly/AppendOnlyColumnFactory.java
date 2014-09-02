package io.logbase.column.appendonly;

import io.logbase.collections.impl.BitsetList;
import io.logbase.collections.impl.IntegerArrayList;
import io.logbase.collections.impl.ListBackedBatchList;
import io.logbase.column.Column;
import io.logbase.column.ColumnFactory;
import io.logbase.column.TypeUtils;

public class AppendOnlyColumnFactory implements ColumnFactory {

  @Override
  public <T> Column createColumn(Class<T> type, String name, int numArrays) {
    if (type.equals(Integer.class)) {
      return new AppendOnlyColumn(name, numArrays, new IntegerArrayList());
    } else if (type.equals(Boolean.class)) {
      return new AppendOnlyColumn(name, numArrays, new BitsetList());
    } else {
      return new AppendOnlyColumn(name, numArrays, new ListBackedBatchList(TypeUtils.getPrimitiveType(type)));
    }
  }

}
