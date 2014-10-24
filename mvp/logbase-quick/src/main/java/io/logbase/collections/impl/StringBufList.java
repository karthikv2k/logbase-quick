package io.logbase.collections.impl;

import io.logbase.buffer.BufferFactory;
import io.logbase.collections.*;
import io.logbase.collections.nativelists.*;

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
    stringBuf = BufferFactory.newBufWithCharCapacity((int) stats.getSum()); //TBD remove (int)
    lengthList = IntListFactory.newReadOnlyList(stats, null, true);
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
    return primitiveIterator(maxIndex);
  }

  @Override
  public BatchListReader<CharBuffer> reader(long maxIndex) {
    return primitiveReader(maxIndex);
  }

  @Override
  public BatchListWriter<CharBuffer> writer() {
    return primitiveWriter();
  }

  @Override
  public long memSize() {
    return lengthList.memSize() + stringBuf.capacity();
  }

  @Override
  public StringListIterator primitiveIterator(long maxIndex) {
    return new StringBufListIterator(this, maxIndex);
  }

  @Override
  public StringListReader primitiveReader(long maxIndex) {
    return new StringBufListReader(getReadBuffer(), lengthList.primitiveIterator(maxIndex));
  }

  @Override
  public StringListWriter primitiveWriter() {
    return new StringBufListWriter(this);
  }
}