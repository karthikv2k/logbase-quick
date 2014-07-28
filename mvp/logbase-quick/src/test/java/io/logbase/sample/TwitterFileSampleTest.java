package io.logbase.sample;

import static org.junit.Assert.*;
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
import io.logbase.utils.InFilter;
import io.logbase.utils.Utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Arrays;

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
    TableIterator tableIterator = view.getIterator();
    logger.info("Columns in table: " + Arrays.toString(tableIterator.getColumnNames()));
    logger.info("Reading all rows:");
    int count = 0;
    while (tableIterator.hasNext()) {
      // System.out.println(Utils.toString(tableIterator.next()));
      tableIterator.next();
      count++;
    }
    logger.info("Sample run done, No. of rows in iterator:" + count);
    assertEquals(count, 2000);

  }

}
