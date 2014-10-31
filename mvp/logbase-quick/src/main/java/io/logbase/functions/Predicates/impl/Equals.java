package io.logbase.functions.predicates.impl;

import io.logbase.functions.predicates.*;

/**
 * Created by Kousik on 27/10/14.
 */
public class Equals implements LBPredicate {
  private int intValue;
  private float floatValue;
  private double doubleValue;
  private long longValue;
  private PredicateType predicateType;

  public Equals(int value, PredicateType predicateType) {
    assert(predicateType == PredicateType.INTPREDICATE);
    this.predicateType = predicateType;

    this.intValue = value;
  }

  public Equals(float value, PredicateType predicateType) {
    assert(predicateType == PredicateType.FLOATPREDICATE);
    this.predicateType = predicateType;

    this.floatValue = value;
  }

  public Equals(double value, PredicateType predicateType) {
    assert(predicateType == PredicateType.DOUBLEPREDICATE);
    this.predicateType = predicateType;

    this.doubleValue = value;
  }

  public Equals(long value, PredicateType predicateType) {
    assert(predicateType == PredicateType.LONGPREDICATE);
    this.predicateType = predicateType;

    this.longValue = value;
  }

  @Override
  public boolean apply(double value) {
    return value == this.doubleValue;
  }

  @Override
  public boolean apply(float value) {
    return value == this.floatValue;
  }

  @Override
  public boolean apply(int value) {
    return value == this.intValue;
  }

  @Override
  public boolean apply(long value) {
    return value == this.longValue;
  }
}
