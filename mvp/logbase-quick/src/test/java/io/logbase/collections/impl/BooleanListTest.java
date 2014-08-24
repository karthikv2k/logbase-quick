package io.logbase.collections.impl;

import io.logbase.collections.BatchIterator;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Kousik on 17/08/14.
 */
public class BooleanListTest {
    @Test
    public void testWrite() throws Exception {
        int num = 100*1000*1000;
        boolean[] values = new boolean[num];
        long time = System.currentTimeMillis();

        /*
         * Initialize the boolean array
         */
        for(int i=0; i<num; i++){
            values[i] = (i%100 == 0) ? true: false;
        }
        System.out.println("init: " + (System.currentTimeMillis()-time));

        /*
         * Push the content of boolean array to the BooleanList
         */
        BooleanList list = new BooleanList();
        time = System.currentTimeMillis();
        list.write(values, 0, values.length);
        System.out.println("write: " + (System.currentTimeMillis()-time));

        /*
         * Iterate and read from list
         */
        BatchIterator reader = list.batchIterator(1);
        time = System.currentTimeMillis();
        boolean[] holder = new boolean[1024*10];
        int cnt = 0;
        int totalReads = 0;
        while(cnt!=-1){
            cnt = reader.readNative(holder, 0, holder.length);
            totalReads = totalReads+cnt;
        }
        assertTrue((totalReads + 1) == num);
        System.out.println("read native: " + (System.currentTimeMillis()-time));

        /*
         * Validate the contents
         */
        reader = list.batchIterator(1);
        totalReads = cnt = 0;
        while(true){
            cnt = reader.readNative(holder, 0, holder.length);
            if(cnt==-1) break;
            for(int j=0; j<cnt; j++){
                assertTrue(values[totalReads] == holder[j]);
                totalReads++;
            }
        }
    }
}
