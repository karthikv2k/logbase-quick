package io.logbase.view;

import com.google.common.base.Predicate;
import io.logbase.table.TableIterator;

import java.util.Set;

public interface View {

  public TableIterator getIterator();

  public TableIterator getIterator(Predicate<CharSequence> filter);

  public Set<String> getUnderlyingTableNames();

  public long getNumRows();

  public Set<String> getColumnNames();

}
