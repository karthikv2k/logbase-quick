package io.logbase.utils.TimeStamp;

import com.google.gson.Gson;
import io.logbase.event.JSONEvent;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Kousik on 18/10/14.
 */
public class JSONTimeStampExtractor implements TimeStampExtractor<JSONEvent> {
  private long time;
  private Gson gson;
  private SimpleDateFormat sdf;

  public JSONTimeStampExtractor() {
    this("EEE MMM dd HH:mm:ss Z yyyy");
  }

  public JSONTimeStampExtractor(String timeStampFormat) {
    gson = new Gson();
    sdf = new SimpleDateFormat(timeStampFormat);
  }

  @Override
  public long timestamp(JSONEvent event) {
    Map json = gson.fromJson(event.getJSONString(), Map.class);
    Iterator itr = json.keySet().iterator();
    ParsePosition pos = new ParsePosition(0);

    while(itr.hasNext()) {
      Object key = itr.next();
      Object val = json.get(key);

      if (val != null & val instanceof String) {
        time = sdf.parse((String)val, pos).getTime();
        if (time !=0 ) {
          return time;
        }
        pos.setIndex(0);
      }
    }
    // Looks like we don't have a valid time stamp. Return event ingestion time stamp.
    return event.getTimestamp();
  }
}
