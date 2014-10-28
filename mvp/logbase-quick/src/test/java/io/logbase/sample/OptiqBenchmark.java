package io.logbase.sample;

import io.logbase.querying.optiq.LBSchema;
import io.logbase.querying.optiq.QueryExecutor;
import io.logbase.table.TableIterator;
import io.logbase.view.TestView;
import io.logbase.view.View;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Loads and queries a twitter stream file.
 *
 * @author Abishek Baskaran
 */
public class OptiqBenchmark {

  private View view;

  static final Logger logger = LoggerFactory
      .getLogger(OptiqBenchmark.class);

  @Before
  public void init() {


    String fileName = "/Volumes/Data/Downloads/slim2.json";
    Set<String> cols = new LinkedHashSet<>();
    cols.add("text.String");
    cols.add("created_at.String");
    cols.add("id.Double");
    logger.info("start loading json");
    view = new TestView(fileName, cols, 10*1000*1000);
    logger.info("loaded json");
  }

  /**
   * Test case without query push down
   * 
   */
  @Test
  public void testTwitterFileSample() {

    logger.info("Running a Twitter File sample...");
    TableIterator tableIterator = view.getIterator();

    logger.debug("Columns in table: "
      + Arrays.toString(tableIterator.getColumnNames()));
    LBSchema lbSchema = new LBSchema("TEST");
    lbSchema.addAsTable("TWITTER", view);
    QueryExecutor queryExec = new QueryExecutor(lbSchema);
    //String sql = "SELECT \"text.String\", \"created_at.String\" "
      //  + " from \"TEST\".\"TWITTER\"";
    String sql = "SELECT \"created_at.String\",\"text.String\", count(distinct \"text.String\") as cnt, count(1) as cnt1"
      + " from \"TEST\".\"TWITTER\" group by \"created_at.String\",\"text.String\" order by cnt1 desc";
    int resultCount = 0;
    StringBuilder buf = new StringBuilder(1000);

    long time = System.currentTimeMillis();
    try {
    ResultSet results = queryExec.execute(sql);
      logger.info("Time taken " + (System.currentTimeMillis()-time));
      int N = results.getMetaData().getColumnCount();
      while (results.next()) {
        resultCount++;
        //if(resultCount>100) break;
       /* for(int i=1; i<=N;i++){
          buf.append(results.getString(i) + ", ");
        }
        logger.info(buf.toString());
        buf.setLength(0);          */
      }
      logger.info("cnt is: " + resultCount);
    } catch (SQLException e) {
      e.printStackTrace();
      logger.error("Error while executing optiq query: " + sql);
    }
    logger.info("Time taken " + (System.currentTimeMillis()-time));
    logger.info("Result count: " + resultCount);
  }

}
