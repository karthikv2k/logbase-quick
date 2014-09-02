package io.logbase.buffer;

import java.nio.*;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class BufferFactory {

  public static ByteBuffer newByteBuffer(int capacity){
    return ByteBuffer.allocateDirect(capacity);
  }

  public static ByteBuffer newBufWithIntCapacity(int capacity){
    return newByteBuffer(capacity*(Integer.SIZE/8));
  }

  public static ByteBuffer newBufWithLongCapacity(int capacity){
    return newByteBuffer(capacity*(Long.SIZE/8));
  }

  public static ByteBuffer newBufWithCharCapacity(int capacity){
    return newByteBuffer(capacity*(Character.SIZE/8));
  }

  public static ByteBuffer newBufWithFloatCapacity(int capacity){
    return newByteBuffer(capacity*(Float.SIZE/8));
  }

  public static ByteBuffer newBufWithDoubleCapacity(int capacity){
    return newByteBuffer(capacity*(Double.SIZE/8));
  }

}
