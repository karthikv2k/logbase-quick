package io.logbase.exceptions;

public class IllegalQueryException extends Exception {

  private static final long serialVersionUID = 1L;
  private String message = null;

  public IllegalQueryException() {
    super();
  }

  public IllegalQueryException(String message) {
    super(message);
    this.message = message;
  }

  public IllegalQueryException(Throwable cause) {
    super(cause);
  }

  @Override
  public String toString() {
    return message;
  }

  @Override
  public String getMessage() {
    return message;
  }

}
