package io.logbase.node;

import com.google.common.collect.SortedSetMultimap;
import io.logbase.event.Event;
import io.logbase.table.Table;

public interface Node {

  public Writer getWriter(String tableName, Class<? extends Event> eventType);

  public Reader getReader();

  public Table createTable(String tableName, Class<? extends Event> eventType);

  public SortedSetMultimap<String, Table> getTables();
}
