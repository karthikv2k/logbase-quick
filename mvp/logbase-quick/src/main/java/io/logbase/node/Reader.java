package io.logbase.node;

import io.logbase.datamodel.ViewFactory;

import java.util.Set;

public interface Reader {

  public Set<String> getTableNames();

  public ViewFactory getViewFactory();

}
