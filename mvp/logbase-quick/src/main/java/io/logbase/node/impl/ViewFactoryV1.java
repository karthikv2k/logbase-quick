package io.logbase.node.impl;

import com.google.common.collect.SortedSetMultimap;
import io.logbase.table.Table;
import io.logbase.view.View;
import io.logbase.view.ViewFactory;
import io.logbase.node.Node;
import io.logbase.view.SimpleView;
import io.logbase.utils.Filter;

import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class ViewFactoryV1 implements ViewFactory {
  Node node;
  private SortedSetMultimap<String, Table> tables;

  ViewFactoryV1(Node node) {
    this.node = node;
    tables = node.getTables();
  }

  @Override
  public View createView(Filter filter) {
    SortedSet<Table> filteredTables = new TreeSet<Table>();
    for (Map.Entry<String, Collection<Table>> entry : tables.asMap().entrySet()) {
      if (filter.accept(entry.getKey())) {
        filteredTables.addAll(entry.getValue());
      }
    }
    View view = new SimpleView(filteredTables, "TBA");
    return view;
  }

}
