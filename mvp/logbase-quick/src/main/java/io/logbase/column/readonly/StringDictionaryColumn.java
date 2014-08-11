package io.logbase.column.readonly;

import io.logbase.column.Column;
import io.logbase.column.ColumnIterator;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class StringDictionaryColumn {

  StringDictionaryColumn(Column<String> column){

    SortedSet<byte[]> dict = new TreeSet<byte[]>();
    ColumnIterator<Object> iterator = column.getSimpleIterator();

    //iterate through the column and form the dict. Here we convert into UTF8 rightaway and store them instead of string
    //representation
    while(iterator.hasNext()){
      Object temp = iterator.next();
     // if()
     // dict.add(iterator.next())
    }

  }

}
