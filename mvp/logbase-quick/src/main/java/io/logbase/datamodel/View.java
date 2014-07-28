package io.logbase.datamodel;

import java.util.Set;

public interface View {

  public TableIterator getIterator();

  public Set<String> getUnderlyingTableNames();

  public long getNumRows();

}
