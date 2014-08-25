package io.logbase.collections.impl;

import io.logbase.collections.BatchListReader;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Kousik on 25/08/14.
 */
public class BooleanListReader implements BatchListReader<Boolean> {
  private BooleanList list;

  BooleanListReader(BooleanList list, long maxIndex) {
    this.list = list;
  }

  @Override
  public Boolean get(long index) {
    return list.bits.get((int)index);
  }

  @Override
  public boolean primitiveTypeSupport() {
    return true;
  }

  @Override
  public void getPrimitiveArray(Object values, int offset, long[] index, int idxOffset, int length) {
    checkNotNull(values, "Null array is not permitted");
    checkArgument(values instanceof boolean[], "buffer must be boolean[], found " +
      values.getClass().getSimpleName());
    boolean[] nativeBuffer = (boolean[]) values;
    checkArgument(offset + length <= nativeBuffer.length, "length of buffer should be greater than offset+rows.");
    for (int i=0; i<length; i++) {
      nativeBuffer[offset + i] = list.bits.get((int)index[idxOffset + i]);
    }
  }
}
