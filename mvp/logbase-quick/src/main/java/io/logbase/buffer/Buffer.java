package io.logbase.buffer;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public abstract class Buffer {

  public long getLong(long index){
    throw new UnsupportedOperationException();
  }

  public void setLong(long index, long value){
    throw new UnsupportedOperationException();
  }

  public long getLong(int index){
    throw new UnsupportedOperationException();
  }

  public void setLong(int index, long value){
    throw new UnsupportedOperationException();
  }

  public void getBytes(byte[] outBuf, int offset, int length, int index){
    throw new UnsupportedOperationException();
  }

  public void setBytes(byte[] inBuf, int offset, int length, int index){
    throw new UnsupportedOperationException();
  }
}