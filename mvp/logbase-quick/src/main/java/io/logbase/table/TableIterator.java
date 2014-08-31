package io.logbase.table;

import java.util.Iterator;

public interface TableIterator extends Iterator<Object[]> {

  public String[] getColumnNames();

}
