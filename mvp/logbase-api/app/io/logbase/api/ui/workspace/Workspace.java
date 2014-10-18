package io.logbase.api.ui.workspace;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Workspace {

  private static final long serialVersionUID = 1L;

  @Id
  private String name;
  @OneToMany(fetch = FetchType.EAGER)
  private List<Tile> tiles;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Tile> getTiles() {
    return tiles;
  }

  public void setTiles(List<Tile> tiles) {
    this.tiles = tiles;
  }

}
