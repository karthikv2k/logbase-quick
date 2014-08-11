package io.logbase.column.appendonly;

import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.logbase.column.Column;
import io.logbase.column.ColumnIterator;
import io.logbase.utils.Utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ListBackedColumnTest {

  static final Logger logger = LoggerFactory
      .getLogger(ListBackedColumnTest.class);

  public static ListBackedColumn createTestColumn() throws Exception {
    Integer[] values = { 1, 2, 3, 4, 5 };
    long[] rowNums = { 2, 4, 5, 8, 9 };
    ListBackedColumn column = new ListBackedColumn("test", 0);
    for (int i = 0; i < values.length; i++) {
      column.append(values[i], rowNums[i]);
    }
    return column;
  }

  public ListBackedColumn createTestArrayColumn() throws Exception {
    Integer[] values = { 1, 2, 3, 4, 5 };
    long[] rowNums = { 2, 2, 3, 5, 5 };
    int[][] arrayIdx = { { 0, 0 }, { 0, 1 }, { 0, 1 }, { 0, 0 }, { 0, 1 } };
    ListBackedColumn column = new ListBackedColumn("test", 2);
    for (int i = 0; i < values.length; i++) {
      column.append(values[i], rowNums[i], arrayIdx[i]);
    }
    return column;
  }

  @Test
  public void testGetSimpleIterator() throws Exception {
    Column column = createTestColumn();
    ColumnIterator it = column.getSimpleIterator();
    Integer[] values = { null, null, 1, null, 2, 3, null, null, 4, 5};
    int i = 0;
    while (it.hasNext()) {
      assertEquals(values[i++], it.next());
    }

    column = createTestArrayColumn();
    it = column.getSimpleIterator();
    i = 0;
    Integer[][] arrayValues = {null, null, {1, 2}, {3}, null, {4, 5}};
    while (it.hasNext()) {
      Object temp = it.next();
      if(arrayValues[i] == null){
        assertNull(temp);
      }else{
        Object[] tempArray = (Object[]) temp;
        for(int j=0; j<arrayValues[i].length; j++ ){
          assertEquals(arrayValues[i][j], tempArray[j]);
        }
      }
      i++;
    }
  }
}
