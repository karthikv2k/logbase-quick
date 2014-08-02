package io.logbase.node.impl;


import io.logbase.table.Table;
import io.logbase.event.JSONEvent;
import io.logbase.node.Node;
import io.logbase.node.Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONWriter implements Writer<JSONEvent> {

  final Logger logger = LoggerFactory.getLogger(JSONWriter.class);

  private Table<JSONEvent> table;
  private Node node;

  public JSONWriter(Node node, String tableName) {
    this.node = node;
    table = node.createTable(tableName, JSONEvent.class);
  }

  @Override
  public void write(JSONEvent event) {
    table.insert(event);
  }

}
