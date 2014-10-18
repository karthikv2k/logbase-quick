package io.logbase.api.ui.workspace;

import java.util.List;

import javax.persistence.Embeddable;

import io.logbase.api.ui.query.QueryRequest;

@Embeddable
public class QueryUnit {

  private static final long serialVersionUID = 1L;

  private QueryRequest queryRequest;
  private List<String> tableColumns;
  private List<String> plotColumns;

  public QueryRequest getQueryRequest() {
    return queryRequest;
  }

  public void setQueryRequest(QueryRequest queryRequest) {
    this.queryRequest = queryRequest;
  }

  public List<String> getTableColumns() {
    return tableColumns;
  }

  public void setTableColumns(List<String> tableColumns) {
    this.tableColumns = tableColumns;
  }

  public List<String> getPlotColumns() {
    return plotColumns;
  }

  public void setPlotColumns(List<String> plotColumns) {
    this.plotColumns = plotColumns;
  }

}
