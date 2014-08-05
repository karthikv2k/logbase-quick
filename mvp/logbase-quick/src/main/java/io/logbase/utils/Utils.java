package io.logbase.utils;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public class Utils {

  public static final Predicate<CharSequence> ALWAYS_TRUE_PATTERN = Predicates.alwaysTrue();


  public static String toString(Object val) {
    if (val == null) {
      return "null";
    } else if (!(val instanceof Object[])) {
      return val.toString();
    }
    Object[] values = (Object[]) val;
    if (values.length == 0) {
      return "[]";
    }
    StringBuffer buf = new StringBuffer();
    buf.append("[");
    for (Object value : values) {
      if (value != null) {
        if (value instanceof Object[]) {
          Object[] nestedValues = (Object[]) value;
          if (nestedValues.length > 0) {
            buf.append("[");
            for (Object nestedValue : nestedValues) {
              if (nestedValue == null) {
                buf.append("null");
              } else {
                buf.append(nestedValue.toString());
              }
              buf.append(",");
            }
            buf.setCharAt(buf.length() - 1, ']');
            ;
          } else {
            buf.append("[]");
          }
        } else {
          buf.append(value.toString());
        }
      } else {
        buf.append("null");
      }
      buf.append(",");
    }
    buf.setCharAt(buf.length() - 1, ']');
    return buf.toString();
  }
}
