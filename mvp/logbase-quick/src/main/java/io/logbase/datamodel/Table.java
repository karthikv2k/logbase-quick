package io.logbase.datamodel;

import io.logbase.datamodel.types.JSONEvent;

public interface Table<E extends Event> extends Comparable<Table> {

  public void setName(String tableName);

  public String getTableName();

  public int getNumOfRows();

  public void insert(E event);

  public JSONEvent getRow(long rowNum);

  public TableIterator getIterator(long maxRowNum);

  public TableIterator getIterator();

  public long getLatestEventTime();

}
