package io.logbase.querying.optiq;

import io.logbase.datamodel.View;

import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.hydromatic.linq4j.Enumerator;

/**
 * LBEnumerator converts a Logbase view into a row iterator.
 * 
 * @author Abishek Baskaran
 *
 */
public class LBEnumerator implements Enumerator<Object> {

  static final Logger logger = LoggerFactory.getLogger(LBEnumerator.class);
  private Object current;
  private Iterator<Object[]> rowIterator;

  /**
   * Constructor - forms the iterator.
   * 
   * @param view
   *          The logbase view.
   */
  public <E> LBEnumerator(View view) {
    rowIterator = view.getIterator();
    logger.debug("Created an iterator for the enumerator");
  }

  /**
   * This constructor is for a smart table. Projection fields will tell what
   * columns or fields are required.
   * 
   * @param view
   *          The Logbase view
   * @param projectionFields
   *          The fields or columns in the select clause of query.
   */
  public <E> LBEnumerator(View view, Set<String> projectFieldNames) {
    // TODO filter for projection by pushing down projected fields
    rowIterator = view.getIterator();
    logger.debug("Created an iterator for the enumerator with projects");
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

  public static int[] identityList(int fieldCount) {
    int[] integers = new int[fieldCount];
    for (int i = 0; i < fieldCount; i++) {
      integers[i] = i;
    }
    return integers;
  }

}

