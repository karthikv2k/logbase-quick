package io.logbase.table.impl;

import com.google.common.base.Predicate;
import io.logbase.column.Column;
import io.logbase.table.Table;
import io.logbase.table.TableIterator;
import io.logbase.utils.Utils;

import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class SimpleTableIterator implements TableIterator {
  private final String[] columnNames;
  private final Column[] columns;
  private final Iterator[] iterators;
  private final Predicate<CharSequence> predicate;

  SimpleTableIterator(Table table, long maxRows) {
    this(table, maxRows, Utils.ALWAYS_TRUE_PATTERN);
  }

  SimpleTableIterator(Table table, long maxRows, Predicate<CharSequence> predicate) {
    this.predicate = predicate;

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
    iterators = new Iterator[count];

    count = 0;
    for (Map.Entry<String, Column> entry : allColumns.entrySet()) {
      Column c = entry.getValue();
      if (predicate.apply(c.getColumnName()) && c.getStartRowNum() < maxRows) {
        columnNames[count] = entry.getKey();
        columns[count] = entry.getValue();
        iterators[count] = entry.getValue().getSimpleIterator(maxRows);
        count++;
      }
    }
  }

  @Override
  public boolean hasNext() {
    for (Iterator i : iterators) {
      if (i.hasNext()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Object[] next() {
    Object[] obj = new Object[columns.length];
    for (int i = 0; i < iterators.length; i++) {
      if (iterators[i].hasNext()) {
        obj[i] = iterators[i].next();
      }
    }
    return obj;
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException("Readonly table");
  }

  @Override
  public String[] getColumnNames() {
    return columnNames;
  }

}
