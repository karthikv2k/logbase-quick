package io.logbase.collections.impl;

import io.logbase.collections.BatchIterator;
import io.logbase.collections.BatchListWriter;

import java.nio.IntBuffer;
import java.util.function.IntConsumer;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class IntegerListWriter implements BatchListWriter<Integer>, IntConsumer {
  private boolean isClosed;
  private IntBuffer buf;
  private final IntegerList list;


  IntegerListWriter(IntegerList list){
    this.list = list;
  }

  @Override
  public boolean primitiveTypeSupport() {
    return true;
  }

  @Override
  public void addPrimitiveArray(Object values, int offset, int length) {
    checkNotNull(values, "Null vales are not permitted");
    checkArgument(values instanceof int[], "values must be int[], found " + values.getClass().getSimpleName());
    int[] intValues = (int[]) values;
    //TBA optimize
    for (int i = 0; i < length; i++) {
      accept(intValues[offset + i]);
    }
  }

  @Override
  public boolean close() {
    isClosed = true;
    return isClosed;
  }

  @Override
  public void add(Integer integer) {
    accept(integer.intValue());
  }

  @Override
  public void addAll(BatchIterator<Integer> iterator) {

  }

  @Override
  public void accept(int value) {
    if (!buf.hasRemaining()) {
      buf = list.addBlock();
    }
    buf.put(value);
  }

}
