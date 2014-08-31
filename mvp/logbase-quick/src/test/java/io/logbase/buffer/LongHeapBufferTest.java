package io.logbase.buffer;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class LongHeapBufferTest {
  @Test
  public void testGetLong() throws Exception {
    /*int testSize = 100 * 1000 * 1000;
    System.out.println("inserting " + testSize + " ints");
    LongBuffer lb = LongBuffer.allocate(testSize);
    long time = System.currentTimeMillis();
    for (int i = 0; i < testSize; i++) {
      lb.put(i, i);
    }
    System.out.println("LongHeapBufferJava list (ms) " + (System.currentTimeMillis() - time));
    System.out.println("inserting " + testSize + " ints");
    LongHeapBuffer lbh = new LongHeapBuffer(testSize);
    time = System.currentTimeMillis();
    for (int i = 0; i < testSize; i++) {
      lbh.setLong(i, i);
    }
    System.out.println("LongHeapBufferLB list (ms) " + (System.currentTimeMillis() - time));
    Buffer buf = new GenericOffHeapBuffer(testSize*8);
    time = System.currentTimeMillis();
    for (int i = 0; i < testSize; i++) {
      buf.setLong(i, i);
    }
    System.out.println("LongHeapBufferLBOffHeap list (ms) " + (System.currentTimeMillis() - time));
    time = System.currentTimeMillis();
    long x = 12;
    for (int i = 0; i < testSize; i++) {
      x = x & buf.getLong(i);
    }
    System.out.println("LongHeapBufferLBOffHeap list (ms) " + (System.currentTimeMillis() - time) + " " + x);
    LongBuffer lbj = ByteBuffer.allocateDirect(testSize*8).asLongBuffer();
    time = System.currentTimeMillis();
    for (int i = 0; i < testSize; i++) {
      lbj.put(i, i);
    }
    System.out.println("LongHeapBufferLBOffHeap list (ms) " + (System.currentTimeMillis() - time) + " " + x);
    long[] holder = new long[testSize];
    time = System.currentTimeMillis();
    for (int i = 0; i < testSize; i++) {
      holder[i] =  i;
    }
    System.out.println("LongHeapBufferLBOffHeap list (ms) " + (System.currentTimeMillis() - time) + " " + holder[100]);
    */
  }
}
