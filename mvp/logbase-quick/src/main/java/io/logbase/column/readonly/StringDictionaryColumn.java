package io.logbase.column.readonly;

import io.logbase.collections.BatchIterator;
import io.logbase.collections.impl.UniqStringConsumer;
import io.logbase.column.Column;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class StringDictionaryColumn {
  //final StringList dict;

  StringDictionaryColumn(Column<String> column) {
    BatchIterator<String> iterator = column.getValuesIterator();
    UniqStringConsumer stringConsumer = new UniqStringConsumer();

    while (iterator.hasNext()) {
      stringConsumer.accept(iterator.next());
    }

    //dict = new StringList(stringConsumer.numValues())


  }

}
