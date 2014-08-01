package io.logbase.consumer;

import io.logbase.exceptions.ConsumerInitException;
import io.logbase.node.Writer;

/**
 * The Interface EventConsumer. Events can be consumed from different sources
 * like Kafka, Files etc.
 */
public interface EventConsumer {

  /**
   * Inits the event consumer and establishes a connections to the event source.
   *
   * @throws ConsumerInitException if unable to initialize the consumer.
   */
  public void init(Writer writer) throws ConsumerInitException;

  /**
   * Start consuming the events.
   */
  public void start();

}
