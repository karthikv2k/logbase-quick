package io.logbase.node.impl.realtime.data.tables;

import io.logbase.datamodel.ColumnFactory;
import io.logbase.datamodel.Event;
import io.logbase.datamodel.Table;
import io.logbase.datamodel.TableFactory;
import io.logbase.datamodel.types.JSONEvent;

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
