package io.logbase.view;

import com.google.common.base.Predicate;

import io.logbase.column.Column;
import io.logbase.functions.FunctionFactory;
import io.logbase.querying.optiq.Expression;
import io.logbase.querying.optiq.ExpressionExecutor;
import io.logbase.table.Table;
import io.logbase.table.TableIterator;
import io.logbase.utils.Utils;

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
  public TableIterator getIterator(Predicate<CharSequence> filter) {
    return new CombinedTableIterator(filter);
  }

  @Override
  public TableIterator getIterator(Predicate<CharSequence> filter, Expression expression) {
    return new CombinedTableIterator(filter, expression, this);
  }

  @Override
  public Set<String> getUnderlyingTableNames() {
    return null;
  }

  @Override
  public long getNumRows() {
    return 0;
  }

  @Override
  public Set<String> getColumnNames() {
    Set<String> columns = new HashSet<String>();
    for (Table t : tables) {
      columns.addAll(t.getColumnNames());
    }
    return columns;
  }

  public class CombinedTableIterator implements TableIterator {
    String[] columnNames;
    TableIterator[] iterators;
    int[][] columnPos;
    int iteratorIdx = 0;

    public CombinedTableIterator() {
      this(Utils.ALWAYS_TRUE_PATTERN);
    }

    public CombinedTableIterator(Predicate<CharSequence> filter) {
      init(filter, null);
    }

    public CombinedTableIterator(Predicate<CharSequence> filter,
        Expression expression, View view) {
      FunctionFactory factory = new FunctionFactory();
      Column validRows = (Column) ExpressionExecutor.execute(expression,
          factory, view);
      init(filter, validRows);
    }

    private void init(Predicate<CharSequence> filter, Column validRows) {
      iterators = new TableIterator[tables.length];
      for (int i = 0; i < iterators.length; i++) {
        iterators[i] = tables[i].getIterator(filter, validRows);
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

  @Override
  public Column getColumn(String columnName) {
    // TODO Need to improve this and take out of Table
    for (Table t : tables) {
      for (Object colKey : t.getColumns().keySet()) {
        if (colKey.equals(columnName))
          return (Column) t.getColumns().get(colKey);
      }
    }
    return null;
  }
}
