package io.logbase.querying.optiq;

public interface Expression {

  public ExpressionNode getRootNode();

  public boolean isFullyExecuted();

  public Operation getNextOperation();

  public void storeLastOperationOutput(Object output);

  public Object getLastOperationOutput();

  public Expression copy();

}
