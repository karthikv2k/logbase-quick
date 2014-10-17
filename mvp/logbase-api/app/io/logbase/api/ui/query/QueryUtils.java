package io.logbase.api.ui.query;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import play.Logger;
import io.logbase.node.Node;
import io.logbase.node.NodeConnector;
import io.logbase.node.impl.SimpleRealtimeNodeConnector;
import io.logbase.querying.optiq.LBSchema;
import io.logbase.querying.optiq.QueryExecutor;
import io.logbase.utils.InFilter;
import io.logbase.view.View;

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

  public static QueryRequest getQueryRequest(int reqid) {
    return queryRequests.get(reqid);
  }

  public static List<String> getEvents(QueryRequest queryRequest) {
    List<String> events = new ArrayList<String>();
    View view = node.getReader().getViewFactory()
        .createView(new InFilter("Twitter"));
    LBSchema lbSchema = new LBSchema("TEST");
    lbSchema.addAsSmartTable("TWITTER", view);
    QueryExecutor queryExec = new QueryExecutor(lbSchema);
    String sql = "SELECT \"RawEvent.String\""
        + " from \"TEST\".\"TWITTER\" where \"RawEvent.String\" LIKE '"
        + queryRequest.getArgs() + "'";
    int resultCount = 0;
    try {
      ResultSet results = queryExec.execute(sql);
      while (results.next()) {
        resultCount++;
        String event = results.getString("RawEvent.String");
        events.add(event);
      }
    } catch (Exception e) {
      Logger.error("Error while executing optiq query: " + sql);
    }
    Logger.info("Result count: " + resultCount);
    return events;
  }

}
