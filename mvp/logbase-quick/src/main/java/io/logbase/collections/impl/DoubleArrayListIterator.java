package io.logbase.collections.impl;

import io.logbase.collections.nativelists.DoubleListIterator;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created with IntelliJ IDEA File Template.
 * User: Kousik
 */
public class DoubleArrayListIterator implements DoubleListIterator {
  private final DoubleBuffer[] blocks;
  private final DoubleArrayList list;
  private int index;
  private int limit;
  private int block;
  private long totalRead;
  private final long maxIndex;
  private final long size;

  DoubleArrayListIterator(DoubleArrayList list, List<ByteBuffer> blocksList, long maxIndex) {
    synchronized (blocksList) {
      blocks = new DoubleBuffer[blocksList.size()];
      int i = 0;
      for (ByteBuffer item : blocksList) {
        blocks[i++] = item.asDoubleBuffer();
      }
    }
    this.list = list;
    size = list.size();
    this.maxIndex = Math.min(maxIndex, size);
    rewind();
  }

  private void nextBlock() {
    block++;
    index = 0;
    limit = blocks[block].remaining();
  }

  @Override
  public boolean hasNext() {
    return remaining() > 0;
  }

  @Override
  public long remaining() {
    return maxIndex - totalRead;
  }

  @Override
  public int nextPrimitive(double[] buffer, int offset, int count) {
    count = (int) Math.min(count, (maxIndex - totalRead));
    int curCnt = 0;
    int batchSize;
    while (curCnt < count) {
      //read as much as possible in current buffer
      batchSize = Math.min((count - curCnt), limit - index);
      try {
        blocks[block].get(buffer, offset + curCnt, batchSize);
      } catch (BufferUnderflowException e) {
        e.printStackTrace();
        System.out.println("Error in writing to the buffer: input (offset, count) = " + offset + ", " + count);
      }
      index = index + batchSize;
      curCnt = curCnt + batchSize;
      totalRead = totalRead + batchSize;
      if (index >= limit) {
        if (hasNext()) {
          nextBlock();
        } else {
          break;
        }
      }
    }
    return curCnt;
  }

  @Override
  public void rewind() {
    if (blocks.length != 0) {
      for (DoubleBuffer buf : blocks) {
        buf.rewind();
      }
      limit = blocks[0].remaining();
    } else {
      limit = 0;
    }
    index = 0;
    block = 0;
    totalRead = 0;
  }

  @Override
  public int next(Object buffer, int offset, int count) {
    checkNotNull(buffer, "Null buffer is not permitted");
    checkArgument(buffer instanceof double[], "buffer must be double[], found " + buffer.getClass().getSimpleName());
    return nextPrimitive((double[]) buffer, offset, count);
  }
}
