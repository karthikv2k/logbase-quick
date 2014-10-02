package io.logbase.collections.impl;

import io.logbase.collections.nativelists.IntListIterator;
import io.logbase.column.TypeUtils;

import java.nio.CharBuffer;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Kousik on 18/09/14.
 */
public class StringListTestSuite {
  private final BatchListFactory<CharBuffer> factory;
  private final String[] testData;

  StringListTestSuite(BatchListFactory<CharBuffer> factory, String[] testData){
    this.factory = factory;
    this.testData = testData;
  }

  public void testList(){
    StringBufList list = (StringBufList) factory.newInstance();
    StringBufListWriter writer = new StringBufListWriter(list);
    long totalWrites=0;

    /*
     * Writer test
     */
    for(int i=0; i<testData.length; i++){
      writer.add((CharBuffer) TypeUtils.castToLB(testData[i]));
      totalWrites++;
    }
    assertEquals(list.size(), totalWrites);

    /*
     * Iterator test, read in batch
     */
    StringBufListIterator it = new StringBufListIterator(list, list.size());

    int count =0;
    CharBuffer[] buffer = new CharBuffer[(int)list.size()];
    count = it.next(buffer, 0, (int)list.size());
    for (int i = 0; i < count; i++) {
      assert(testData[i].equals(buffer[i].toString()));
    }

    /*
     * Iterator test, read one entry at a time
     */

    CharBuffer[] buf = new CharBuffer[1];
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
    IntListIterator lengthIterator = (IntListIterator)list.lengthList.iterator(list.size());
    CharBuffer readBuffer = list.getReadBuffer();
    StringBufListReader reader = new StringBufListReader(readBuffer, lengthIterator);
    iter = DataGen.randomInt(0, (int)list.size() - 1);
    while(iter>=0) {
      long index = DataGen.randomInt(0, (int)list.size()-1);
      buf[0] = reader.get(index);
      assert(testData[(int)index].equals(buf[0].toString()));
      iter--;
    }
  }
}
