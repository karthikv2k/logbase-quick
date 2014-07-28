package io.logbase.node.impl.realtime.wrapper;

import io.logbase.datamodel.Event;
import io.logbase.datamodel.Table;
import io.logbase.datamodel.types.JSONEvent;
import io.logbase.node.Node;
import io.logbase.node.Reader;
import io.logbase.node.Writer;
import io.logbase.node.impl.realtime.data.columns.ListBackedColumnFactory;
import io.logbase.node.impl.realtime.data.tables.TableFactoryV1;

public class SimpleRealtimeNode extends BaseNode implements Node {

  private Table table;

  public SimpleRealtimeNode() {
    super(new TableFactoryV1(), new ListBackedColumnFactory());
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
