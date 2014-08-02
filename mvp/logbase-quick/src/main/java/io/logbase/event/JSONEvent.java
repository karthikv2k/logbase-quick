package io.logbase.event;

public class JSONEvent extends Event {

  public JSONEvent(int account, String source) {
    super(account, source);
  }

  public String getJSONString() {
    return (String) getData();
  }
}
