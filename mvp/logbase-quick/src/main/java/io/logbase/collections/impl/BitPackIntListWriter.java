package io.logbase.collections.impl;

import io.logbase.collections.BatchIterator;

import java.util.Iterator;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class BitPackIntListWriter extends BaseList<Integer> {
  private BitPackIntList list;
  private int arrayIndex = 0;
  private int bitIndex = 64; //index (1 indexed) where MSB of the input goes
  boolean isClosed = false;
  private int[] holder = new int[1];

  public BitPackIntListWriter(BitPackIntList list){
    checkArgument(list.size==0, "The list is not empty.");
    this.list=list;
  }

  public void write(int[] values, int offset, int length){
    long cur;
    long buf = list.buf.getLong(arrayIndex);
    for(int i=0; i<length; i++){
      cur = values[offset+i] - list.minValue;
      if(bitIndex>=list.width){ //minimum free space required to insert a value
        bitIndex = bitIndex - list.width;
      }else{
        cur = cur >>> (list.width - bitIndex);
        buf |= cur;
        cur = values[offset+i] - list.minValue;
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
  public boolean add(Integer value) {
    checkNotNull(value, "Null vales are not permitted");
    checkState(!isClosed, "Attempting to modify a closed list.");
    holder[0] = value.intValue();
    write(holder, 0 ,1);
    return true;
  }

  @Override
  public void addPrimitiveArray(Object values, int offset, int length) {
    checkNotNull(values, "Null vales are not permitted");
    checkArgument(values instanceof int[], "values must be int[], found " + values.getClass().getSimpleName());
    checkState(!isClosed, "Attempting to modify a closed list.");
    write((int[]) values, offset, length);
  }

  @Override
  public long sizeAsLong() {
    return list.size;
  }

  @Override
  public boolean primitiveTypeSupport() {
    return true;
  }

  @Override
  public BatchIterator<Integer> batchIterator(long maxIndex) {
    return new BitPackIntListIterator(list);
  }

  @Override
  public boolean close() {
    isClosed = true;
    return isClosed;
  }

  @Override
  public boolean isEmpty() {
    return size()>0;
  }

  @Override
  public Iterator<Integer> iterator() {
    return batchIterator(-1);
  }
}
