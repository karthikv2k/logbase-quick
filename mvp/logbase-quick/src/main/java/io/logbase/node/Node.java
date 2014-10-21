package io.logbase.node;

import com.google.common.collect.SortedSetMultimap;
import io.logbase.event.Event;
import io.logbase.table.Table;

public interface Node {

  public Writer getWriter(String tableName, Class<? extends Event> eventType);

  public Reader getReader();

  public Table createTable(String tableName, Class<? extends Event> eventType);

  /**
   * The thread which converts an Append only table to Read only table, updates
   * the node with the new table name.
   *
   * @param AppendOnlyTable
   * @param ReadOnlyTable
   */
  public void updateTables(Table AppendOnlyTable, Table ReadOnlyTable);

  public SortedSetMultimap<String, Table> getTables();
}
