package io.logbase.functions.impl.Benchmark;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkHistoryChart;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;
import com.carrotsearch.junitbenchmarks.annotation.LabelType;
import com.carrotsearch.junitbenchmarks.h2.H2Consumer;

import io.logbase.collections.impl.BitsetList;
import io.logbase.collections.nativelists.BooleanList;
import io.logbase.column.Column;
import io.logbase.column.ColumnFactory;
import io.logbase.functions.Function;
import io.logbase.functions.FunctionFactory;
import io.logbase.functions.Predicates.FunctionPredicate;
import io.logbase.functions.Predicates.PredicateType;
import io.logbase.functions.Predicates.impl.Search;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;

/**
 * Created by Kousik on 08/10/14.
 */

@BenchmarkHistoryChart(filePrefix = "./results/search-benchmark-history-chart",
  labelWith = LabelType.CUSTOM_KEY, maxRuns = 20)

public class SearchBenchmarkTest extends AbstractBenchmark {
  private File dbFile= new  File(System.getProperty("jub.db.file"));
  private static Column column;

  @Rule
  public TestRule benchmarkRun = new BenchmarkRule(new H2Consumer(dbFile));

  public static void setUp(Column column) throws Exception {
    String line;
    int testDataCount = 1200; //stringTestData has ~1200 entries, ~70K of test data
    int iteration = 1000 * 8; //populate the column so many times to get ~500M of test data
    int count = 0;
    String [] testData = new String[testDataCount];

    //Load the test data from file
    String fileName = "stringTestData";
    URL url = ClassLoader.getSystemResource(fileName);
    BufferedReader br = new BufferedReader(new FileReader(url.getFile()));

    while (((line = br.readLine()) != null) && (count < testDataCount)) {
      testData[count] = new String(line);
      count++;
    }
    br.close();

    // Populate the column with test data
    for (int i=0 ; i<iteration; i++) {
      for(int j=0; j<count; j++) {
        if (testData[j] != null) {
          column.append(testData[j], i * count + j);
        }
      }
    }
  }

  @BeforeClass
  public static void baseSearchInit() throws Exception{
    column = ColumnFactory.createAppendOnlyColumn(String.class, "Test column", 0);
    setUp(column);
  }

  @BenchmarkOptions(benchmarkRounds = 3, warmupRounds = 2)
  @Ignore
  public void baseSearch() throws Exception{
    FunctionFactory factory = new FunctionFactory();
    Object[] operands = {column, "cover myself"};
    Function func = factory.createFunction(
      FunctionFactory.FunctionOperator.SEARCH, operands);
    func.execute();
  }

  @BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0)
  @Ignore
  public void predicateSearch() throws Exception {
    FunctionPredicate predicate = new Search("cover", PredicateType.STRINGPREDICATE);
    BooleanList list = new BitsetList();
    column.execute(predicate, list);
  }
}
