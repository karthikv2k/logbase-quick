package io.logbase.table;

import io.logbase.node.Node;
import io.logbase.table.impl.ReadOnlyTable;

/**
 * Created by Kousik on 21/10/14.
 *
 * Thread to convert a given Append only table to Read only table.
 * After conversion, updates the node with the new Read only table.
 */

public class TableConverter implements Runnable {
  private Thread thread = null;
  private Node node;
  private Table readOnlyTable; // Read only table
  private Table appendOnlyTable; // Append only table

  public TableConverter(Table table, Node node) {
    this.node = node;
    this.appendOnlyTable = table;
  }

  @Override
  public void run() {
    readOnlyTable = new ReadOnlyTable(this.appendOnlyTable);
    // TODO - acquire a lock before updating tables in the node
    node.updateTables(this.appendOnlyTable, this.readOnlyTable);
  }

  public void start() {
    if (thread == null) {
      thread = new Thread(this, "TableConverter");
      thread.start();
    }
  }
}
