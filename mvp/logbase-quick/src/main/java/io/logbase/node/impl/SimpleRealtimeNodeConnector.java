package io.logbase.node.impl;

import io.logbase.node.Node;
import io.logbase.node.NodeConnector;

public class SimpleRealtimeNodeConnector implements NodeConnector {

  private Node node;

  public SimpleRealtimeNodeConnector() {
    node = new SimpleRealtimeNode();
  }

  @Override
  public Node connect() {
    return node;
  }

}
