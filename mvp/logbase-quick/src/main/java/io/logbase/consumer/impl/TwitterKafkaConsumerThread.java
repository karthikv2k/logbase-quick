package io.logbase.consumer.impl;

import io.logbase.event.JSONEvent;
import io.logbase.node.Writer;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class KafkaTwitterConsumerThread. This class contains the logic on what
 * needs to be done when an event is consumed.
 *
 * @author Abishek Baskaran
 * @version 0.1
 */
public class TwitterKafkaConsumerThread implements Runnable {


  final Logger logger = LoggerFactory
    .getLogger(TwitterKafkaConsumerThread.class);
  private KafkaStream m_stream;
  private int m_threadNumber;
  private Writer writer;

  /**
   * Instantiates a new kafka consumer thread.
   *
   * @param a_stream       the kafka stream the consumer connects to
   * @param a_threadNumber the thread number
   */
  public TwitterKafkaConsumerThread(KafkaStream a_stream, int a_threadNumber,
                                    Writer writer) {
    m_threadNumber = a_threadNumber;
    m_stream = a_stream;
    this.writer = writer;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Runnable#run()
   */
  public void run() {
    ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
    JSONEvent jsonEvent = new JSONEvent(0, "Twitter");

    while (it.hasNext()) {
      String message = new String(it.next().message());
      logger.info("Kafka Consumer Thread " + m_threadNumber
        + " received message: " + message);

      // Create event from Kafka and write to writer.
      jsonEvent.setData(message);
      jsonEvent.setTimestamp(System.currentTimeMillis());
      writer.write(jsonEvent);
    }

    logger.info("Shutting down Thread: " + m_threadNumber);
  }

}
