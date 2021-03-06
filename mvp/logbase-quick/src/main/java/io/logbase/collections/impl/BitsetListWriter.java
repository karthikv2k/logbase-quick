package io.logbase.collections.impl;

import io.logbase.collections.nativelists.BooleanListWriter;

import static com.google.common.base.Preconditions.*;

/**
 * Created by Kousik on 25/08/14.
 */
public class BitsetListWriter implements BooleanListWriter {

  private BitsetList list;
  boolean isClosed = false;
  private int arrayIndex = 0;
  private boolean[] holder = new boolean[1];

  public BitsetListWriter(BitsetList list) {
    checkArgument(list.size() == 0, "The list is not empty.");
    this.list = list;
  }

  /*
   * Iterate the given array from offset till length and insert
   * entries into the BitSet
   */
  @Override
  public void addNative(boolean[] values, int offset, int length) {
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
  public void accept(boolean value) {
    holder[0] = value;
    addNative(holder, 0, 1);
  }

  @Override
  public void add(Boolean value) {
    checkNotNull(value, "Null vales are not permitted");
    checkState(!isClosed, "Attempting to modify a closed list.");
    accept(value);
  }

  @Override
  public void add(Object values, int offset, int length) {
    checkNotNull(values, "Null vales are not permitted");
    checkArgument(values instanceof boolean[], "values must be boolean[], found " + values.getClass().getSimpleName());
    checkState(!isClosed, "Attempting to modify a closed list.");
    addNative((boolean[]) values, offset, length);
  }

  @Override
  public boolean close() {
    isClosed = true;
    return isClosed;
  }
}
