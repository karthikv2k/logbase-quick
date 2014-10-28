package io.logbase.functions.Predicates.impl;

import io.logbase.functions.Predicates.FunctionPredicate;
import io.logbase.functions.Predicates.PredicateType;

/**
 * Created by Kousik on 28/10/14.
 */
public class Search implements FunctionPredicate {

  String searchString;
  PredicateType predicateType;

  public Search(String value, PredicateType predicateType) {
    assert(predicateType == PredicateType.STRINGPREDICATE);
    this.predicateType = predicateType;
    this.searchString = value;
  }

  @Override
  public boolean apply(String value) {
    return searchString.contains(value);
  }
}
