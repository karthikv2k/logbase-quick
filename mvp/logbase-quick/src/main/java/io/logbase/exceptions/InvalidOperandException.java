package io.logbase.exceptions;

public class InvalidOperandException extends Exception {

  private static final long serialVersionUID = 1L;
  private String message = null;

  public InvalidOperandException() {
    super();
  }

  public InvalidOperandException(String message) {
    super(message);
    this.message = message;
  }

  public InvalidOperandException(Throwable cause) {
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
