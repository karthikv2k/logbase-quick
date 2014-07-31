package io.logbase.sample;

import static org.junit.Assert.*;

import com.google.common.base.Predicates;
import com.google.common.base.Predicate;
import io.logbase.consumer.EventConsumer;
import io.logbase.consumer.impl.TwitterFileConsumer;
import io.logbase.datamodel.TableIterator;
import io.logbase.datamodel.View;
import io.logbase.datamodel.types.JSONEvent;
import io.logbase.exceptions.ConsumerInitException;
import io.logbase.node.Node;
import io.logbase.node.Reader;
import io.logbase.node.connector.NodeConnector;
import io.logbase.node.connector.impl.SimpleRealtimeNodeConnector;
import io.logbase.querying.optiq.LBSchema;
import io.logbase.querying.optiq.QueryExecutor;
import io.logbase.utils.InFilter;
import io.logbase.utils.Utils;

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

  static final Logger logger = LoggerFactory
      .getLogger(TwitterFileSampleTest.class);


  @Test
  public void testTwitterFileSample() {

    logger.info("Running a Twitter File sample...");

    // Step 1: Create a local Realtime Node
    NodeConnector nodeConnector = new SimpleRealtimeNodeConnector();
    Node node = nodeConnector.connect();

    // Step 2: Configure and start a File consumer
    String fileName = "twitter_events_mini.dat"; //"test_input1.json"
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

    // Step 4: Get console input for sample queries.
    // TODO

    // For testing
    logger.info("Table names in the node: " + reader.getTableNames());
    View view = reader.getViewFactory().createView(new InFilter("Twitter"));
    List<CharSequence> columns = new ArrayList<CharSequence>();
    columns.add("text.StringType");
    columns.add("created_at.StringType");
    Predicate<CharSequence> columnFilter1 = Predicates.in(columns);
    Predicate<CharSequence> columnFilter2 = Predicates.containsPattern("^retweeted_status.*(String|Double)Type");
    Predicate<CharSequence> columnFilter = Predicates.or(columnFilter1, columnFilter2);
    TableIterator tableIterator = view.getIterator(columnFilter);
    logger.error("Columns in table: " + Arrays.toString(tableIterator.getColumnNames()));
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
    String sql = "SELECT * "
        + " from \"TEST\".\"TWITTER\"";
    int resultCount = 0;
    try {
    ResultSet results = queryExec.execute(sql);
      while (results.next()) {
        resultCount++;
        // logger.info("Text is: " + results.getString("textStringType")
        // + " Source is: " + results.getString("sourceStringType"));
      }
    } catch (SQLException e) {
      logger.error("Error while executing optiq query: " + sql);
    }
    logger.error("Result count: " + resultCount);
    assertEquals(resultCount, 2000);
  }

}
