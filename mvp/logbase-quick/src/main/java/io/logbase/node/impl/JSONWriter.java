package io.logbase.node.impl;

import io.logbase.event.JSONEvent;
import io.logbase.node.Node;
import io.logbase.node.Writer;
import io.logbase.table.Table;
import io.logbase.table.TableConverter;
import io.logbase.utils.GlobalConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONWriter implements Writer<JSONEvent> {

  final Logger logger = LoggerFactory.getLogger(JSONWriter.class);

  private Table<JSONEvent> table;
  private String tableName;
  private Node node;

  public JSONWriter(Node node, String tableName) {
    this.node = node;
    this.tableName = tableName;
    table = node.createTable(tableName, JSONEvent.class);
  }

  @Override
  public void write(JSONEvent event) {
    /*
     * table.memSize is a costly operation.
     * Do memory lookup once every "MAX_TABLE_ROW_COUNT_CHECK" times.
     */
    if ((table.getNumOfRows() % GlobalConfig.MAX_TABLE_ROW_COUNT_CHECK) == 0) {
      if (table.memSize() >= GlobalConfig.MAX_TABLE_SIZE_IN_BYTES) {
        TableConverter tableConverter = new TableConverter(table, node);
        tableConverter.start();

        // Create a new Append only table and start inserting the events
        table = node.createTable(this.tableName, JSONEvent.class);
      }
    }
    table.insert(event);
  }

}
