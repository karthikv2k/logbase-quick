package io.logbase.collections;

import io.logbase.collections.impl.IntegerLinkedArrayList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Benchmark {
  public static void main(String[] args) {
    int testSize = 10 * 1000 * 1000;
    System.out.println("inserting " + testSize + " ints");
    IntegerLinkedArrayList intList = new IntegerLinkedArrayList(64 * 1024);
    long time = System.currentTimeMillis();
    for (int i = 0; i < testSize; i++) {
      intList.add(i);
    }
    System.out.println("Awesome list (ms) " + (System.currentTimeMillis() - time));
    List<Integer> genList = new LinkedList<Integer>();
    time = System.currentTimeMillis();
    for (int i = 0; i < testSize; i++) {
      genList.add(i);
    }
    System.out.println("LinkedList list (ms) " + (System.currentTimeMillis() - time));
    List<Integer> arrayList = new ArrayList<Integer>(testSize + 1);
    time = System.currentTimeMillis();
    for (int i = 0; i < testSize; i++) {
      arrayList.add(i);
    }
    System.out.println("ArrayList list (ms) " + (System.currentTimeMillis() - time));
    int readSize = 10000;
    System.out.println("reading " + readSize + " ints");
    System.out.println("Awesome list (ms) " + read(intList, readSize));
    //System.out.println("LinkedList list (ms) " + read(genList,readSize));
    System.out.println("ArrayList list (ms) " + read(arrayList, readSize));
  }

  private static long read(List<Integer> list, int readSize) {
    int length = list.size();
    int[] readIdx = new int[readSize];
    for (int i = 0; i < readIdx.length; i++) {
      readIdx[i] = (int) (Math.random() * length);
    }
    long time = System.currentTimeMillis();
    for (int i = 0; i < readIdx.length; i++) {
      if (readIdx[i] != list.get(readIdx[i])) {
        throw new RuntimeException("wrong read");
      }
    }
    return System.currentTimeMillis() - time;
  }

}
