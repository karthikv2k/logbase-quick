package io.logbase.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tokenizes the given text based on the special characters. The special characters
 * are also treated as individual tokens.
 * Created by admin on 16/10/14.
 */
public class SpecialCharacterTokenizer implements Iterable<String>, Iterator<String> {
  private String value;
  private Pattern pattern;
  private Matcher matcher;
  private List tokens;
  private int index;

  public SpecialCharacterTokenizer(String value) {
    this.value = value;
    // All special characters & end of line character
    this.pattern = Pattern.compile("((\\W|$))");
    matcher = pattern.matcher(value);
    tokens = new ArrayList<String>();
    tokenize();
  }

  private void tokenize() {
    int start = 0, end = 0, index=0;
    while (matcher.find()) {
      end = matcher.start();
      /*
       * There may be two special characters one after the other.
       * In that case there will be no token between the special characters
       */
      if (start != end) {
        tokens.add(index, value.substring(start, end));
        index++;
      }

      //Add the special character as well, but skip the end of line character
      if(end != value.length()) {
        tokens.add(index, matcher.group());
        index++;
      }

      //Since we have already tokenized the special character
      start = end + 1;
    }
  }

  @Override
  public Iterator<String> iterator() {
    return this;
  }

  @Override
  public boolean hasNext() {
    return index < tokens.size();
  }

  @Override
  public String next() {
    return (String)tokens.get(index++);
  }
}
