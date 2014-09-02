package io.logbase.benchmark;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import io.logbase.consumer.EventConsumer;
import io.logbase.consumer.impl.TwitterFileConsumer;
import io.logbase.event.JSONEvent;
import io.logbase.exceptions.ConsumerInitException;
import io.logbase.node.Node;
import io.logbase.node.NodeConnector;
import io.logbase.node.impl.SimpleRealtimeNodeConnector;
import io.logbase.table.TableIterator;
import io.logbase.utils.InFilter;
import io.logbase.view.View;
import org.junit.Test;

import java.net.URL;
/**
 * Created by kousik on 06/08/14.
 */

public class PerfBenchmark extends AbstractBenchmark{

  static NodeConnector nodeConnector = new SimpleRealtimeNodeConnector();
  static Node node = nodeConnector.connect();

  @BenchmarkOptions(benchmarkRounds = 10, warmupRounds = 5)
  @Test
  public void twitterFileWrite() {
    String fileName = "twitter_events_mini.dat";
    URL url = ClassLoader.getSystemResource(fileName);
    EventConsumer consumer = new TwitterFileConsumer(url.getFile());

    try {
      consumer.init(node.getWriter("Twitter", JSONEvent.class));
    } catch (ConsumerInitException e) {
      System.exit(-1);
    }
    /*
     * Consumer, reads every line from the twitter file and inserts it to the
     * table it creates.
     */
    consumer.start();
  }

  @BenchmarkOptions(benchmarkRounds = 10, warmupRounds = 5)
  @Test
  public void twitterFileRead() {
    View view = node.getReader().getViewFactory()
        .createView(new InFilter("Twitter"));
    TableIterator tableIterator = view.getIterator();
    while (tableIterator.hasNext()) {
      tableIterator.next();
    }
  }
}