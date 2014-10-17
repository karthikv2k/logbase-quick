package io.logbase.collections.impl;

import io.logbase.collections.BatchList;
import io.logbase.collections.BatchListIterator;
import io.logbase.collections.BatchListReader;
import io.logbase.collections.BatchListWriter;
import io.logbase.column.TypeUtils;

import java.lang.reflect.Array;
import java.nio.CharBuffer;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Kousik on 18/09/14.
 */
public class StringListTestSuite<E> {
  private final BatchListFactory<E> factory;
  private final String[] testData;

  StringListTestSuite(BatchListFactory<E> factory, String[] testData){
    this.factory = factory;
    this.testData = testData;
  }

  public void testList(){
    BatchList list = factory.newInstance();
    BatchListWriter writer = list.writer();
    long totalWrites=0;

    /*
     * Writer test
     */
    for(int i=0; i<testData.length; i++){
      writer.add(testData[i]);
      totalWrites++;
    }
    assertEquals(list.size(), totalWrites);

    /*
     * Iterator test, read in batch
     */
    BatchListIterator it = list.iterator(list.size());

    int count =0;
    E[] buffer = (E[]) Array.newInstance(list.type(), (int)list.size());
    count = it.next(buffer, 0, (int)list.size());
    for (int i = 0; i < count; i++) {
      assert(testData[i].equals(buffer[i].toString()));
    }

    /*
     * Iterator test, read one entry at a time
     */

    E[] buf = (E[]) Array.newInstance(list.type(), (int)list.size());
    int iter = 0;
    it.rewind();
    while (it.hasNext()) {
      it.next(buf, 0, 1);
      assert(testData[iter].equals(buf[0].toString()));
      iter++;
    }

    /*
     * Reader test - read random entries from the list and verify the content
     */
    BatchListReader reader = list.reader(list.size());
    iter = DataGen.randomInt(0, (int)list.size() - 1);
    while(iter>=0) {
      long index = DataGen.randomInt(0, (int)list.size()-1);
      buf[0] = (E)reader.get(index);
      assert(testData[(int)index].equals(buf[0].toString()));
      iter--;
    }
  }
}
