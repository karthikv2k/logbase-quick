package io.logbase.api.ui.workspace;

import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Embeddable
public class Tile {

  private static final long serialVersionUID = 1L;

  private String name;
  @OneToMany(fetch = FetchType.EAGER)
  private List<QueryUnit> queries;

  public List<QueryUnit> getQueries() {
    return queries;
  }

  public void setQueries(List<QueryUnit> queries) {
    this.queries = queries;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
