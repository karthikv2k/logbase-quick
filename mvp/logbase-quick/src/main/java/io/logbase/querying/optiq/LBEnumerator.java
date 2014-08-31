package io.logbase.querying.optiq;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import io.logbase.view.View;
import net.hydromatic.linq4j.Enumerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * LBEnumerator converts a Logbase view into a row iterator.
 *
 * @author Abishek Baskaran
 */
public class LBEnumerator implements Enumerator<Object> {

  static final Logger logger = LoggerFactory.getLogger(LBEnumerator.class);
  private Object current;
  private Iterator<Object[]> rowIterator;

  /**
   * Constructor - forms the iterator.
   *
   * @param view The logbase view.
   */
  public <E> LBEnumerator(View view) {
    String[] columnNames = view.getIterator().getColumnNames();
    List<CharSequence> allProjects = new ArrayList<CharSequence>();
    for (String columnName : columnNames) {
      if (LBTable.getJavaColumnType(columnName) != null) {
        allProjects.add(columnName);
      }
    }
    logger.debug("No. of columns added to filter: " + allProjects.size());
    Predicate<CharSequence> allColumnFilter = Predicates.in(allProjects);
    rowIterator = view.getIterator(allColumnFilter);

  }

  /**
   * This constructor is for a smart table. Projection fields will tell what
   * columns or fields are required.
   *
   * @param view             The Logbase view
   * @param projectionFields The fields or columns in the select clause of query.
   */
  public <E> LBEnumerator(View view, List<String> projection, String filter) {
    // TODO Pass filter to get iterator
    if (projection == null) {
      String[] columnNames = view.getIterator().getColumnNames();
      List<CharSequence> allProjects = new ArrayList<CharSequence>();
      for (String columnName : columnNames) {
        if (LBTable.getJavaColumnType(columnName) != null) {
          allProjects.add(columnName);
        }
      }
      logger.debug("No. of columns added to filter: " + allProjects.size());
      Predicate<CharSequence> allColumnFilter = Predicates.in(allProjects);
      rowIterator = view.getIterator(allColumnFilter);
    } else {
      List<CharSequence> selectProjects = new ArrayList<CharSequence>();
      for (String p : projection) {
        if (LBTable.getJavaColumnType(p) != null) {
          selectProjects.add(p);
        }
      }
      logger.debug("No. of select columns added to filter: "
        + selectProjects.size());
      Predicate<CharSequence> selectColumnFilter = Predicates
        .in(selectProjects);
      rowIterator = view.getIterator(selectColumnFilter);
    }
  }

  @Override
  public void close() {
    // Nothing to do
  }

  @Override
  public Object current() {
    if (current == null) {
      this.moveNext();
    }
    return current;
  }

  @Override
  public boolean moveNext() {
    if (this.rowIterator.hasNext()) {
      final Object[] row = this.rowIterator.next();
      current = row;
      return true;
    } else {
      current = null;
      return false;
    }
  }

  @Override
  public void reset() {
    throw new UnsupportedOperationException();
  }

}

