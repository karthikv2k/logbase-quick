package io.logbase.collections.impl;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;
import io.logbase.collections.BatchListWriter;
import io.logbase.collections.nativelists.IntList;
import io.logbase.collections.nativelists.IntListWriter;
import io.logbase.utils.GlobalConfig;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class IntLisBenchmarkTest extends AbstractBenchmark {
  private IntListWriter writer;
  private int[] intP;
  private Integer[] intO;
  private int iterations;

  public IntLisBenchmarkTest(){
    IntList list = new IntegerArrayList();
    int[] testData = (int[]) DataGen.genRndData(int.class, GlobalConfig.DEFAULT_READ_BUFFER_SIZE);
    init(list.primitiveWriter(), testData, 1000);
  }

  private void init(IntListWriter writer, int[] testData, int iterations){
    this.writer = writer;
    this.intP = testData;
    intO = new Integer[intP.length];
    for(int i=0;i<intP.length;i++){
      intO[i] = intP[i];
    }
    this.iterations = iterations;
  }

  @BenchmarkOptions(benchmarkRounds = 10, warmupRounds = 5)
  @Test
  public void writeBatchPrimitive(){
    for(int i=0; i<iterations; i++){
      writer.addPrimitive(intP, 0, intP.length);
    }
  }

  @BenchmarkOptions(benchmarkRounds = 10, warmupRounds = 5)
  @Test
  public void write(){
    for(int i=0; i<iterations; i++){
      for(int j=0; j< intO.length;j++){
        writer.add(intO[j]);
      }
    }
  }

  @BenchmarkOptions(benchmarkRounds = 10, warmupRounds = 5)
  @Test
  public void writePrimitive(){
    for(int i=0; i<iterations; i++){
      for(int j=0; j< intP.length;j++){
        writer.addPrimitive(intP[j]);
      }
    }
  }

}
