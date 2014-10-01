package io.logbase.functions;

import io.logbase.functions.impl.AND;
import io.logbase.functions.impl.Equals;
import io.logbase.functions.impl.OR;
import io.logbase.functions.impl.Search;

/**
 * Created by Kousik on 30/09/14.
 */
public class FunctionFactory<E> {

  public enum FunctionOperator {
    AND,
    OR,
    EQUALS,
    SEARCH
  }

  public Function createFunction(FunctionOperator operator, Object[] operands) {
    switch (operator) {
      case AND:
        return new AND(operands);
      case OR:
        return new OR(operands);
      case SEARCH:
        return new Search(operands);
      case EQUALS:
        return new Equals<E>(operands);
      default:
        assert(false);
    }
    return null;
  }
}
