package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import io.logbase.node.Node;
import io.logbase.node.NodeConnector;
import io.logbase.node.impl.SimpleRealtimeNodeConnector;
import io.logbase.querying.optiq.LBSchema;
import io.logbase.querying.optiq.QueryExecutor;
import io.logbase.utils.InFilter;
import io.logbase.view.View;
import play.*;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

  public static Result search(String args) {
    NodeConnector nodeConnector = new SimpleRealtimeNodeConnector();
    Node node = nodeConnector.connect();
    //TODO change the filter condition.
    View view = node.getReader().getViewFactory()
        .createView(new InFilter("Twitter"));
    LBSchema lbSchema = new LBSchema("TEST");
    lbSchema.addAsSmartTable("TWITTER", view);
    QueryExecutor queryExec = new QueryExecutor(lbSchema);
    String sql = "SELECT \"RawEvent.String\""
        + " from \"TEST\".\"TWITTER\" where \"RawEvent.String\" LIKE '" + args
        + "'";
    int resultCount = 0;
    ObjectMapper mapper = new ObjectMapper();
    ArrayNode output = null;
    JsonNode jsonEvent = null;
    List<JsonNode> eventsArray = new ArrayList();
    try {
      ResultSet results = queryExec.execute(sql);
      while (results.next()) {
        resultCount++;
        String event = results.getString("RawEvent.String");
        Logger.debug("Got Event: " + event);
        jsonEvent = mapper.readTree(event);
        eventsArray.add(jsonEvent);
      }
    } catch (Exception e) {
      Logger.error("Error while executing optiq query: " + sql);
    }
    Logger.info("Result count: " + resultCount);

    output = mapper.valueToTree(eventsArray);
    return ok(output);
  }

}
