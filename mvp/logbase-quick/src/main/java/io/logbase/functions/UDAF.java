package io.logbase.functions;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public interface UDAF {

  public void apply(Object[] operands);

  public Object result();

  public void add(UDAF udaf);

}