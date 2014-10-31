package io.logbase.functions.predicates.impl;

import io.logbase.functions.predicates.LBPredicate;
import io.logbase.functions.predicates.PredicateType;

/**
 * Created by Kousik on 28/10/14.
 */
public class Search implements LBPredicate {

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
