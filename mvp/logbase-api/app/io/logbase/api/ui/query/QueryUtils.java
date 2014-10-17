package io.logbase.api.ui.query;

import java.util.HashMap;
import java.util.Map;

import play.Logger;
import io.logbase.node.Node;
import io.logbase.node.NodeConnector;
import io.logbase.node.impl.SimpleRealtimeNodeConnector;

public class QueryUtils {

  private static NodeConnector nodeConnector = new SimpleRealtimeNodeConnector();
  private static Node node = nodeConnector.connect();
  private static int reqid = 0;
  private static Map<Integer, QueryRequest> queryRequests = new HashMap<Integer, QueryRequest>();

  public static int postQuery(QueryRequest queryRequest) {
    // TODO
    reqid++;
    // Store request somewhere
    queryRequests.put(reqid, queryRequest);
    Logger.info("No. of query requests in memory: " + queryRequests.size());
    return reqid;
  }

}
