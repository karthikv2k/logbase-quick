package io.logbase.datamodel;

import java.util.Comparator;

public class TableComparator implements Comparator<Table> {

  @Override
  public int compare(Table table, Table table2) {
    return table.compareTo(table2);
  }

}
