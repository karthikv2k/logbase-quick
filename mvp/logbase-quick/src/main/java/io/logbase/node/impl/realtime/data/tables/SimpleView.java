package io.logbase.node.impl.realtime.data.tables;

import io.logbase.datamodel.Column;
import io.logbase.datamodel.Table;
import io.logbase.datamodel.TableIterator;
import io.logbase.datamodel.View;

import java.util.*;

import static com.google.common.base.Preconditions.checkState;

public class SimpleView implements View {
  final Table[] tables;
  final String tableSelector;

  public SimpleView(SortedSet<Table> tables, String tableSelector) {
    this.tables = tables.toArray(new Table[0]);
    this.tableSelector = tableSelector;
  }

  @Override
  public TableIterator getIterator() {
    return new CombinedTableIterator();
  }

  @Override
  public Set<String> getUnderlyingTableNames() {
    return null;
  }

  @Override
  public long getNumRows() {
    return 0;
  }

  public class CombinedTableIterator implements TableIterator {
    String[] columnNames;
    TableIterator[] iterators;
    int[][] columnPos;
    int iteratorIdx = 0;

    CombinedTableIterator() {
      iterators = new TableIterator[tables.length];
      for (int i = 0; i < iterators.length; i++) {
        iterators[i] = tables[i].getIterator();
      }

      SortedSet<String> allColumns = new TreeSet<String>();
      for (int i = 0; i < iterators.length; i++) {
        String[] columnNamesTemp = iterators[i].getColumnNames();
        for (String columnName : columnNamesTemp) {
          allColumns.add(columnName);
        }
      }

      Map<String, Integer> columnNumbers = new HashMap<String, Integer>(allColumns.size() * 2);
      int count = 0;
      for (String column : allColumns) {
        columnNumbers.put(column, count);
        count++;
      }
      columnNames = allColumns.toArray(new String[0]);

      columnPos = new int[iterators.length][];
      for (int i = 0; i < columnPos.length; i++) {
        String[] columns = iterators[i].getColumnNames();
        columnPos[i] = new int[columns.length];
        for (int j = 0; j < columns.length; j++) {
          columnPos[i][j] = columnNumbers.get(columns[j]);
        }
      }
    }

    @Override
    public String[] getColumnNames() {
      return columnNames;
    }

    @Override
    public Column[] getColumns() {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean skip(long rows) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasNext() {
      if (iteratorIdx >= iterators.length) {
        return false;
      } else if (iterators[iteratorIdx].hasNext()) {
        return true;
      } else {
        iteratorIdx++;
        return hasNext();
      }
    }

    @Override
    public Object[] next() {
      checkState(hasNext(), "check hasNext() before calling next.");
      if (iterators[iteratorIdx].hasNext()) {
        Object[] row = iterators[iteratorIdx].next();
        Object[] outputRow = new Object[columnNames.length];
        for (int i = 0; i < row.length; i++) {
          outputRow[columnPos[iteratorIdx][i]] = row[i];
        }
        return outputRow;
      } else {
        iteratorIdx++;
        return next();
      }
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
}
