package io.logbase.collections.impl;

import io.logbase.collections.BatchListIterator;
import io.logbase.collections.BatchListWriter;
import io.logbase.collections.nativelists.IntListWriter;

import java.nio.CharBuffer;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class StringListWriter implements BatchListWriter<CharBuffer> {
  private final CharBuffer stringBuf;
  private final IntListWriter lengthWriter;
  public final String CHARSET = "UTF-8";

  StringListWriter(StringList list) {
    stringBuf = list.getWriteBuffer();
    lengthWriter = list.lengthList.primitiveWriter();
  }

  @Override
  public boolean close() {
    return false;
  }

  @Override
  public void add(Object values, int offset, int length) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void add(CharBuffer value) {
    lengthWriter.addPrimitive(value.length());
    stringBuf.put(value);
  }

  @Override
  public BatchListWriter<CharBuffer> addAll(BatchListIterator<CharBuffer> iterator) {
    //To change body of implemented methods use File | Settings | File Templates.
    return this;
  }

  public void addPrimitiveArray(Object values, int offset, int length) {
    checkArgument(values instanceof CharBuffer[], "values should be instance of CharBuffer[].");
    checkArgument(values != null, "values can't be null");
    CharBuffer[] valueArray = (CharBuffer[]) values;
    for (int i = 0; i < valueArray.length; i++) {
      add(valueArray[i]);
    }
  }

}
