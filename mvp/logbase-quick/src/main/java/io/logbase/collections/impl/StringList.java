package io.logbase.collections.impl;

import io.logbase.collections.BatchList;
import io.logbase.collections.BatchListIterator;
import io.logbase.collections.BatchListReader;
import io.logbase.collections.BatchListWriter;
import io.logbase.collections.nativelists.IntList;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.IntSummaryStatistics;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class StringList implements BatchList<CharBuffer> {
  private final CharBuffer stringBuf;
  public final IntList lengthList;

  public StringList(IntSummaryStatistics stats) {
    this(stats.getMin(), stats.getMax(), stats.getCount(), stats.getSum());
  }

  public StringList(int min, int max, long count, long sum) {
    //tba remove (int)
    stringBuf = ByteBuffer.allocateDirect((int) sum * 2).asCharBuffer();
    lengthList = new BitPackIntList(min, max, count);
  }

  public CharBuffer getWriteBuffer() {
    return stringBuf.duplicate();
  }

  public CharBuffer getReadBuffer() {
    return stringBuf.asReadOnlyBuffer();
  }

  @Override
  public long size() {
    return lengthList.size();
  }

  @Override
  public BatchListIterator<CharBuffer> iterator(long maxIndex) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public BatchListReader<CharBuffer> reader(long maxIndex) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public BatchListWriter<CharBuffer> writer() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }
}