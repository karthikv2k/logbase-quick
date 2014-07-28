package io.logbase.node.impl.realtime.wrapper;

import com.google.common.collect.Ordering;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import io.logbase.datamodel.ColumnFactory;
import io.logbase.datamodel.Event;
import io.logbase.datamodel.Table;
import io.logbase.datamodel.TableFactory;
import io.logbase.node.Node;

public abstract class BaseNode implements Node {

  protected SortedSetMultimap<String, Table> tables = TreeMultimap.create(Ordering.natural(), Ordering.natural());
  final TableFactory tableFactory;

  final ColumnFactory columnFactory;

  public BaseNode(TableFactory tableFactory, ColumnFactory columnFactory) {
    this.tableFactory = tableFactory;
    this.columnFactory = columnFactory;
    Ordering<Comparable> a = Ordering.natural();
  }

  @Override
  public Table createTable(String tableName, Class<? extends Event> event) {
    Table table = tableFactory.createTable(tableName, event, columnFactory);
    tables.put(tableName, table);
    return table;
  }

  @Override
  public SortedSetMultimap<String, Table> getTables() {
    return tables;
  }
}
