package io.logbase.api.ui.query;

import io.logbase.node.Node;
import io.logbase.node.NodeConnector;
import io.logbase.node.impl.SimpleRealtimeNodeConnector;

public class QueryUtils {

  private static NodeConnector nodeConnector = new SimpleRealtimeNodeConnector();
  private static Node node = nodeConnector.connect();
  private static int reqid = 0;
  public static int postQuery(String args, long from, long to, int dataset,
      String source) {
    // TODO
    reqid++;
    // Store request somewhere

    return reqid;
  }

}
