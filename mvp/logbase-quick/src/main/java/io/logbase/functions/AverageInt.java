package io.logbase.functions;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class AverageInt implements UDAF {
  long sum = 0;
  long total = 0;

  @Override
  public void apply(Object[] operands) {
    checkArgument(operands.length == 2, "More than one parameter given.");
    checkArgument(operands[0] instanceof int[], "More than one parameter given.");
    int[] values = (int[]) operands[0];
    byte[] isNull = (byte[]) operands[1];
    for (int i = 0; i < values.length; i++) {
      sum += values[i];
      total += 1 * isNull[i];
    }
  }

  @Override
  public Object result() {
    return ((double) sum) / total;
  }

  @Override
  public void add(UDAF udaf) {
    //To change body of implemented methods use File | Settings | File Templates.
  }
}
