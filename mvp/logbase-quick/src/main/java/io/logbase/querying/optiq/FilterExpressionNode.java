package io.logbase.querying.optiq;

import io.logbase.querying.optiq.ExpressionNode;
import io.logbase.querying.optiq.ExpressionNodeType;

public class FilterExpressionNode implements ExpressionNode {

  private ExpressionNodeType type;
  private Object value;
  private FilterExpressionNode leftNode;
  private FilterExpressionNode rightNode;

  public FilterExpressionNode(ExpressionNodeType type, Object value) {
    this.type = type;
    this.value = value;
  }

  public void setLeftNode(FilterExpressionNode leftNode) {
    this.leftNode = leftNode;
  }

  public void setRightNode(FilterExpressionNode rightNode) {
    this.rightNode = rightNode;
  }

  @Override
  public ExpressionNodeType getType() {
    return type;
  }

  public void setType(ExpressionNodeType type) {
    this.type = type;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public FilterExpressionNode getLeftNode() {
    return leftNode;
  }

  public FilterExpressionNode getRightNode() {
    return rightNode;
  }

  @Override
  public ExpressionNode[] getChildren() {
    ExpressionNode[] children = null;
    if (rightNode != null)
      children = new ExpressionNode[] { leftNode, rightNode };
    else if (leftNode != null)
      children = new ExpressionNode[] { leftNode };
    return children;
  }

}
