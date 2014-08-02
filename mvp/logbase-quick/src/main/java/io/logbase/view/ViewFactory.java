package io.logbase.view;

import io.logbase.utils.Filter;

public interface ViewFactory {

  public View createView(Filter filter);

}
