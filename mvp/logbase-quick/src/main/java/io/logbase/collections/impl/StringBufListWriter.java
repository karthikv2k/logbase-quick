package io.logbase.collections.impl;

import io.logbase.collections.BatchListWriter;
import io.logbase.collections.nativelists.IntListWriter;
import io.logbase.collections.nativelists.StringListWriter;

import java.nio.CharBuffer;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class StringBufListWriter implements StringListWriter {
  private final CharBuffer stringBuf;
  private final IntListWriter lengthWriter;
  public final String CHARSET = "UTF-8";

  StringBufListWriter(StringBufList list) {
    stringBuf = list.getWriteBuffer();
    lengthWriter = list.lengthList.primitiveWriter();
  }

  @Override
  public boolean close() {
    return false;
  }

  @Override
  public void add(Object values, int offset, int length) {
    checkArgument(values instanceof CharBuffer[], "values should be instance of CharBuffer[].");
    checkArgument(values != null, "values can't be null");
    CharBuffer[] valueArray = (CharBuffer[]) values;
    for (int i = 0; i < valueArray.length; i++) {
      add(valueArray[i]);
    }
  }

  @Override
  public void add(CharBuffer value) {
    lengthWriter.addPrimitive(value.length());
    stringBuf.put(value);
  }

}
