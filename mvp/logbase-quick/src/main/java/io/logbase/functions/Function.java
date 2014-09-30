package io.logbase.functions;

import io.logbase.exceptions.InvalidOperandException;

/**
 * Created with IntelliJ IDEA.
 * User: Karthik
 */
public interface Function {

  /**
   * Returns either scalar or vector objects as per the function definition
   * @return
   */
  public Object execute() throws InvalidOperandException;
}
