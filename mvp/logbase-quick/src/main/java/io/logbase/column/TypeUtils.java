package io.logbase.column;

import io.logbase.collections.impl.*;

import java.nio.CharBuffer;

public class TypeUtils {

  public static Class getSQLType(Object value) {
    if (value == null) {
      return NullType.class;
    } else if (value instanceof String) {
      return String.class;
    } else {
      return value.getClass();
    }
  }

  public static Object castToLB(Object value) {
    if (value == null) {
      return Column.NULL;
    /*} else if (value instanceof String) {
      return CharBuffer.wrap((String) value);
    /*} else if (value instanceof Boolean){
      return (Boolean) value ? Column.TRUE : Column.FALSE;*/
    } else {
      return value;
    }
  }

  public static Class getPrimitiveType(Class t){
    if(t.equals(Integer.class)){
      return int.class;
    } else if(t.equals(Long.class)){
      return long.class;
    } else if(t.equals(Float.class)){
      return float.class;
    } else if(t.equals(Double.class)){
      return double.class;
    } else if(t.equals(Boolean.class)){
      return boolean.class;
    } else {
      return t;
    }
  }

  public static Class getBaseType(Class t) {
    if (t.equals(IntegerArrayList.class) ||
      t.equals(BitPackIntList.class)) {
      return Integer.class;
    } else if (t.equals(FloatArrayList.class)) {
      return Float.class;
    } else if (t.equals(BitsetList.class)) {
      return Boolean.class;
    } else if (t.equals(LongArrayList.class)) {
      return Long.class;
    } else if (t.equals(DoubleArrayList.class)) {
      return Double.class;
    } else if (t.equals(StringBufList.class) ||
      t.equals(StringDictionaryList.class)) {
      return String.class;
    } else {
      return t;
    }
  }

  public static Object castToSQL(Object value) {
    if (value instanceof NullType) {
      return null;
    } else if (value instanceof CharBuffer) {
      return value.toString();
    /*} else if (value instanceof Boolean){
      return (Boolean) value ? Column.TRUE : Column.FALSE;*/
    } else {
      return value;
    }
  }

}
