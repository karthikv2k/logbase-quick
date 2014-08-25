package io.logbase.collections.impl;

import io.logbase.column.TypeUtils;
import org.junit.Test;

import java.nio.CharBuffer;
import java.util.IntSummaryStatistics;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class StringListIteratorTest {
  @Test
  public void testList() throws Exception {
    String[] values = {"aaa","aab","aa","aac"};
    List<CharBuffer> source = new LinkedList<CharBuffer>();
    for(String value: values){
      source.add((CharBuffer) TypeUtils.castToLB(value));
    }
    StringList list = new StringList(new BatchIteratorWrapper<CharBuffer>(source.iterator()));
    list.addAll(new BatchIteratorWrapper<CharBuffer>(source.iterator()));
    StringListIterator it = new StringListIterator(list.listBuffer);

    int i =0;
    while(it.hasNext()){
      assertEquals(values[i++], it.next().toString());
      //System.out.println(it.next().toString());
    }
  }

  @Test
  public void testListPerformance() throws Exception {
    CharBuffer[] values = new CharBuffer[1*1000*1000];
    IntSummaryStatistics stats = new IntSummaryStatistics();

    for(int i=0; i<values.length; i++){
      values[i] = CharBuffer.wrap(Double.toString(Math.random()));
      stats.accept(values[i].length());
    }

    StringList list = new StringList(stats);
    long time = System.currentTimeMillis();
    for(CharBuffer value: values){
      list.add(value);
    }
    System.out.println("write: " + (System.currentTimeMillis()-time));

    StringListIterator it = new StringListIterator(list.listBuffer);
    time = System.currentTimeMillis();
    while(it.hasNext()){
      it.next();
    }
    System.out.println("read: " + (System.currentTimeMillis()-time));
  }


}
