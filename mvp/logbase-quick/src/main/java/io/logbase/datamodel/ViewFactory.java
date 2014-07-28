package io.logbase.datamodel;

import io.logbase.utils.Filter;

public interface ViewFactory {

  public View createView(Filter filter);

}
