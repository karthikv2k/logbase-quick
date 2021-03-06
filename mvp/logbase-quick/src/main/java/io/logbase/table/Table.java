package io.logbase.table;

import com.google.common.base.Predicate;
import io.logbase.column.Column;
import io.logbase.column.ColumnFactory;
import io.logbase.event.Event;
import io.logbase.node.Node;

import java.util.Map;
import java.util.Set;

public interface Table<E extends Event> extends Comparable<Table> {

  public void setName(String tableName);

  public String getTableName();

  public void insert(E event);

  public TableIterator getIterator(long maxRowNum);

  public TableIterator getIterator(Predicate<CharSequence> columnFilter);

  public TableIterator getIterator(Predicate<CharSequence> columnFilter, Column validRows);

  public long getLatestEventTime();

  public Set<String> getColumnNames();

  public Map<String, Column> getColumns();

  int getNumOfRows();

  public ColumnFactory getColumnFactory();

  /**
   * Returns the memory used by all the backing columns of this table in bytes.
   *
   * @return
   */
  public long memSize();

  public Column getColumn(String colName);
}
