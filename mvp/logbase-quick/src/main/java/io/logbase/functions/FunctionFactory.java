package io.logbase.functions;

import io.logbase.functions.impl.*;

/**
 * Created by Kousik on 30/09/14.
 */
public class FunctionFactory<E> {

  public enum FunctionOperator {
    AND,
    OR,
    EQUALS,
    SEARCH,
    GREATERTHAN,
    LESSERTHAN,
    GREATERTHANEQUALS,
    LESSERTHANEQUALS,
    NOTEQUALS
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
      case GREATERTHAN:
        return new GreaterThan<E>(operands);
      case GREATERTHANEQUALS:
        return new GreaterThanEquals<E>(operands);
      case LESSERTHAN:
        return new LesserThan<E>(operands);
      case LESSERTHANEQUALS:
        return new LesserThanEquals<E>(operands);
      case NOTEQUALS:
        return new NotEquals<E>(operands);
      default:
        assert(false);
    }
    return null;
  }
}
