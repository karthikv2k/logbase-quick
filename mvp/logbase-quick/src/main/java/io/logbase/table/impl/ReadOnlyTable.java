package io.logbase.table.impl;

import com.google.common.base.Predicate;
import io.logbase.column.Column;
import io.logbase.column.ColumnFactory;
import io.logbase.column.TypeUtils;
import io.logbase.event.Event;
import io.logbase.table.Table;
import io.logbase.table.TableIterator;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by Kousik on 21/10/14.
 */
public class ReadOnlyTable implements Table<Event> {
  private String tableName;
  private Map<String, Column> columns;
  private final ColumnFactory columnFactory;
  private long latestTime;
  private int rowNum = 0;

  public ReadOnlyTable(Table table) {
    this.columns = new ConcurrentSkipListMap<String, Column>();
    this.columnFactory = table.getColumnFactory();
    this.rowNum = table.getNumOfRows();
    this.latestTime = table.getLatestEventTime();
    setName(table.getTableName());

    Object[] AOColumns = table.getColumns().values().toArray(); // Append only columns
    Column ROColumn; // Read only Column
    String ROColumnName;

    for (Object obj : AOColumns) {
      Column col = (Column)obj;
      // TODO - decide on proper naming
      // TODO - acquire a lock before converting the columns
      ROColumnName = col.getColumnName() + ".RO";
      ROColumn = columnFactory.createReadOnlyColumn(col);
      if (ROColumn == null) {
        // RO conversion is not implemented for few columns. Keep it as append only
        columns.put(col.getColumnName(), col);
      } else {
        columns.put(ROColumnName, ROColumn);
      }
    }
  }

  @Override
  public void setName(String tableName) {
    //TODO decide on proper naming
    this.tableName = tableName + ".RO";
  }

  @Override
  public String getTableName() {
    return tableName;
  }

  @Override
  public void insert(Event event) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int getNumOfRows() {
    return rowNum;
  }

  @Override
  public ColumnFactory getColumnFactory() {
    return this.columnFactory;
  }

  @Override
  public TableIterator getIterator(long maxRows) {
    return new SimpleTableIterator(this, maxRows);
  }

  @Override
  public TableIterator getIterator(Predicate<CharSequence> filter) {
    return new SimpleTableIterator(this, this.rowNum, filter);
  }

  @Override
  public TableIterator getIterator(Predicate<CharSequence> filter, Column validRows) {
    return new SimpleTableIterator(this, this.rowNum, filter, validRows);
  }

  @Override
  public long getLatestEventTime() {
    return latestTime;
  }

  @Override
  public Set<String> getColumnNames() {
    return columns.keySet();
  }

  @Override
  public Map<String, Column> getColumns() {
    return columns;
  }

  @Override
  public int compareTo(Table table) {
    int temp = this.tableName.compareTo(table.getTableName());
    if (temp == 0) {
      if (this.latestTime > table.getLatestEventTime()) {
        return 1;
      } else if (this.latestTime > table.getLatestEventTime()) {
        return -1;
      } else {
        return 0;
      }
    } else {
      return temp;
    }
  }

  @Override
  public long memSize() {
    long memSize = 0;
    Object[] columnList = columns.values().toArray();
    for (Object column : columnList) {
      memSize = memSize + ((Column)column).memSize();
    }
    return memSize;
  }

  @Override
  public Column getColumn(String colName) {
    return columns.get(colName);
  }
}
