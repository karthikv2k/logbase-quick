package io.logbase.sample;

import static org.junit.Assert.*;

import com.google.common.base.Predicates;
import com.google.common.base.Predicate;

import io.logbase.column.Column;
import io.logbase.consumer.EventConsumer;
import io.logbase.consumer.impl.TwitterFileConsumer;
import io.logbase.table.Table;
import io.logbase.table.TableIterator;
import io.logbase.view.View;
import io.logbase.event.JSONEvent;
import io.logbase.exceptions.ConsumerInitException;
import io.logbase.node.Node;
import io.logbase.node.Reader;
import io.logbase.node.NodeConnector;
import io.logbase.node.impl.SimpleRealtimeNodeConnector;
import io.logbase.querying.optiq.LBSchema;
import io.logbase.querying.optiq.QueryExecutor;
import io.logbase.utils.InFilter;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Loads and queries a twitter stream file.
 *
 * @author Abishek Baskaran
 */
public class TwitterFileSampleTest {

  private Node node;
  private View view;

  static final Logger logger = LoggerFactory
      .getLogger(TwitterFileSampleTest.class);

  @Before
  public void init() {
    // Step 1: Create a local Realtime Node
    NodeConnector nodeConnector = new SimpleRealtimeNodeConnector();
    node = nodeConnector.connect();

    // Step 2: Configure and start a File consumer
    String fileName = "twitter_events_mini.dat";
    URL url = ClassLoader.getSystemResource(fileName);
    EventConsumer consumer = new TwitterFileConsumer(url.getFile());

    try {
      consumer.init(node.getWriter("Twitter", JSONEvent.class));
    } catch (ConsumerInitException e) {
      logger.error("Consumer init error: " + e.getMessage());
      System.exit(-1);
    }
    consumer.start();

    // Step 3: Get a reader to fire queries
    Reader reader = node.getReader();

    // Step 4: Get a view
    logger.info("Table names in the node: " + reader.getTableNames());
    view = reader.getViewFactory().createView(new InFilter("Twitter"));
  }

  /**
   * Test case without query push down
   */
  @Test
  public void testTwitterFileSample() {

    logger.info("Running a Twitter File sample...");

    List<CharSequence> columns = new ArrayList<CharSequence>();
    columns.add("text.String");
    columns.add("created_at.String");
    columns.add("id.Double");
    Predicate<CharSequence> columnFilter1 = Predicates.in(columns);
    Predicate<CharSequence> columnFilter2 = Predicates
        .containsPattern("^retweeted_status1.*(String|Double)");
    Predicate<CharSequence> columnFilter = Predicates.or(columnFilter1,
        columnFilter2);
    TableIterator tableIterator = view.getIterator(columnFilter);

    logger.debug("Columns in table: "
      + Arrays.toString(tableIterator.getColumnNames()));
    // logger.info("Reading all rows:");
    // int count = 0;
    // while (tableIterator.hasNext()) {
    // tableIterator.next();
    // count++;
    // }
    // logger.info("Sample run done, No. of rows in iterator:" + count);
    // assertEquals(count, 2000);

    // Testing Optiq without Push Down
    LBSchema lbSchema = new LBSchema("TEST");
    lbSchema.addAsTable("TWITTER", view);
    QueryExecutor queryExec = new QueryExecutor(lbSchema);
    String sql = "SELECT \"text.String\", \"source.String\" "
        + " from \"TEST\".\"TWITTER\" where \"id.Double\" = 461506965680951296";
    int resultCount = 0;
    try {
    ResultSet results = queryExec.execute(sql);
      while (results.next()) {
        resultCount++;
        logger.debug("Text is: " + results.getString("text.String")
            + " Source is: " + results.getString("source.String"));
      }
    } catch (SQLException e) {
      logger.error("Error while executing optiq query: " + sql);
    }
    logger.info("Result count: " + resultCount);
    assertEquals(resultCount, 1);
  }

  /**
   * Test case for query push down.
   */
  // Ignoring as filter push not yet implemented.
  @Test
  public void testSmartTwitterFileSample() {

    logger.info("Running a Smart Twitter File sample...");

    // Testing Optiq with Push Down
    LBSchema lbSchema = new LBSchema("TEST");
    lbSchema.addAsSmartTable("TWITTER", view);
    QueryExecutor queryExec = new QueryExecutor(lbSchema);
    String sql = "SELECT \"text.String\", \"source.String\" "
        + " from \"TEST\".\"TWITTER\" where (\"id_str.String\" = '461506965680951296') and (\"id.Double\" = 461506965680951296 or \"text.String\" = '@smiley_bieber15 nxuq')";
    int resultCount = 0;
    try {
      ResultSet results = queryExec.execute(sql);
      while (results.next()) {
        resultCount++;
        // logger.debug("Text is: " + results.getString("text.String")
        // + " Source is: " + results.getString("source.String"));
      }
    } catch (SQLException e) {
      logger.error("Error while executing optiq query: " + sql);
    }
    logger.info("Result count: " + resultCount);
    assertEquals(resultCount, 1);
  }
}
