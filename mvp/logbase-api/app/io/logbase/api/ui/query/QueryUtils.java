package io.logbase.api.ui.query;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
  private static Map<Integer, List<String>> queryResults = new HashMap<Integer, List<String>>();
  private static Map<Integer, List<String>> columnMap = new HashMap<Integer, List<String>>();

  public static int postQuery(QueryRequest queryRequest) {
    reqid++;
    // Store request somewhere
    queryRequests.put(reqid, queryRequest);
    Logger.info("No. of query requests in memory: " + queryRequests.size());
    return reqid;
  }

  public static boolean isValidRequest(int reqid) {
    return queryRequests.get(reqid) == null ? false : true;
  }

  public static List<String> getEvents(int reqid) {
    List<String> cachedEvents = queryResults.get(reqid);
    if (cachedEvents != null)
      return cachedEvents;
    else
      return fetchEvents(reqid);
  }

  public static List<String> getEventsP(int reqid, long offset, int max) {
    List<String> events = null;
    List<String> selectEvents = new ArrayList<String>();
    List<String> cachedEvents = queryResults.get(reqid);
    if (cachedEvents != null)
      events = cachedEvents;
    else
      events = fetchEvents(reqid);
    if (offset <= events.size()) {
      int start = (int) (offset - 1);
      int end = start + max - 1;
      if (end >= events.size())
        end = events.size() - 1;
      for (int i = start; i <= end; i++)
        selectEvents.add(events.get(i));
    }
    return selectEvents;
  }

  public static long getEventsCount(int reqid){
    List<String> cachedEvents = queryResults.get(reqid);
    if (cachedEvents != null)
      return cachedEvents.size();
    else
      return fetchEvents(reqid).size();
  }

  private static List<String> fetchEvents(int reqid) {
    // TODO This needs to be improved with multi table support etc.
    QueryRequest queryRequest = queryRequests.get(reqid);
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
    queryResults.put(reqid, events);
    return events;
  }

  public static List<String> getColumns(int reqid) {
    View view = node.getReader().getViewFactory()
        .createView(new InFilter("Twitter"));
    List<String> columns = new ArrayList(view.getColumnNames());
    return columns;
  }
  
  public static void createTableColumns(int reqid, List<String> columns) {
    columnMap.put(reqid, columns);
  }
  
  public static List<Map<String, Object>> getTable(int reqid) {
    List<Map<String, Object>> results = null;
    // TODO
    // Execute query with the passed columns
    QueryRequest queryRequest = queryRequests.get(reqid);
    View view = node.getReader().getViewFactory()
        .createView(new InFilter("Twitter"));
    LBSchema lbSchema = new LBSchema("TEST");
    lbSchema.addAsSmartTable("TWITTER", view);
    QueryExecutor queryExec = new QueryExecutor(lbSchema);

    String selectClause = "";
    for (String column : columnMap.get(reqid)) {
      if (selectClause.equals(""))
        selectClause = selectClause + "\"" + column + "\"";
      else
        selectClause = selectClause + ", \"" + column + "\"";
    }
    Logger.info("Select clause: " + selectClause);
    String sql = "SELECT " + selectClause
        + " from \"TEST\".\"TWITTER\" where \"RawEvent.String\" LIKE '"
        + queryRequest.getArgs() + "'";
    try {
      ResultSet resultSet = queryExec.execute(sql);
      results = getEntitiesFromResultSet(resultSet);
    } catch (Exception e) {
      Logger.error("Error while executing optiq query: " + sql);
    }
    return results;
  }

  private static List<Map<String, Object>> getEntitiesFromResultSet(
      ResultSet resultSet) throws SQLException {
    List<Map<String, Object>> entities = new ArrayList<Map<String, Object>>();
    while (resultSet.next()) {
      entities.add(getEntityFromResultSet(resultSet));
    }
    return entities;
  }

  private static Map<String, Object> getEntityFromResultSet(ResultSet resultSet)
      throws SQLException {
    ResultSetMetaData metaData = resultSet.getMetaData();
    int columnCount = metaData.getColumnCount();
    Map<String, Object> resultsMap = new HashMap<>();
    for (int i = 1; i <= columnCount; ++i) {
      String columnName = metaData.getColumnName(i).toLowerCase();
      Object object = resultSet.getObject(i);
      resultsMap.put(columnName, object);
    }
    return resultsMap;
  }

}
