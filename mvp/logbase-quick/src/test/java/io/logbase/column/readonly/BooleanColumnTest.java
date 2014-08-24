package io.logbase.column.readonly;

import io.logbase.collections.BatchIterator;
import io.logbase.column.ColumnIterator;
import io.logbase.column.appendonly.ListBackedColumn;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Kousik on 23/08/14.
 */
public class BooleanColumnTest {

    @Test
    public void benchmarkSimpleIterator() throws Exception {
        int num = 100*1000;
        boolean[] values = new boolean[num];
        long time = System.currentTimeMillis();
        for(int i=0; i<num; i++){
            values[i] = (i%4 == 0) ? true: false;
        }
        System.out.println("init: " + (System.currentTimeMillis()-time));

        time = System.currentTimeMillis();
        ListBackedColumn column = new ListBackedColumn("test", 0);
        for (int i = 0; i < values.length; i++) {
            column.append(values[i], i);
        }
        System.out.println("append only column write: " + (System.currentTimeMillis()-time));

        time = System.currentTimeMillis();
        BooleanColumn colRO = new BooleanColumn(column);
        System.out.println("append only to readonly column: " + (System.currentTimeMillis()-time));

        time = System.currentTimeMillis();
        ColumnIterator<Object> roIt = colRO.getSimpleIterator();
        while(roIt.hasNext()){
            roIt.next();
        }
        System.out.println("readonly column read (row): " + (System.currentTimeMillis()-time));

        time = System.currentTimeMillis();
        BatchIterator<Boolean> valuesIt = colRO.getValuesIterator();
        boolean[] holder = new boolean[1024];
        int cnt = 0;
        while(valuesIt.hasNext()){
            cnt = valuesIt.readNative(holder, 0, holder.length);
        }
        System.out.println("readonly column read (batch): " + (System.currentTimeMillis()-time) + " " + cnt);
    }
}
