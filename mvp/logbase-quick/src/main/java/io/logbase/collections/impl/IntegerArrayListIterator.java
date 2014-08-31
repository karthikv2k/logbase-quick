package io.logbase.collections.impl;

import io.logbase.collections.nativelists.IntListIterator;

import java.nio.IntBuffer;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class IntegerArrayListIterator implements IntListIterator {
  private final IntBuffer[] blocks;
  private final IntegerArrayList list;
  private int index;
  private int limit;
  private int block;
  private long totalRead;
  private final long maxIndex;
  private final long size;

  IntegerArrayListIterator(IntegerArrayList list, List<IntBuffer> blocksList, long maxIndex) {
    synchronized (blocksList) {
      blocks = blocksList.toArray(new IntBuffer[0]);
    }
    this.list = list;
    size = list.size();
    this.maxIndex = Math.min(maxIndex, size);
    reset();
  }

  private void nextBlock() {
    block++;
    index = 0;
    limit = blocks[block].capacity();
  }

  @Override
  public boolean hasNext() {
    return totalRead < maxIndex;
  }

  private long remaining(){
    return maxIndex - totalRead;
  }

  @Override
  public int nextPrimitive(int[] buffer, int offset, int count) {
    count = (int) Math.min(count, (maxIndex-totalRead));
    int curCnt = 0;
    int batchSize;
    while (curCnt < count) {
      batchSize = Math.min((count - curCnt), limit - index);
      blocks[block].get(buffer, offset + curCnt, batchSize);
      index = index + batchSize;
      curCnt = curCnt + batchSize;
      totalRead = totalRead + batchSize;
      if (index >= limit) {
        nextBlock();
      }
    }
    return curCnt;
  }

  @Override
  public void reset() {
    if (blocks.length != 0) {
      limit = blocks[0].capacity();
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
    checkArgument(buffer instanceof int[], "buffer must be int[], found " + buffer.getClass().getSimpleName());
    return nextPrimitive((int[]) buffer, offset, count);
  }
}
