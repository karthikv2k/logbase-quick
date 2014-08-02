package io.logbase.node.impl;

import io.logbase.view.ViewFactory;
import io.logbase.node.Node;
import io.logbase.node.Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class SimpleReader implements Reader {

  final Logger logger = LoggerFactory.getLogger(SimpleReader.class);

  private final ViewFactory viewFactory;
  private final Node node;

  public SimpleReader(Node node, ViewFactory viewFactory) {
    this.node = node;
    this.viewFactory = viewFactory;
  }

  @Override
  public Set<String> getTableNames() {
    return node.getTables().keySet();
  }

  @Override
  public ViewFactory getViewFactory() {
    return viewFactory;
  }

}
