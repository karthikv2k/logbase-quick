package io.logbase.table.impl;

import com.google.common.base.Predicate;
import io.logbase.collections.impl.IteratorWrapper;
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
  private Iterator<Boolean> validRowIterator = null;

  SimpleTableIterator(Table table, long maxRows) {
    this(table, maxRows, Utils.ALWAYS_TRUE_PATTERN);
  }

  SimpleTableIterator(Table table, long maxRows, Predicate<CharSequence> predicate, Column validRows) {
    this(table, maxRows, predicate);
    if (validRows != null) {
      validRowIterator = new IteratorWrapper<Boolean>(validRows.getIsPresentIterator(maxRows));
    }
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
    // If we don't have a row column, return all rows in the table
    if (validRowIterator != null) {
      return validRowIterator.hasNext();
    }

    for (Iterator i : iterators) {
      if (i.hasNext()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Object[] next() {
    // If we don't have a row column, return all rows in the table
    if (validRowIterator == null) {
      return fetchNextRow();
    }

    while(validRowIterator.hasNext()) {
      if(validRowIterator.next()) {
        return fetchNextRow();
      } else {
        // Proceed to the next valid row
        fetchNextRow();
      }
    }
    return null;
  }

  public Object[] fetchNextRow() {
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
