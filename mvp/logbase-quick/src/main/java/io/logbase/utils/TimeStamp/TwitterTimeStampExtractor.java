package io.logbase.utils.TimeStamp;

import com.google.gson.Gson;
import io.logbase.event.Event;
import io.logbase.event.JSONEvent;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Created by Kousik on 18/10/14.
 */
public class TwitterTimeStampExtractor implements TimeStampExtractor<JSONEvent> {
  private long time;
  private Gson gson;
  private SimpleDateFormat sdf;

  public TwitterTimeStampExtractor() {
    this("EEE MMM dd HH:mm:ss Z yyyy");
  }

  public TwitterTimeStampExtractor(String timeStampFormat) {
    gson = new Gson();
    sdf = new SimpleDateFormat(timeStampFormat);
  }

  @Override
  public long timestamp(JSONEvent event) {
    Map json = gson.fromJson(event.getJSONString(), Map.class);
    ParsePosition pos = new ParsePosition(0);
    // TODO - use a regex. using specific key is not scalable.
    String str = (String)json.get("created_at");

    if (str == null) {
      /*
       * TODO - FIX HACK - when there is no timestamp, we return the timestamp of previous event.
       * If there is no previous time stamp, we return the event ingestion time.
       */
      return (time == 0)? event.getTimestamp() : time;
    }

    time = sdf.parse(str, pos).getTime();
    System.out.println(time);
    return time;
  }
}
