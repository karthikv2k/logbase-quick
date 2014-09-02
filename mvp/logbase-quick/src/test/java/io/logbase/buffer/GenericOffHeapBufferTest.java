package io.logbase.buffer;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import io.logbase.collections.impl.DataGen;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class GenericOffHeapBufferTest  extends AbstractBenchmark {
  public static IntBuffer intBuffer = ByteBuffer.allocateDirect(1000*1000*1000*2).asIntBuffer();
  public static int temp;
  public int[] rndData = (int[]) DataGen.genRndData(int.class, 1024*100);
  @BeforeClass
  public static void init(){
  }

  @Test
  public void intBufferWrite() throws Exception {
    intBuffer.rewind();
    int i =0;
    while(intBuffer.hasRemaining()){
      intBuffer.put(i);
      i++;
    }
  }

  @Test
  public void intBufferWriteBatch() throws Exception {
    intBuffer.rewind();
    int i =0;
    while(i+rndData.length<intBuffer.capacity()){
      intBuffer.put(rndData, 0, rndData.length);
      i+=rndData.length;
    }
    //System.out.println(i);
  }

  @Test
  public void intBufferRead() throws Exception {
    intBuffer.rewind();
    int i = 0, j=0;
    while(intBuffer.hasRemaining()){
      i = i + intBuffer.get();
      j++;
    }
    //System.out.println(i + " " + j);
  }

  @Test
  public void intBufferReadBatch() throws Exception {
    intBuffer.rewind();
    int i = 0, j=0;
    while(intBuffer.hasRemaining()){
      intBuffer.get(rndData, 0, Math.min(rndData.length,intBuffer.remaining()));
      j++;
    }
    //System.out.println(i + " " + j);
  }

}
