package io.logbase.view;

import io.logbase.node.Node;
import io.logbase.table.Table;
import io.logbase.utils.Filter;

import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class ViewFactoryV1 implements ViewFactory {
  Node node;

  public ViewFactoryV1(Node node) {
    this.node = node;
  }

  @Override
  public View createView(Filter filter) {
    SortedSet<Table> filteredTables = new TreeSet<Table>();
    for (Map.Entry<String, Collection<Table>> entry : node.getTables().asMap().entrySet()) {
      if (filter.accept(entry.getKey())) {
        filteredTables.addAll(entry.getValue());
      }
    }
    View view = new SimpleView(filteredTables, "TBA");
    return view;
  }

}
