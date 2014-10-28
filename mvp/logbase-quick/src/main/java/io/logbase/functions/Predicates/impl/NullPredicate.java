package io.logbase.functions.Predicates.impl;

import io.logbase.functions.Predicates.*;

/**
 * Created by Kousik on 27/10/14.
 */
public class NullPredicate implements FunctionPredicate{
  @Override
  public boolean apply(double value) {
    return false;
  }

  @Override
  public boolean apply(float value) {
    return false;
  }

  @Override
  public boolean apply(int value) {
    return false;
  }

  @Override
  public boolean apply(long value) {
    return false;
  }

  @Override
  public boolean apply(String value) {
    return false;
  }

  @Override
  public boolean isNull() {
    return true;
  }
}
