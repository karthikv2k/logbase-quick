package io.logbase.exceptions;

public class ConsumerInitException extends Exception {

  private static final long serialVersionUID = 1L;
  private String message = null;

  public ConsumerInitException() {
    super();
  }

  public ConsumerInitException(String message) {
    super(message);
    this.message = message;
  }

  public ConsumerInitException(Throwable cause) {
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
