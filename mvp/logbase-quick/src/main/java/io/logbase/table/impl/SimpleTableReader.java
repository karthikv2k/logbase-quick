package io.logbase.table.impl;

import com.google.common.base.Predicate;
import io.logbase.collections.nativelists.BooleanList;
import io.logbase.collections.nativelists.BooleanListReader;
import io.logbase.column.Column;
import io.logbase.column.ColumnReader;
import io.logbase.functions.Predicates.PredicateType;
import io.logbase.table.Table;
import io.logbase.table.TableIterator;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by Kousik on 30/10/14.
 */
public class SimpleTableReader implements TableIterator {
  private final String[] columnNames;
  private final Column[] columns;
  private final ColumnReader[] columnReaders;
  private long size, index = 0;
  private final BooleanListReader validRowReader;


  public SimpleTableReader(Table table, long maxRows,
                           Predicate<CharSequence> predicate, BooleanListReader validRowReader) {
    this.size = maxRows;
    this. validRowReader = validRowReader;

    Map<String, Column> allColumns = table.getColumns();
    int count = 0;
    for (Map.Entry<String, Column> entry : allColumns.entrySet()) {
      Column c = entry.getValue();
      if (predicate.apply(c.getColumnName()) && c.getStartRowNum() < maxRows) {
        count++;
      }
    }

    columnNames = new String[count];
    columns = new Column[count];
    columnReaders = new ColumnReader[count];
    count = 0;
    for (Map.Entry<String, Column> entry : allColumns.entrySet()) {
      Column c = entry.getValue();
      if (predicate.apply(c.getColumnName()) && c.getStartRowNum() < maxRows) {
        columnNames[count] = entry.getKey();
        columns[count] = entry.getValue();
        columnReaders[count] = entry.getValue().getSimpleReader(maxRows);
        count++;
      }
    }
  }
  @Override
  public String[] getColumnNames() {
    return columnNames;
  }

  @Override
  public boolean hasNext() {
    while (index < size && !validRowReader.get(index)) {
      index++;
    }
    return index < size;
  }

  @Override
  public Object[] next() {
    Object[] obj = new Object[columns.length];
    Object temp;
    for (int i=0; i<columns.length; i++) {
      temp = columnReaders[i].get(index);
      if (temp != null) {
        obj[i] = temp;
      }
    }
    index++;
    return obj;
  }
}
