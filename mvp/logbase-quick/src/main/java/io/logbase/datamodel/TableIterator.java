package io.logbase.datamodel;

public interface TableIterator extends ColumnIterator<Object[]> {
  public String[] getColumnNames();

  public Column[] getColumns();
}
