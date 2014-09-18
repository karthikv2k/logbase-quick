package io.logbase.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kousik on 15/09/14.
 */
public class RegexFilter implements Filter<String> {

  Pattern regexPattern;
  Matcher matcher;
  public RegexFilter(String pattern) {
    regexPattern = Pattern.compile(pattern);
  }

  @Override
  public boolean accept(String value) {
    matcher = regexPattern.matcher(value);
    if (matcher.find()) {
      return true;
    }
    return false;
  }
}
