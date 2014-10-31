package io.logbase.functions.predicates;

import io.logbase.collections.nativelists.BooleanList;
import io.logbase.exceptions.UnsupportedFunctionPredicateException;

/**
 * Created by Kousik on 27/10/14.
 */
public interface PredicateExecutor {
  /**
   * Evaluates the predicate for every row and stores the result in the given list
   *
   * @param predicate
   * @param list
   * @throws UnsupportedOperationException
   */
  public default void execute(LBPredicate predicate, BooleanList list)
    throws UnsupportedFunctionPredicateException {
    throw new UnsupportedFunctionPredicateException();
  };
}
