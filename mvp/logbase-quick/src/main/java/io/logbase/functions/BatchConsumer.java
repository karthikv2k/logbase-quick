package io.logbase.functions;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public interface BatchConsumer {

  public void accept(Object values, int offset, int length);

}
