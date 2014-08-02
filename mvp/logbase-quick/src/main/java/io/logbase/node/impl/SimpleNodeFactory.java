package io.logbase.node.impl;

import io.logbase.node.Node;
import io.logbase.node.NodeFactory;

public class SimpleNodeFactory implements NodeFactory {

  @Override
  public Node createNode() {
    return new SimpleRealtimeNode();
  }

}
