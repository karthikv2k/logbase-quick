package io.logbase.collections.impl;

import io.logbase.collections.nativelists.IntListReader;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class BitPackIntListReader implements IntListReader {
  private BitPackIntList list;

  BitPackIntListReader(BitPackIntList list, long maxIndex) {
    this.list = list;
  }

  @Override
  public Integer get(long index) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public int getAsInt(long index) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }
}
