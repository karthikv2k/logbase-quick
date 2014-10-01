package io.logbase.querying.optiq;

public interface ExpressionNode {

  public ExpressionNodeType getType();

  public Object getValue();

  public ExpressionNode[] getChildren();

}
