package io.logbase.datamodel.types;

import io.logbase.datamodel.Event;

public class JSONEvent extends Event {

  public JSONEvent(int account, String source) {
    super(account, source);
  }

  public String getJSONString() {
    return (String) getData();
  }
}
