package io.logbase.collections.impl;

import org.junit.Test;import java.lang.Exception;import java.lang.System;

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
    BitPackedIntegerList list = new BitPackedIntegerList(100000, num);
    time = System.currentTimeMillis();
    list.write(values);
    System.out.println("write: " + (System.currentTimeMillis()-time));
    time = System.currentTimeMillis();
    list.read();
    System.out.println("read: " + (System.currentTimeMillis()-time));
  }
}
