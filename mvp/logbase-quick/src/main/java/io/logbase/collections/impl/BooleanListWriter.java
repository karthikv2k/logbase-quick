package io.logbase.collections.impl;

import io.logbase.collections.BatchIterator;
import io.logbase.collections.BatchListWriter;


import java.util.BitSet;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by Kousik on 25/08/14.
 */
public class BooleanListWriter implements BatchListWriter<Boolean>{

  private BooleanList list;
  boolean isClosed = false;
  private int arrayIndex = 0;
  private boolean[] holder = new boolean[1];

  public BooleanListWriter(BooleanList list) {
    checkArgument(list.size() == 0, "The list is not empty.");
    this.list = list;
  }

  /*
   * Iterate the given array from offset till length and insert
   * entries into the BitSet
   */
  public void write(boolean[] values, int offset, int length) {
    int start = offset;
    int end = offset + length;

    for (; start < end; start++) {
      if (values[start] == true) {
        list.bits.set(arrayIndex);
      }
      arrayIndex++;
      list.incrSize();
    }
  }

  @Override
  public void add(Boolean value) {
    checkNotNull(value, "Null vales are not permitted");
    checkState(!isClosed, "Attempting to modify a closed list.");
    holder[0] = value.booleanValue();
    write(holder, 0, 1);
  }

  @Override
  public void addPrimitiveArray(Object values, int offset, int length) {
    checkNotNull(values, "Null vales are not permitted");
    checkArgument(values instanceof boolean[], "values must be boolean[], found " + values.getClass().getSimpleName());
    checkState(!isClosed, "Attempting to modify a closed list.");
    write((boolean[]) values, offset, length);
  }

  @Override
  public void addAll(BatchIterator<Boolean> iterator) {
    boolean[] buffer = new boolean[1024];
    int count;
    if (iterator.primitiveTypeSupport()) {
      while (iterator.hasNext()) {
        count = iterator.readNative(buffer, 0, buffer.length);
        if (count > 0) {
          break;
        }
        write(buffer, 0, count);
      }
    } else {
      while (iterator.hasNext()) {
        for (count = 0;
             count < buffer.length && iterator.hasNext();
             count++) {
          buffer[count] = iterator.next();
        }
        write(buffer, 0, count);
      }
    }
  }

  @Override
  public boolean primitiveTypeSupport() {
    return true;
  }

  @Override
  public boolean close() {
    isClosed = true;
    return isClosed;
  }
}
