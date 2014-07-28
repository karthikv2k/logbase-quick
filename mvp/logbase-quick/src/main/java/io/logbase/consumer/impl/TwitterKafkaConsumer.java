package io.logbase.consumer.impl;

import io.logbase.consumer.EventConsumer;
import io.logbase.exceptions.ConsumerInitException;
import io.logbase.node.Writer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The Class KafkaEventConsumer. This class initializes the kafka stream and the
 * consumer threads and starts consuming kafka events.
 */
public class TwitterKafkaConsumer implements EventConsumer {

  final Logger logger = LoggerFactory.getLogger(TwitterKafkaConsumer.class);
  private String topic;
  private String zooKeeper;
  private String group;
  private Integer threads;
  private ConsumerConnector consumer;
  private ExecutorService executor;
  private Writer writer;


  /**
   * Instantiates a new kafka event consumer.
   *
   * @param config the config for connecting to kafka. Pass the topic, zooKeeper url,
   *               consumer group name and the number of threads.
   */
  public TwitterKafkaConsumer(Configuration config) {
    topic = config.getString("topic");
    zooKeeper = config.getString("zooKeeper");
    group = config.getString("group");
    threads = config.getInt("threads");
    logger.info("Creating a Kafka Event Consumer for: " + topic + "|"
      + zooKeeper + "|" + group + "|" + threads);
  }


  /*
   * (non-Javadoc)
   * 
   * @see io.thedal.event.consumer.EventConsumer#init()
   */
  @Override
  public void init(Writer writer) throws ConsumerInitException {
    logger.info("Initializing Kafka Event Consumer");

    this.writer = writer;

    if ((topic == null) || (zooKeeper == null) || (group == null)
      || (threads == null))
      throw new ConsumerInitException("Kafka properties empty");

    Properties props = new Properties();
    props.put("zookeeper.connect", zooKeeper);
    props.put("group.id", group);
    props.put("zookeeper.session.timeout.ms", "400");
    props.put("zookeeper.sync.time.ms", "200");
    props.put("auto.commit.interval.ms", "1000");
    consumer = kafka.consumer.Consumer
      .createJavaConsumerConnector(new ConsumerConfig(props));

    if (consumer == null)
      throw new ConsumerInitException("Could not create Consumer");
  }

  /*
   * (non-Javadoc)
   * 
   * @see io.thedal.event.consumer.EventConsumer#start()
   */
  @Override
  public void start() {
    logger.info("Starting Kafka Event Consumer");

    Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
    topicCountMap.put(topic, threads);
    Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer
      .createMessageStreams(topicCountMap);
    List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);

    // now launch all the threads
    executor = Executors.newFixedThreadPool(threads);

    // now create an object to consume the messages
    int threadNumber = 0;
    for (final KafkaStream stream : streams) {
      executor.submit(new TwitterKafkaConsumerThread(stream, threadNumber, writer));
      logger.info("Starting Kafka Event Consumer Thread: " + threadNumber);
      threadNumber++;
    }

  }

}
