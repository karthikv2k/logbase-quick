package io.logbase.functions;

import io.logbase.functions.impl.AND;
import io.logbase.functions.impl.Equals;
import io.logbase.functions.impl.OR;
import io.logbase.functions.impl.Search;

/**
 * Created by Kousik on 30/09/14.
 */
public class FunctionFactory<E> {

  public enum FunctionOperators {
    AND,
    OR,
    EQUALS,
    SEARCH
  }

  public Function createFunction(FunctionOperators operator) {
    switch (operator) {
      case AND:
        return new AND();
      case OR:
        return new OR();
      case SEARCH:
        return new Search();
      case EQUALS:
        return new Equals<E>();
      default:
        assert(false);
    }
    return null;
  }
}
