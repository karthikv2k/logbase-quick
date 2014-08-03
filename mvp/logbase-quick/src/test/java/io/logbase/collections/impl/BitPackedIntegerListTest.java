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
    int num = 1000*1000*100;
    int[] values = new int[num];
    long time = System.currentTimeMillis();
    for(int i=0; i<num; i++){
      values[i] = i%100000;
    }
    System.out.println("init: " + (System.currentTimeMillis()-time));
    BitPackIntList list = new BitPackIntList(100000, num);
    BitPackIntListWriter writer = new BitPackIntListWriter(list);
    time = System.currentTimeMillis();
    writer.write(values);
    System.out.println("write: " + (System.currentTimeMillis()-time));
    BitPackIntListReader reader = new BitPackIntListReader(list);
    time = System.currentTimeMillis();
    int[] holder = new int[1024*10];
    int cnt = 0;
    int totalReads = 0;
    while(cnt!=-1){
      cnt = reader.read(holder, 0, holder.length);
      totalReads = totalReads+cnt;
    }
    assertTrue((totalReads+1)==num);
    System.out.println("read: " + (System.currentTimeMillis()-time));
  }
}
