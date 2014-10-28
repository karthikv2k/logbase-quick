package io.logbase.functions.Predicates.impl;

import io.logbase.functions.Predicates.*;

/**
 * Created by Kousik on 27/10/14.
 */
public class GreaterThan implements FunctionPredicate {
  private int intValue;
  private float floatValue;
  private double doubleValue;
  private long longValue;
  private PredicateType predicateType;

  public GreaterThan(int value, PredicateType predicateType) {
    assert (predicateType == PredicateType.INTPREDICATE);
    this.predicateType = predicateType;

    this.intValue = value;
  }

  public GreaterThan(float value, PredicateType predicateType) {
    assert (predicateType == PredicateType.FLOATPREDICATE);
    this.predicateType = predicateType;

    this.floatValue = value;
  }

  public GreaterThan(double value, PredicateType predicateType) {
    assert (predicateType == PredicateType.DOUBLEPREDICATE);
    this.predicateType = predicateType;

    this.doubleValue = value;
  }

  public GreaterThan(long value, PredicateType predicateType) {
    assert (predicateType == PredicateType.LONGPREDICATE);
    this.predicateType = predicateType;

    this.longValue = value;
  }

  @Override
  public boolean apply(double value) {
    return value > this.doubleValue;
  }

  @Override
  public boolean apply(float value) {
    return value > this.floatValue;
  }

  @Override
  public boolean apply(int value) {
    return value > this.intValue;
  }

  @Override
  public boolean apply(long value) {
    return value > this.longValue;
  }
}
