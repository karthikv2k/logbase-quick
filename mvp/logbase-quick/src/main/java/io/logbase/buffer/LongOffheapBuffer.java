package io.logbase.buffer;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class LongOffheapBuffer  extends Buffer {
  private LongBuffer buf;

  public LongOffheapBuffer(int capacity){
    buf = ByteBuffer.allocateDirect(capacity * (64/8)).asLongBuffer();
  }

  @Override
  public long getLong(long index){
    return buf.get((int)index);
  }

  @Override
  public void setLong(long index, long value){
    buf.put((int)index, value);
  }

  @Override
  public long getLong(int index){
    return buf.get(index);
  }

  @Override
  public void setLong(int index, long value){
    buf.put(index, value);
  }

}
