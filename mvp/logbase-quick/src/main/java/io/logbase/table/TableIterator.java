package io.logbase.table;

import io.logbase.collections.BatchIterator;
import io.logbase.column.Column;
import io.logbase.column.ColumnIterator;

public interface TableIterator extends ColumnIterator<Object[]> {

  public String[] getColumnNames();

}
