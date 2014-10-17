package io.logbase.api.ui.query;

public class QueryRequest {

  private String args;
  private long from;
  private long to;
  private int dataset;
  private String source;

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

  public int getDataset() {
    return dataset;
  }

  public void setDataset(int dataset) {
    this.dataset = dataset;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

}
