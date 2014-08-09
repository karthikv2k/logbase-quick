package io.logbase.collections.impl;

import io.logbase.collections.ReadonlyListWriter;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class BitPackIntListWriter implements ReadonlyListWriter<Integer> {
  private BitPackIntList list;
  private int arrayIndex = 0;
  private int bitIndex = 64; //index (1 indexed) where MSB of the input goes
  boolean isClosed = false;
  private int[] holder = new int[1];

  public BitPackIntListWriter(BitPackIntList list){
    checkArgument(list.size==0, "The list is not empty.");
    this.list=list;
  }

  public void write(int[] values){
    long cur;
    long buf = list.buf.getLong(arrayIndex);
    for(int value: values){
      cur = value - list.minValue;
      if(bitIndex>=list.width){ //minimum free space required to insert a value
        bitIndex = bitIndex - list.width;
      }else{
        cur = cur >>> (list.width - bitIndex);
        buf |= cur;
        cur = value - list.minValue;
        bitIndex = 64 - (list.width - bitIndex);
        list.buf.setLong(arrayIndex, buf);
        arrayIndex++;
        buf=0;
      }
      cur = cur << bitIndex;
      buf |= cur;
      list.size++;
    }
    list.buf.setLong(arrayIndex, buf);
  }

  @Override
  public void append(Integer value) {
    checkNotNull(value, "Null vales are not permitted");
    checkState(!isClosed, "Attempting to modify a closed list.");
    holder[0] = value.intValue();
    write(holder);
  }

  @Override
  public void append(Integer[] values) {
    checkNotNull(values, "Null vales are not permitted");
    checkState(!isClosed, "Attempting to modify a closed list.");
    int[] holder = new int[values.length];
    for(int i=0; i<values.length; i++){
      checkNotNull(values[i], "Null vales are not permitted");
      holder[i] = values[i];
    }
    write(holder);
  }

  @Override
  public void appendNativeArray(Object values) {
    checkNotNull(values, "Null vales are not permitted");
    checkArgument(values instanceof int[], "values must be int[], found " + values.getClass().getSimpleName());
    checkState(!isClosed, "Attempting to modify a closed list.");
    write((int[]) values);
  }

  @Override
  public void close() {
    isClosed = true;
  }
}
