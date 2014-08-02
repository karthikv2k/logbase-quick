package io.logbase.table;

import io.logbase.column.ColumnFactory;
import io.logbase.event.Event;

public interface TableFactory {

  public Table createTable(String name, Class<? extends Event> event, ColumnFactory columnFactory);

}
