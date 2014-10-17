package io.logbase.api.ui.query;

import java.util.List;

public class QueryRequest {

  private String args;
  private long from;
  private long to;
  private List<Integer> source;

  public String getArgs() {
    return args;
  }

  public void setArgs(String args) {
    this.args = args;
  }

  public long getFrom() {
    return from;
  }

  public void setFrom(long from) {
    this.from = from;
  }

  public long getTo() {
    return to;
  }

  public void setTo(long to) {
    this.to = to;
  }

  public List<Integer> getSource() {
    return source;
  }

  public void setSource(List<Integer> source) {
    this.source = source;
  }

}
