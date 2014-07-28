package io.logbase.node.impl.realtime.data.columns;

import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.logbase.datamodel.Column;
import io.logbase.datamodel.ColumnIterator;
import io.logbase.datamodel.types.IntType;
import io.logbase.utils.Utils;

public class ListBackedColumnTest {

  static final Logger logger = LoggerFactory
      .getLogger(ListBackedColumnTest.class);

  public ListBackedColumn createTestColumn() throws Exception {
    Integer[] values = { 1, 2, 3, 4, 5 };
    long[] rowNums = { 2, 4, 5, 8, 9 };
    ListBackedColumn column = new ListBackedColumn("test", 0);
    IntType intType = new IntType();
    for (int i = 0; i < values.length; i++) {
      intType.set(values[i]);
      column.append(intType, rowNums[i]);
    }
    return column;
  }

  public ListBackedColumn createTestArrayColumn() throws Exception {
    Integer[] values = { 1, 2, 3, 4, 5 };
    long[] rowNums = { 2, 2, 3, 5, 5 };
    int[][] arrayIdx = { { 0, 0 }, { 0, 1 }, { 0, 1 }, { 0, 0 }, { 0, 1 } };
    ListBackedColumn column = new ListBackedColumn("test", 2);
    IntType intType = new IntType();
    for (int i = 0; i < values.length; i++) {
      intType.set(values[i]);
      column.append(intType, rowNums[i], arrayIdx[i]);
    }
    return column;
  }

  @Test
  public void testGetSimpleIterator() throws Exception {
    Column column = createTestColumn();
    ColumnIterator it = column.getSimpleIterator();
    while (it.hasNext()) {
      logger.info((Utils.toString(it.next())));
    }
    column = createTestArrayColumn();
    it = column.getSimpleIterator();
    while (it.hasNext()) {
      logger.info((Utils.toString(it.next())));
    }
  }
}
