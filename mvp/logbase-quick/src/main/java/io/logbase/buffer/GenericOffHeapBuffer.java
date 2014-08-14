package io.logbase.buffer;

import java.nio.ByteBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class GenericOffHeapBuffer extends Buffer {

  public final ByteBuffer buf;

  public GenericOffHeapBuffer(int capacity) {
    buf = ByteBuffer.wrap(new byte[capacity]);
  }

  @Override
  public void getBytes(byte[] outBuf, int offset, int length, int index) {
    buf.position(index);
    buf.get(outBuf, offset, length);
  }

  @Override
  public void setBytes(byte[] inBuf, int offset, int length, int index) {
    buf.position(index);
    buf.put(inBuf, offset, length);
  }

  @Override
  public long getLong(long index) {
    return buf.getLong((int) (index * Long.SIZE / 8));
  }

  @Override
  public void setLong(long index, long value) {
    buf.putLong((int) (index * Long.SIZE / 8), value);
  }

  @Override
  public long getLong(int index) {
    return buf.getLong((index * Long.SIZE / 8));
  }

  @Override
  public void setLong(int index, long value) {
    buf.putLong((index * Long.SIZE / 8), value);
  }

}
