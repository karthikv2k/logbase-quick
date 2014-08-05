package io.logbase.table;

import com.google.common.base.Predicate;
import io.logbase.event.Event;
import io.logbase.event.JSONEvent;

import java.util.Set;

public interface Table<E extends Event> extends Comparable<Table> {

  public void setName(String tableName);

  public String getTableName();

  public void insert(E event);

  public TableIterator getIterator(long maxRowNum);

  public TableIterator getIterator();

  public TableIterator getIterator(Predicate<CharSequence> filter);

  public long getLatestEventTime();

  public Set<String> getColumnNames();

  int getNumOfRows();
}
