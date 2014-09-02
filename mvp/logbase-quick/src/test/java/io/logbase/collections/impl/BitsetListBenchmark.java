package io.logbase.collections.impl;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;

import io.logbase.collections.BatchListIterator;
import io.logbase.collections.BatchListWriter;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Kousik on 02/09/14.
 */
public class BitsetListBenchmark extends AbstractBenchmark{
  private BatchListWriter writer;
  private boolean[] booleanP;
  private Boolean[] booleanO;
  private BitsetList list;
  private int iterations = 10;
  private int testDataSize = 1000 * 1000 * 10;

  public BitsetListBenchmark(){
    list = new BitsetList(testDataSize);
    boolean[] testData = new boolean[testDataSize];
    writer = list.writer();
    init(testData);
  }

  private void init(boolean[] testData){
    this.booleanP = testData;
    booleanO = new Boolean[booleanP.length];
    for(int i=0;i<booleanP.length;i++){
      booleanO[i] = booleanP[i];
    }
  }

  @BenchmarkOptions(benchmarkRounds = 10, warmupRounds = 5)
  @Test
  @Before
  public void writeBatach(){
    for(int i=0; i<iterations; i++){
      writer.add(booleanP, 0, booleanP.length);
    }
  }

  @BenchmarkOptions(benchmarkRounds = 10, warmupRounds = 5)
  @Test
  public void write(){
    for(int i=0; i<iterations; i++){
      for(int j=0; j< booleanO.length;j++){
        writer.add(booleanO[j]);
      }
    }
  }

  @BenchmarkOptions(benchmarkRounds = 10, warmupRounds = 5)
  @Test
  public void iterate(){
    BatchListIterator itr = list.iterator((long)testDataSize);
    while(itr.hasNext()) {
      itr.next(booleanP, 0, booleanP.length);
    }
  }
}
