package io.logbase.column.readonly;

import io.logbase.column.Column;
import io.logbase.column.ColumnIterator;
import io.logbase.column.appendonly.ListBackedColumn;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;


/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class BitpackIntegerColumnTest {

  public static ListBackedColumn createTestColumn() throws Exception {
    Integer[] values = { 1, 2, 3, 4, 5 };
    long[] rowNums = { 2, 4, 5, 8, 9 };
    ListBackedColumn column = new ListBackedColumn("test", 0);
    for (int i = 0; i < values.length; i++) {
      column.append(values[i], rowNums[i]);
    }
    return column;
  }

  @Test
  public void testGetSimpleIterator() throws Exception {
    Column<Integer> col = createTestColumn();
    ColumnIterator<Object> apIt = col.getSimpleIterator();
    BitpackIntegerColumn colRO = new BitpackIntegerColumn(col);
    ColumnIterator<Object> roIt = colRO.getSimpleIterator();
    while(apIt.hasNext()){
      assertEquals(apIt.next(), roIt.next());
    }
  }

  @Test
  public void benchmarkSimpleIterator() throws Exception {
    int num = 1000*100;
    int[] values = new int[num];
    long time = System.currentTimeMillis();
    for(int i=0; i<num; i++){
      values[i] = 100 + i%100000;
    }
    System.out.println("init: " + (System.currentTimeMillis()-time));

    time = System.currentTimeMillis();
    ListBackedColumn column = new ListBackedColumn("test", 0);
    for (int i = 0; i < values.length; i++) {
      column.append(values[i], i);
    }
    System.out.println("append only column write: " + (System.currentTimeMillis()-time));

    time = System.currentTimeMillis();
    BitpackIntegerColumn colRO = new BitpackIntegerColumn(column);
    System.out.println("append only to readonly column: " + (System.currentTimeMillis()-time));

    time = System.currentTimeMillis();
    ColumnIterator<Object> roIt = colRO.getSimpleIterator();
    while(roIt.hasNext()){
      roIt.next();
    }
    System.out.println("readonly column read: " + (System.currentTimeMillis()-time));

  }
}
