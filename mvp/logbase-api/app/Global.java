import java.net.URL;

import io.logbase.consumer.EventConsumer;
import io.logbase.consumer.impl.TwitterFileConsumer;
import io.logbase.event.JSONEvent;
import io.logbase.exceptions.ConsumerInitException;
import io.logbase.node.Node;
import io.logbase.node.NodeConnector;
import io.logbase.node.impl.SimpleRealtimeNodeConnector;
import play.*;
import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

public class Global extends GlobalSettings {

  public void onStart(Application app) {

    Logger.info("Application is starting...");
    // Step 1: Create a local Realtime Node
    NodeConnector nodeConnector = new SimpleRealtimeNodeConnector();
    Node node = nodeConnector.connect();

    // TODO This should be removed and be a load file API
    // Step 2: Configure and start a File consumer
    String fileName = "twitter_events_mini.dat";
    // URL url = ClassLoader.getSystemResource(fileName);
    URL url = this.getClass().getResource(fileName);
    Logger.info("File URL: " + url.getPath());
    EventConsumer consumer = new TwitterFileConsumer(url.getFile());

    try {
      consumer.init(node.getWriter("Twitter", JSONEvent.class));
    } catch (ConsumerInitException e) {
      Logger.error("Consumer init error: " + e.getMessage());
      System.exit(-1);
    }
    consumer.start();
    Logger.info("Application has started");
  }

  public void onStop(Application app) {
    Logger.info("Application shutdown...");
  }

  // For CORS
  private class ActionWrapper extends Action.Simple {
    public ActionWrapper(Action<?> action) {
      this.delegate = action;
    }

    @Override
    public Promise<Result> call(Http.Context ctx) throws java.lang.Throwable {
      Promise<Result> result = this.delegate.call(ctx);
      Http.Response response = ctx.response();
      response.setHeader("Access-Control-Allow-Origin", "*");
      return result;
    }
  }

  @Override
  public Action<?> onRequest(Http.Request request,
      java.lang.reflect.Method actionMethod) {
    return new ActionWrapper(super.onRequest(request, actionMethod));
  }

}