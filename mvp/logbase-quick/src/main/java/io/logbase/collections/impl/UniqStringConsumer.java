package io.logbase.collections.impl;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class UniqStringConsumer implements Consumer<CharBuffer> {

  private Set<CharBuffer> uniqValues = new TreeSet<CharBuffer>();
  private IntSummaryStatistics stats = new IntSummaryStatistics();

  @Override
  public void accept(CharBuffer value) {
    if(!uniqValues.contains(value)){
      uniqValues.add(value);
      stats.accept(value.length());
    }
  }

  public Iterator<CharBuffer> iterator() {
    return uniqValues.iterator();
  }

  public IntSummaryStatistics getStats(){
    return stats;
  }

}
