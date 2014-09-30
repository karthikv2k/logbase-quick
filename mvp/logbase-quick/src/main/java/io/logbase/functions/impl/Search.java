package io.logbase.functions.impl;

import io.logbase.collections.impl.BitsetList;
import io.logbase.column.Column;
import io.logbase.column.SimpleColumnIterator;
import io.logbase.column.appendonly.AppendOnlyColumn;
import io.logbase.functions.Function;
import io.logbase.utils.RegexFilter;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Kousik on 24/09/14.
 */
public class Search implements Function {

  private Column column;
  private String searchString;
  private BitsetList validRowList;
  private Column rows;
  RegexFilter filter;

  public Search(Object[] operands) {
    this.validRowList = new BitsetList();
    this.rows = new AppendOnlyColumn("Valid Rows", 0, validRowList);

    checkArgument(operands.length == 2, "Only 2 operands expected");
    for (Object operand: operands) {
      checkNotNull(operand, "Operand(s) should not be NULL");
    }
    checkArgument(operands[0] instanceof Column, "First operand should be a Column");
    checkArgument(operands[1] instanceof String, "Second operand should be the search string");

    this.column = (Column)operands[0];
    this.searchString = (String)operands[1];
  }

  @Override
  public Object execute() {
    String regexDelimiter = "(\\W|^|$)"; // Any special character, end or beginning of line
    String pattern = regexDelimiter + this.searchString + regexDelimiter;
    SimpleColumnIterator itr = new SimpleColumnIterator(column, column.getRowCount());
    long rowNum = 0;

    this.filter = new RegexFilter(pattern);
    while (itr.hasNext()) {
      Object buffer = itr.next();
      if (buffer != null && filter.accept((String)buffer)) {
        rows.append(true, rowNum);
      }
      rowNum++;
    }
    return rows;
  }
}
