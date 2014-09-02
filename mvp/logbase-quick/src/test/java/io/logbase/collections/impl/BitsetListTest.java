package io.logbase.collections.impl;

import io.logbase.collections.*;
import org.apache.commons.lang.ObjectUtils;
import org.junit.Before;
import org.junit.Test;


import static junit.framework.Assert.assertTrue;

/**
 * Created by Kousik on 01/09/14.
 */
public class BitsetListTest implements BatchListFactory{
  private int num = 100 * 1000 * 1000;
  private boolean[] values = new boolean[num];
  private bitsetType type = bitsetType.OFFHEAP;

  public enum bitsetType {
    OFFHEAP, NATIVE
  };

  @Before
  public void setUp() {
    for (int i = 0; i < num; i++) {
      values[i] = (i%100 == 0) ? true : false;
    }
  }

  @Override
  public BitsetList newInstance() {
    switch (this.type) {
      case OFFHEAP:
        return new BitsetList(values.length);
      case NATIVE:
        return new BitsetList();
    }
    return null;
  }

  @Test
  public void BitsetListTest() {
    /*
     * Test offheap bitset
     */
    this.type = bitsetType.OFFHEAP;
    testBitset();

    /*
     * Test native bitset
     */
    this.type = bitsetType.NATIVE;
    testBitset();
  }

  private void testBitset() {
    BitsetList list;

    /*
     * Initialize the list
     */
      list = this.newInstance();

    /*
     * Test batch write
     */
    BatchListWriter writer = new BitsetListWriter(list);
    writer.add(values, 0, values.length);
    testBitSetRead(list);

    /*
     * Re-initialize the list
     */
    list = this.newInstance();


    /*
     * Test single write
     */
    writer = new BitsetListWriter(list);
    for (int i =0; i<num; i++) {
      writer.add(new Boolean(values[i]));
    }
    testBitSetRead(list);

  }

  private void testBitSetRead(BitsetList list) {

    /*
     * Read using Iterator next method
     */
    BatchListIterator itr = list.iterator(values.length);
    boolean[] holder = new boolean[1024 * 10];
    int cnt = 0;
    int totalReads = 0;
    while (cnt != -1) {
      cnt = itr.next(holder, 0, holder.length);
      totalReads = totalReads + cnt;
    }
    assertTrue((totalReads + 1) == num);

    /*
     * Reset the iterator and test batch reads.
     */
    itr.rewind();
    cnt = 0;
    totalReads = 0;
    while (true) {
      cnt = itr.next(holder, 0, holder.length);
      if (cnt == -1) break;
      for (int j = 0; j < cnt; j++) {
        assertTrue(values[totalReads] == holder[j]);
        totalReads++;
      }
    }

    /*
     * Read using a reader
     */
    BitsetListReader reader = new BitsetListReader(list, 0);
    for (int i=0; i<num; i++) {
      Boolean value = reader.get((long)i);
      assertTrue(values[i] == value.booleanValue());
    }
  }
}
