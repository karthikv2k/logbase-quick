package io.logbase.collections.impl;

import io.logbase.buffer.BufferFactory;
import io.logbase.collections.BatchList;
import io.logbase.collections.BatchListIterator;
import io.logbase.collections.BatchListReader;
import io.logbase.collections.BatchListWriter;
import io.logbase.collections.nativelists.IntList;
import io.logbase.collections.nativelists.StringList;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.IntSummaryStatistics;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class StringBufList implements StringList {
  private final ByteBuffer stringBuf;
  public final IntList lengthList;

  public StringBufList(IntSummaryStatistics stats) {
    this(stats.getMin(), stats.getMax(), stats.getCount(), stats.getSum());
  }

  public StringBufList(int min, int max, long count, long sum) {
    //tba remove (int)
    stringBuf = BufferFactory.newBufWithCharCapacity((int) sum);
    lengthList = new BitPackIntList(min, max, count);
  }

  public CharBuffer getWriteBuffer() {
    return stringBuf.duplicate().asCharBuffer();
  }

  public CharBuffer getReadBuffer() {
    return stringBuf.asReadOnlyBuffer().asCharBuffer();
  }

  @Override
  public long size() {
    return lengthList.size();
  }

  @Override
  public BatchListIterator<CharBuffer> iterator(long maxIndex) {
    return new StringBufListIterator(this, maxIndex);
  }

  @Override
  public BatchListReader<CharBuffer> reader(long maxIndex) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public BatchListWriter<CharBuffer> writer() {
    return new StringBufListWriter(this);
  }

  @Override
  public Class<CharBuffer> type(){
    return CharBuffer.class;
  }
}