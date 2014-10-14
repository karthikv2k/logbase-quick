package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import io.logbase.column.Column;
import io.logbase.exceptions.InvalidOperandException;
import io.logbase.functions.Function;
import io.logbase.functions.FunctionFactory;
import io.logbase.functions.FunctionFactory.FunctionOperator;
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
    // TODO form the query from args.
    // Column rawEventColumn = view.getColumn("Raw Event");
    // FunctionFactory ff = new FunctionFactory();
    // Function searchFunction = ff.createFunction(FunctionOperator.SEARCH,
    // new Object[] { rawEventColumn,
    // args });
    // Column filteredRows = null;
    // try {
    // filteredRows = (Column) searchFunction.execute();
    // // No method to get raw events by passing
    // } catch (InvalidOperandException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }

    // String sql = "SELECT \"text.String\", \"source.String\" "
    // +
    // " from \"TEST\".\"TWITTER\" where (\"id_str.String\" = '461506965680951296') and (\"id.Double\" = 461506965680951296 or \"text.String\" = '@smiley_bieber15 nxuq')";
    String sql = "SELECT \"Raw Event\""
        + " from \"TEST\".\"TWITTER\" where \"Raw Event\" LIKE 'beiber'";
    int resultCount = 0;
    try {
      ResultSet results = queryExec.execute(sql);
      while (results.next()) {
        resultCount++;
        // logger.debug("Text is: " + results.getString("text.String")
        // + " Source is: " + results.getString("source.String"));
      }
    } catch (SQLException e) {
      Logger.error("Error while executing optiq query: " + sql);
    }
    Logger.info("Result count: " + resultCount);

    return ok("You said: " + args);
  }

}
