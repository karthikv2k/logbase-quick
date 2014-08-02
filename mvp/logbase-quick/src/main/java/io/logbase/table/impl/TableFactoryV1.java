package io.logbase.table.impl;

import io.logbase.column.ColumnFactory;
import io.logbase.event.Event;
import io.logbase.event.JSONEvent;
import io.logbase.table.Table;
import io.logbase.table.TableFactory;

public class TableFactoryV1 implements TableFactory {

  @Override
  public Table createTable(String name, Class<? extends Event> event, ColumnFactory columnFactory) {
    if (event.equals(JSONEvent.class)) {
      Table<JSONEvent> table = new RCFJSONTable(columnFactory);
      table.setName(name);
      return table;
    } else {
      throw new UnsupportedOperationException("Table type " + event + " is not supported");
    }
  }

}
