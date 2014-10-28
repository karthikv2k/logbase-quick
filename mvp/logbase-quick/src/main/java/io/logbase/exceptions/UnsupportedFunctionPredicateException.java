package io.logbase.exceptions;

/**
 * Created by Kousik on 28/10/14.
 */
public class UnsupportedFunctionPredicateException extends Exception {

  private static final long serialVersionUID = 1L;
  private String message = null;

  public UnsupportedFunctionPredicateException() {
    super();
  }

  public UnsupportedFunctionPredicateException(String message) {
    super(message);
    this.message = message;
  }

  public UnsupportedFunctionPredicateException(Throwable cause) {
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
