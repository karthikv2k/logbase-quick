package io.logbase.node.impl;

import io.logbase.column.appendonly.AppendOnlyColumnFactory;
import io.logbase.event.Event;
import io.logbase.table.Table;
import io.logbase.event.JSONEvent;
import io.logbase.node.Node;
import io.logbase.node.Reader;
import io.logbase.node.Writer;
import io.logbase.table.impl.TableFactoryV1;

public class SimpleRealtimeNode extends BaseNode implements Node {

  private Table table;

  public SimpleRealtimeNode() {
    super(new TableFactoryV1(), new AppendOnlyColumnFactory());
  }

  @Override
  public void start() {
  }

  @Override
  public Writer getWriter(String tableName, Class<? extends Event> event) {
    if (event.equals(JSONEvent.class)) {
      return new JSONWriter(this, tableName);
    }
    throw new UnsupportedOperationException();
  }

  @Override
  public Reader getReader() {
    return new SimpleReader(this, new ViewFactoryV1(this));
  }

}
