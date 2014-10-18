package io.logbase.api.ui.query;

import java.util.List;

import javax.persistence.Embeddable;

@Embeddable
public class QueryRequest {

  private String args;
  private String argsType;
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

  public String getArgsType() {
    return argsType;
  }

  public void setArgsType(String argsType) {
    this.argsType = argsType;
  }

}
