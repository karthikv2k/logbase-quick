package io.logbase.collections.impl;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class UniqStringConsumer {

  Set<byte[]> uniqValues = new TreeSet<byte[]>();

  public void accept(CharSequence value) {
    uniqValues.add(value.toString().getBytes(Charset.forName("UTF-8")));
  }

  public Iterator<byte[]> iterator() {
    return uniqValues.iterator();
  }

  public int numValues() {
    return uniqValues.size();
  }

  public long totalSize() {
    long totalSize = 0;
    /*for(byte[] value: this){
      totalSize += value.length;
    } */
    return totalSize;
  }

}
