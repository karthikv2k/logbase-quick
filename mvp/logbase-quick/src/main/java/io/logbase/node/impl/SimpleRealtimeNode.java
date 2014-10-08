package io.logbase.node.impl;

import io.logbase.column.ColumnFactory;
import io.logbase.event.Event;
import io.logbase.event.JSONEvent;
import io.logbase.node.Node;
import io.logbase.node.Reader;
import io.logbase.node.Writer;
import io.logbase.table.impl.TableFactoryV1;
import io.logbase.view.ViewFactoryV1;

public class SimpleRealtimeNode extends BaseNode implements Node {

  private static SimpleRealtimeNode node;

  public static SimpleRealtimeNode getInstance() {
    if (node == null) {
      synchronized (SimpleRealtimeNode.class) {
        if (node == null)
          node = new SimpleRealtimeNode();
      }
    }
    return node;
  }

  private SimpleRealtimeNode() {
    super(new TableFactoryV1(), ColumnFactory.INSTANCE);
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
