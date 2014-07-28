package io.logbase.node;

import com.google.common.collect.SortedSetMultimap;
import io.logbase.datamodel.Event;
import io.logbase.datamodel.Table;

public interface Node {

  public void start();

  public Writer getWriter(String tableName, Class<? extends Event> eventType);

  public Reader getReader();

  public Table createTable(String tableName, Class<? extends Event> eventType);

  public SortedSetMultimap<String, Table> getTables();
}
