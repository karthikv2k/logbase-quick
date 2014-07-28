package io.logbase.sample;

import io.logbase.consumer.EventConsumer;
import io.logbase.consumer.impl.TwitterKafkaConsumer;
import io.logbase.datamodel.types.JSONEvent;
import io.logbase.exceptions.ConsumerInitException;
import io.logbase.node.Node;
import io.logbase.node.Reader;
import io.logbase.node.connector.NodeConnector;
import io.logbase.node.connector.impl.SimpleRealtimeNodeConnector;

import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.Configuration;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc

/**
 * A sample class to start the event consumer for trying out.
 */
public class TwitterKafkaSampleTest {

  static final Logger logger = LoggerFactory.getLogger(TwitterKafkaSampleTest.class);


  @Ignore
  public void testKafkaSample() {

    logger.info("Running a sample...");

    // Step 1: Create a local Realtime Node
    NodeConnector nodeConnector = new SimpleRealtimeNodeConnector();
    Node node = nodeConnector.connect();

    // Step 2: Configure and start a Kafka consumer
    Configuration config = new BaseConfiguration();
    config.addProperty("topic", "test");
    config.addProperty("group", "group1");
    config.addProperty("zooKeeper", "localhost:2181");
    config.addProperty("threads", new Integer(1));
    EventConsumer consumer = new TwitterKafkaConsumer(config);
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
    try {
      Thread.sleep(5000);
    } catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
    }
    /*
    logger.info("Table name in node: " + reader.getTableNames().get(0));
    logger.info("Columns in table: "
        + reader.getColumnNames(reader.getTableNames().get(0)));
    EventsResult result = (EventsResult) reader
        .doOperation(new GetEventOperation());

    logger.info("Sample run started!");
    */
    assertEquals(1, 1);
  }

  private static void assertEquals(int i, int j) {
    // TODO Auto-generated method stub

  }

}
