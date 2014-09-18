package io.logbase.collections.impl;

import io.logbase.collections.BatchList;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.nio.CharBuffer;
import java.util.IntSummaryStatistics;

/**
 * Created by Kousik on 18/09/14.
 */
public class StringListTest implements BatchListFactory {
  public static String[] testData;
  private BufferedReader br;
  private int count = 0;
  private IntSummaryStatistics stats;
  private String line;
  private final int testDataCount = 1200; //stringTestData has ~1200 entries

  @Before
  public void setUp() throws Exception {
    testData = new String[testDataCount];
    stats = new IntSummaryStatistics();
    String fileName = "stringTestData";
    URL url = ClassLoader.getSystemResource(fileName);
    br = new BufferedReader(new FileReader(url.getFile()));

    while (((line = br.readLine()) != null) && (count < testDataCount)) {
      testData[count] = new String(line);
      stats.accept(line.length());
      count++;
    }
    br.close();
  }

  @Test
  public void testList(){
    StringListTestSuite tester = new StringListTestSuite(this, testData);
    tester.testList();
  }

  @Override
  public BatchList<CharBuffer> newInstance() {
    return new StringList(stats);
  }
}
