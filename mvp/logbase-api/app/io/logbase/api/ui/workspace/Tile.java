package io.logbase.api.ui.workspace;

import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Embeddable
public class Tile {

  private static final long serialVersionUID = 1L;

  @OneToMany(fetch = FetchType.EAGER)
  List<QueryUnit> queries;

  public List<QueryUnit> getQueries() {
    return queries;
  }

  public void setQueries(List<QueryUnit> queries) {
    this.queries = queries;
  }

}
