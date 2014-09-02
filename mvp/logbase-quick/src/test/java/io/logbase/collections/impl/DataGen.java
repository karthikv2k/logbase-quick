package io.logbase.collections.impl;

import java.lang.reflect.Array;
import java.nio.CharBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class DataGen {

  public static Object genRndData(Class type, int count){
    Object buffer = Array.newInstance(type, count);
    for(int i=0; i<count; i++){
      Array.set(buffer, i, rand(type));
    }
    return buffer;
  }

  public static Object rand(Class type){
    double rnd = Math.random();

    if(type.equals(int.class)){
      return (int)((rnd-0.5) * ((long)Integer.MAX_VALUE - Integer.MIN_VALUE));
    } else if(type.equals(long.class)){
      return (long)((rnd-0.5) * ((Long.MAX_VALUE-1) - Long.MIN_VALUE));
    } else if(type.equals(boolean.class)){
      return rnd>0.5 ? true : false;
    } else if(type.equals(CharBuffer.class)){
      return CharBuffer.wrap(Double.toString(rnd));
    } else if(type.equals(float.class)){
      return (float) rnd;
    } else if(type.equals(double.class)){
      return rnd;
    }

    throw new UnsupportedOperationException();

  }

  public static int randomInt(int min, int max){
    return ((int)Math.round(Math.random() * (max-min)))+min;
  }
}
