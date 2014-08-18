package io.logbase.collections.impl;

import io.logbase.collections.BatchIterator;

import java.nio.CharBuffer;
import java.util.IntSummaryStatistics;
import java.util.Iterator;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class StringList  extends BaseList<CharBuffer> {
  public final StringListBuffer listBuffer;
  private final CharBuffer stringBuf;
  public final String CHARSET = "UTF-8";

  StringList(Iterator<CharBuffer> source){
    IntSummaryStatistics stats = new IntSummaryStatistics();
    while(source.hasNext()){
      stats.accept(source.next().length());
    }
    listBuffer = new StringListBuffer(stats);
    stringBuf = listBuffer.getWriteBuffer();
  }

  public StringList(IntSummaryStatistics stats){
    listBuffer = new StringListBuffer(stats);
    stringBuf = listBuffer.getWriteBuffer();
  }

  @Override
  public void addAll(BatchIterator<CharBuffer> source){
    while(source.hasNext()){
      add(source.next());
    }
  }

  @Override
  public boolean add(CharBuffer value){
    listBuffer.lengthList.add(value.length());
    stringBuf.put(value);
    return true;
  }

  @Override
  public void addAll(CharBuffer[] values, int offset, int length){
    checkArgument(values!=null, "values can't be null");
    for(int i=0; i<values.length; i++){
      add(values[i]);
    }
  }

}
