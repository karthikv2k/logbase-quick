package io.logbase.view;

import com.google.common.base.Predicate;

import io.logbase.column.Column;

import io.logbase.querying.optiq.Expression;

import io.logbase.table.TableIterator;

import java.util.Set;

public interface View {

  public TableIterator getIterator();

  public TableIterator getIterator(Predicate<CharSequence> filter);

  public TableIterator getIterator(Predicate<CharSequence> filter, Expression expression);

  public Set<String> getUnderlyingTableNames();

  public long getNumRows();

  public Set<String> getColumnNames();

  // This is a hack, we have to revisit views all together
  public Column getColumn(String columnName);

}
