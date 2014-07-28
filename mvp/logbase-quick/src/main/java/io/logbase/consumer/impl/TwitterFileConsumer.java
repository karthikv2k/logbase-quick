package io.logbase.consumer.impl;

import io.logbase.consumer.EventConsumer;
import io.logbase.datamodel.types.JSONEvent;
import io.logbase.exceptions.ConsumerInitException;
import io.logbase.node.Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Consumes twitter stream events stored in a file separated by newline.
 *
 * @author Abishek Baskaran
 */
public class TwitterFileConsumer implements EventConsumer {

  final Logger logger = LoggerFactory.getLogger(TwitterFileConsumer.class);
  private Writer writer;
  private String filePath;
  private BufferedReader br;
  private String line;

  public TwitterFileConsumer(String filePath) {
    this.filePath = filePath;
  }

  @Override
  public void init(Writer writer) throws ConsumerInitException {
    this.writer = writer;
    try {
      br = new BufferedReader(new FileReader(filePath));
    } catch (FileNotFoundException e) {
      throw new ConsumerInitException(
        "Could not create Consumer, file load error.");
    }

  }

  @Override
  public void start() {
    JSONEvent jsonEvent = new JSONEvent(0, "Twitter");

    try {
      while ((line = br.readLine()) != null) {
        // Create event from line and write to writer.
        jsonEvent.setData(line);
        jsonEvent.setTimestamp(System.currentTimeMillis());
        writer.write(jsonEvent);
      }
      br.close();
    } catch (IOException e) {
      logger.error("IOException while reading file.");
    }
  }

}
