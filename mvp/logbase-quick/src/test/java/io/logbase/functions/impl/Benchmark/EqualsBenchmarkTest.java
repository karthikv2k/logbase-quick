package io.logbase.functions.impl.Benchmark;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import io.logbase.collections.impl.BitsetList;
import io.logbase.collections.nativelists.BooleanList;
import io.logbase.column.Column;
import io.logbase.column.ColumnFactory;
import io.logbase.exceptions.UnsupportedFunctionPredicateException;
import io.logbase.functions.predicates.LBPredicate;
import io.logbase.functions.predicates.PredicateType;
import io.logbase.functions.predicates.impl.Equals;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Kousik on 28/10/14.
 */
public class EqualsBenchmarkTest extends AbstractBenchmark{
  private static int rowCount = 10 * 1000 * 1000;
  private LBPredicate predicate;
  private static Column intColumn, floatColumn, doubleColumn, longColumn, bitpackColumn;

  @BeforeClass
  public static void init() {
    intColumn = ColumnGenerator.getColumn(Integer.class, rowCount);
    floatColumn = ColumnGenerator.getColumn(Float.class, rowCount);
    doubleColumn = ColumnGenerator.getColumn(Double.class, rowCount);
    longColumn = ColumnGenerator.getColumn(Long.class, rowCount);
    bitpackColumn = ColumnFactory.createReadOnlyColumn(intColumn);
  }

  @BenchmarkOptions(benchmarkRounds = 3, warmupRounds = 2)
  @Test
  public void intTest() throws UnsupportedFunctionPredicateException {
    BooleanList list = new BitsetList();
    predicate = new Equals(5, PredicateType.INTPREDICATE);
    intColumn.execute(predicate, list);
  }

  @BenchmarkOptions(benchmarkRounds = 3, warmupRounds = 2)
  @Test
  public void bitpackIntTest() throws UnsupportedFunctionPredicateException {
    BooleanList list = new BitsetList();
    predicate = new Equals(5, PredicateType.INTPREDICATE);
    bitpackColumn.execute(predicate, list);
  }

  @BenchmarkOptions(benchmarkRounds = 3, warmupRounds = 2)
  @Test
  public void longTest() throws UnsupportedFunctionPredicateException {
    BooleanList list = new BitsetList();
    predicate = new Equals((long)5, PredicateType.LONGPREDICATE);
    longColumn.execute(predicate, list);
  }

  @BenchmarkOptions(benchmarkRounds = 3, warmupRounds = 2)
  @Test
  public void floatTest() throws UnsupportedFunctionPredicateException {
    BooleanList list = new BitsetList();
    predicate = new Equals((float)5, PredicateType.FLOATPREDICATE);
    floatColumn.execute(predicate, list);
  }

  @BenchmarkOptions(benchmarkRounds = 3, warmupRounds = 2)
  @Test
  public void doubleTest() throws UnsupportedFunctionPredicateException {
    BooleanList list = new BitsetList();
    predicate = new Equals((double)5, PredicateType.DOUBLEPREDICATE);
    doubleColumn.execute(predicate, list);
  }
}
