package io.logbase.collections.impl;

import io.logbase.collections.LBBitSet;
import org.junit.Test;


import static junit.framework.Assert.assertTrue;

/**
 * Created by Kousik on 17/08/14.
 */
public class BooleanListTest {
  private int num = 100 * 1000 * 1000;
  private boolean[] values = new boolean[num];
  private long time = System.currentTimeMillis();

  @Test
  public void booleanListTest() {

    for (int i = 0; i < num; i++) {
      values[i] = (i%100 == 0) ? true : false;
    }
    System.out.println("init: " + (System.currentTimeMillis() - time));

    test(new NativeBitSet());
    test(new OffHeapBitSet(1));
  }

  private void test(LBBitSet bitset) {
    BooleanList list;

    /*
     * Initialize the list
     */
    if (bitset.getClass().equals(OffHeapBitSet.class)) {
      System.out.println("Boolean list with OffHeapBitSet");
      list = new BooleanList(new OffHeapBitSet(1), values.length);
    } else {
      System.out.println("Boolean list with native BitSet");
      list = new BooleanList();
    }

    /*
     * Get a writer and write all values
     */
    BooleanListWriter writer = new BooleanListWriter(list);

    time = System.currentTimeMillis();
    writer.addPrimitiveArray(values, 0, values.length);
    System.out.println("write: " + (System.currentTimeMillis() - time));

    /*
     * Read using Iterator
     */
    BooleanListIterator itr = new BooleanListIterator(list);
    time = System.currentTimeMillis();
    boolean[] holder = new boolean[1024 * 10];
    int cnt = 0;
    int totalReads = 0;
    while (cnt != -1) {
      cnt = itr.readNative(holder, 0, holder.length);
      totalReads = totalReads + cnt;
    }
    assertTrue((totalReads + 1) == num);
    System.out.println("read: " + (System.currentTimeMillis() - time));

    itr = new BooleanListIterator(list);
    cnt = 0;
    totalReads = 0;
    while (true) {
      cnt = itr.readNative(holder, 0, holder.length);
      if (cnt == -1) break;
      for (int j = 0; j < cnt; j++) {
        assertTrue(values[totalReads] == holder[j]);
        totalReads++;
      }
    }

    /*
     * Read using a reader
     */
    long index[] = new long[num];
    boolean buffer[] = new boolean[num];
    for (int i=0; i<num; i++) {
      index[i] = i;
    }

    BooleanListReader reader = new BooleanListReader(list, 0);
    time = System.currentTimeMillis();
    reader.getPrimitiveArray(buffer, 0, index, 0, num);
    System.out.println("Read using a reader: " + (System.currentTimeMillis() - time));

    for (int i=0; i<num; i++) {
      assertTrue(values[i] == buffer[i]);
    }
  }
}
