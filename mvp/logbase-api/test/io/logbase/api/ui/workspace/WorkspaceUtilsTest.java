package io.logbase.api.ui.workspace;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.logbase.api.ui.query.QueryRequest;

import org.junit.Before;
import org.junit.Test;

public class WorkspaceUtilsTest {

  private Workspace ws;

  @Before
  public void init() {
    ws = new Workspace();
    ws.setName("Test Workspace");
    QueryUnit qu = new QueryUnit();
    QueryRequest qr = new QueryRequest();
    qr.setArgs("BIEBER");
    qu.setQueryRequest(qr);
    List<String> tableColumns = Arrays.asList("col1", "col2");
    qu.setTableColumns(tableColumns);
    Tile tile = new Tile();
    List<QueryUnit> queryUnits = new ArrayList<QueryUnit>();
    queryUnits.add(qu);
    tile.setQueries(queryUnits);
    List<Tile> tiles = new ArrayList<Tile>();
    tiles.add(tile);
    ws.setTiles(tiles);
  }

  @Test
  public void saveWorkspace() {
    WorkspaceUtils.saveWorkspace(ws);
    assert (WorkspaceUtils.isSavedWorkspace(ws.getName()));
  }

  @Test
  public void getWorkspace() {
    Workspace fetchedWS = WorkspaceUtils.getWorkspace(ws.getName());
    assertEquals(fetchedWS.getName(), ws.getName());
    assertEquals(fetchedWS.getTiles().get(0).getQueries().get(0)
        .getQueryRequest().getArgs(), ws.getTiles().get(0).getQueries().get(0)
        .getQueryRequest().getArgs());
    assertEquals(fetchedWS.getTiles().get(0).getQueries().get(0)
        .getTableColumns().size(), ws.getTiles().get(0).getQueries().get(0)
        .getTableColumns().size());
  }

  @Test
  public void deleteWorkspace() {
    WorkspaceUtils.deleteWorkspace(ws.getName());
    assert (!WorkspaceUtils.isSavedWorkspace(ws.getName()));
  }

}
