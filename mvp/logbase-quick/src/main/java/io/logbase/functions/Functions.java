package io.logbase.functions;

/**
 * Created with IntelliJ IDEA.
 * User: Karthik
 */
public interface Functions {

  /**
   * Initialize the Function with a list of operands.
   * Operands can be - columns, scalar values ...
   *
   * @param operands
   */
  public void init(Object[] operands);

  public void execute();

  /**
   * Returns either scalar or vector objects as per the function definition
   * @return
   */
  public Object getOutput();

}
