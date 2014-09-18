package io.logbase.collections.impl;

import io.logbase.collections.nativelists.IntListIterator;
import io.logbase.column.TypeUtils;
import io.logbase.utils.Filter;
import io.logbase.utils.RegexFilter;

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
    StringList list = (StringList) factory.newInstance();
    StringListWriter writer = new StringListWriter(list);
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
    StringListIterator it = new StringListIterator(list, list.size());

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
    IntListIterator lengthIterator = (IntListIterator)list.lengthList.iterator(list.size() - 1);
    CharBuffer readBuffer = list.getReadBuffer();
    StringListReader reader = new StringListReader(readBuffer, lengthIterator);
    iter = DataGen.randomInt(0, (int)list.size() - 1);
    while(iter<=0) {
      long index = DataGen.randomInt(0, (int)list.size()-1);
      buf[0] = reader.get(index);
      assert(testData[(int)index].equals(buf[0].toString()));
      iter--;
    }


    /*
     * Iterator test - With filters. Match for entries which has numbers
     * TODO - Add more test cases
     */
    Filter<String> filter =  new RegexFilter("(.*)\\d(.*)");
    StringListIterator itr = new StringListIterator(list, list.size(), filter);

    count=0;

    while(itr.hasNext()){
      count = itr.next(buffer, 0, (int)list.size());
      for (int i=0; i<count; i++) {
        assert(buffer[i].toString().matches("(.*)\\d(.*)"));
      }
    }

  }
}
