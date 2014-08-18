package io.logbase.collections.impl;

import io.logbase.column.TypeUtils;
import org.junit.Test;

import java.nio.CharBuffer;
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


}
