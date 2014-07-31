package io.logbase.datamodel;

import com.google.common.base.Predicate;
import io.logbase.datamodel.types.JSONEvent;

import java.util.Set;

public interface Table<E extends Event> extends Comparable<Table> {

  public void setName(String tableName);

  public String getTableName();

  public int getNumOfRows();

  public void insert(E event);

  public JSONEvent getRow(long rowNum);

  public TableIterator getIterator(long maxRowNum);

  public TableIterator getIterator();

  public TableIterator getIterator(Predicate<CharSequence> filter);

  public long getLatestEventTime();

  public Set<String> getColumnNames();

}
