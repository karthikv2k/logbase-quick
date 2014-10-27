package io.logbase.api.ui.upload;

import play.Logger;
import io.logbase.consumer.EventConsumer;
import io.logbase.consumer.impl.TwitterFileConsumer;
import io.logbase.event.JSONEvent;
import io.logbase.exceptions.ConsumerInitException;
import io.logbase.node.Node;
import io.logbase.node.NodeConnector;
import io.logbase.node.impl.SimpleRealtimeNodeConnector;

public class UploadUtils {

	private static NodeConnector nodeConnector = new SimpleRealtimeNodeConnector();
	private static Node node = nodeConnector.connect();

	public static void ingestFile(String filePath, String alias) {
		// TODO
		// Create a generic JSON file consumer
		EventConsumer consumer = new TwitterFileConsumer(filePath);
		try {
			consumer.init(node.getWriter(alias, JSONEvent.class));
		} catch (ConsumerInitException e) {
			Logger.error("Consumer init error: " + e.getMessage());
		}
		consumer.start();
	}
}
