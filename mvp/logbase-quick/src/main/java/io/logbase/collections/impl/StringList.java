package io.logbase.collections.impl;

import io.logbase.buffer.Buffer;
import io.logbase.buffer.GenericOffHeapBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class StringList {
  public Buffer stringBuf;
  public Buffer offsetBuf;

  public StringList(int numValues, int toalSize) {
    stringBuf = new GenericOffHeapBuffer(toalSize);

  }
}