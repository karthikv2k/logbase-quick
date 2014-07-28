package io.logbase.node.connector.impl;

import io.logbase.node.Node;
import io.logbase.node.connector.NodeConnector;
import io.logbase.node.impl.realtime.wrapper.SimpleRealtimeNode;

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
