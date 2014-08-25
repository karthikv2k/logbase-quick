package io.logbase.collections.impl;

import io.logbase.collections.BatchListReader;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class BitPackIntListReader implements BatchListReader<Integer> {
  private BitPackIntList list;

  BitPackIntListReader(BitPackIntList list, long maxIndex){
    this.list = list;
  }

  @Override
  public Integer get(long index) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public boolean primitiveTypeSupport() {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void getPrimitiveArray(Object values, int offset, long[] index, int idxOffset, int length) {
    //To change body of implemented methods use File | Settings | File Templates.
  }
}
