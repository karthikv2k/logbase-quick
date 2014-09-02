package io.logbase.collections.impl;

import io.logbase.collections.nativelists.IntListReader;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class IntegerArrayListReader implements IntListReader {
  private final IntBuffer[] blocks;
  private final long[] offset;

  IntegerArrayListReader(List<ByteBuffer> blocksList, long size) {
    synchronized (blocksList) {
      blocks = new IntBuffer[blocksList.size()];
      int i =0;
      for(ByteBuffer item: blocksList){
        blocks[i] =  item.asIntBuffer();
        blocks[i].rewind();
        i++;
      }
    }
    offset = new long[blocks.length];
    long min;
    long offset1 = 0;
    for (int i = 0; i < blocks.length; i++) {
      min = Math.min((long) blocks[i].remaining(), size);
      offset1 += min;
      offset[i] = offset1;
      size -= min;
    }
  }

  @Override
  public Integer get(long index) {
    return getAsInt(index);
  }

  @Override
  public int getAsInt(long index) {
    //for small arrays linear search should be faster than binary search
    long prev = 0;
    for (int i = 0; i < offset.length; i++) {
      if (index < offset[i]) {
        try {
          return blocks[i].get((int) (index - prev));
        }catch (IndexOutOfBoundsException e){
          e.printStackTrace();
          System.out.println("Index out of bounds. Size:" + blocks[i].limit() + " index:" + (index - prev));
          throw e;
        }
      }
      prev = offset[i];
    }
    throw new ArrayIndexOutOfBoundsException("input (" + index + ") exceeds list size (" + offset[offset.length] + ")");
  }

}
