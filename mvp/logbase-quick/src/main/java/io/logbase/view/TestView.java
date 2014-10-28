package io.logbase.view;

import com.google.common.base.Predicate;
import com.google.gson.Gson;
import io.logbase.column.Column;
import io.logbase.querying.optiq.Expression;
import io.logbase.table.TableIterator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class TestView implements View {
  Object[][] values;
  Set<String> columns;
  Set<String> ocols;

  public TestView(String filePath, Set<String> columns, int size){
    values = new Object[size][];
    this.ocols = new HashSet<>();
    this.columns = columns;
    for(String col: columns){
      this.ocols.add(col.replaceAll("\\..*",""));
    }
    try {
      BufferedReader br = new BufferedReader(new FileReader(filePath));
      String line;
      int i = 0;
      while((line = br.readLine())!=null){
        Gson gson = new Gson();
        Map json = gson.fromJson(line, Map.class);
        values[i] = new Object[ocols.size()];
        int j=0;
        for(String col : ocols){
          values[i][j] = json.get(col);
          j++;
        }
        i++;
        if(i>=size){
          break;
        }
      }
      if(i<size){
        System.out.println("only able to load " + i + " of desired size of " + size);
      }
      for(;i<size;i++){
        values[i] = new Object[columns.size()];
      }
    } catch (IOException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }

  }


  @Override
  public TableIterator getIterator() {
    return new TestTableIterator();
  }

  @Override
  public TableIterator getIterator(Predicate<CharSequence> filter) {
    return getIterator();
  }

  @Override
  public TableIterator getIterator(Predicate<CharSequence> filter, Expression expression) {
    return getIterator();
  }

  @Override
  public Set<String> getUnderlyingTableNames() {
    throw new UnsupportedOperationException("getUnderlyingTableNames");
  }

  @Override
  public long getNumRows() {
    return values.length;
  }

  @Override
  public Set<String> getColumnNames() {
    return columns;
  }

  @Override
  public Column getColumn(String columnName) {
    throw new UnsupportedOperationException("getColumn");
  }

  class TestTableIterator implements TableIterator{
    int i = 0;
    @Override
    public String[] getColumnNames() {
      return columns.toArray(new String[0]);
    }

    @Override
    public boolean hasNext() {
      return i<values.length;
    }

    @Override
    public Object[] next() {
      return values[i++];
    }
  }
}
