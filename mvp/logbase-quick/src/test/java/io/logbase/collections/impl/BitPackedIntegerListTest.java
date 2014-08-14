package io.logbase.collections.impl;

import org.junit.Test;import java.lang.Exception;import java.lang.System;

import static junit.framework.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class BitPackedIntegerListTest {
  @Test
  public void testWrite() throws Exception {
    int num = 100;
    int[] values = new int[num];
    long time = System.currentTimeMillis();
    for(int i=0; i<num; i++){
      values[i] = 100 + i%100000;
    }
    System.out.println("init: " + (System.currentTimeMillis()-time));
    BitPackIntBuffer list = new BitPackIntBuffer(100,100+100000, num);
    BitPackIntList writer = new BitPackIntList(list);
    time = System.currentTimeMillis();
    writer.write(values, 0, values.length);
    System.out.println("write: " + (System.currentTimeMillis()-time));
    BitPackIntListIterator reader = new BitPackIntListIterator(list);
    time = System.currentTimeMillis();
    int[] holder = new int[1024*10];
    int cnt = 0;
    int totalReads = 0;
    while(cnt!=-1){
      cnt = reader.readNative(holder, 0, holder.length);
      totalReads = totalReads+cnt;
    }
    assertTrue((totalReads + 1) == num);
    System.out.println("read: " + (System.currentTimeMillis()-time));
    reader = new BitPackIntListIterator(list);
    cnt = 0;
    totalReads = 0;
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
