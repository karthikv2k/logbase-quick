package io.logbase.collections.impl;

import io.logbase.collections.BatchIterator;
import io.logbase.collections.BatchList;

import java.nio.CharBuffer;
import java.util.IntSummaryStatistics;
import java.util.Iterator;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class StringList implements BatchList<CharBuffer> {
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
  public BatchIterator<CharBuffer> batchIterator(long maxIndex) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public boolean close() {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public long size() {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public boolean primitiveTypeSupport() {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void add(CharBuffer value){
    listBuffer.lengthList.add(value.length());
    stringBuf.put(value);
  }

  @Override
  public void addPrimitiveArray(Object values, int offset, int length){
    checkArgument(values instanceof CharBuffer[],"values should be instance of CharBuffer[].");
    checkArgument(values!=null, "values can't be null");
    CharBuffer[] valueArray = (CharBuffer[]) values;
    for(int i=0; i<valueArray.length; i++){
      add(valueArray[i]);
    }
  }

}
