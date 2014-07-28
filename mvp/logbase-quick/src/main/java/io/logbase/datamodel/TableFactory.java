package io.logbase.datamodel;

public interface TableFactory {

  public Table createTable(String name, Class<? extends Event> event, ColumnFactory columnFactory);

}
