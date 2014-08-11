package io.logbase.table.impl;

import com.google.common.base.Predicate;
import com.google.gson.Gson;
import io.logbase.column.Column;
import io.logbase.column.ColumnFactory;
import io.logbase.column.ColumnIterator;
import io.logbase.column.TypeUtils;
import io.logbase.column.appendonly.ListBackedColumn;
import io.logbase.event.JSONEvent;
import io.logbase.table.Table;
import io.logbase.table.TableIterator;
import io.logbase.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

public class RCFJSONTable implements Table<JSONEvent> {

  final Logger logger = LoggerFactory.getLogger(RCFJSONTable.class);
  private final Gson gson;

  private String tableName;
  private Map<String, Column> columns;
  private int[] arrayidx = new int[1024];
  private int arrayDepth = 0;
  private int rowNum = 0;
  private final ColumnFactory columnFactory;
  private long latestTime;

  public RCFJSONTable(ColumnFactory columnFactory) {
    columns = new ConcurrentSkipListMap<String, Column>();
    this.columnFactory = columnFactory;
    gson = new Gson();
  }

  @Override
  public void insert(JSONEvent event) {
    latestTime = Math.max(latestTime, event.getTimestamp());
    Map json = gson.fromJson(event.getJSONString(), Map.class);
    // logger.debug("Received event data: " + gson.toJson(json));
    // Parse json and create / append to columns
    arrayDepth = 0;
    //traverse(data, "");
    traverse(json, "");
    rowNum++;
  }

  @Override
  public void setName(String tableName) {
    this.tableName = tableName;
  }

  @Override
  public String getTableName() {
    return tableName;
  }

  private void traverse(Object json, String parent) {
    if (json instanceof Map) {
      Map<String, Object> map = (Map<String, Object>) json;
      if (map.size() == 0) {
        String columnName = parent.substring(1) + ".EmptyMap";
        Column columnGeneric = columns.get(columnName);
        if (columnGeneric == null) {
          columnGeneric = new ListBackedColumn<Map>(columnName, arrayDepth);
        }
        columns.put(columnName, columnGeneric);
        if (arrayDepth > 0) {
          columnGeneric.append(Column.EMPTY_MAP, rowNum, arrayidx);
        } else {
          columnGeneric.append(Column.EMPTY_MAP, rowNum);
        }
      } else {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
          traverse(entry.getValue(), parent + "." + entry.getKey());
        }
      }
    } else if (json instanceof List) {
      List list = (List) json;
      if (list.size() == 0) {
        String columnName = parent.substring(1) + ".EmptyList";
        Column columnGeneric = columns.get(columnName);
        if (columnGeneric == null) {
          columnGeneric = new ListBackedColumn<List>(columnName,
            arrayDepth);
        }
        columns.put(columnName, columnGeneric);
        if (arrayDepth > 0) {
          columnGeneric.append(Column.EMPTY_LIST, rowNum, arrayidx);
        } else {
          columnGeneric.append(Column.EMPTY_LIST, rowNum);
        }
      } else {
        arrayDepth++;
        // since arrayidx can be reused
        arrayidx[arrayDepth - 1] = 0;
        for (Object item : list) {
          arrayidx[arrayDepth - 1]++;
          traverse(item, parent + ".[]");
        }
        arrayDepth--;
      }
    } else {
      String columnName;
      Class type = TypeUtils.getType(TypeUtils.cast(json));
      columnName = parent.substring(1) + "." + type.getSimpleName();
      Column columnGeneric = columns.get(columnName);
      if (columnGeneric == null) {
        columnGeneric = columnFactory.createColumn(type, columnName, arrayDepth);
        columns.put(columnName, columnGeneric);
      }
      if (arrayDepth > 0) {
        columnGeneric.append(TypeUtils.cast(json), rowNum, arrayidx);
      } else {
        try {
          columnGeneric.append(TypeUtils.cast(json), rowNum);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  @Override
  public int getNumOfRows() {
    return rowNum;
  }

  @Override
  public TableIterator getIterator(long maxRows) {
    return new TableIteratorImpl(maxRows);
  }

  @Override
  public TableIterator getIterator() {
    return getIterator(this.rowNum);
  }

  @Override
  public TableIterator getIterator(Predicate<CharSequence> filter) {
    return new TableIteratorImpl(this.rowNum, filter);
  }

  @Override
  public long getLatestEventTime() {
    return latestTime;
  }

  @Override
  public Set<String> getColumnNames() {
    return columns.keySet();
  }

  @Override
  public int compareTo(Table table) {
    int temp = this.tableName.compareTo(table.getTableName());
    if (temp == 0) {
      if (this.latestTime > table.getLatestEventTime()) {
        return 1;
      } else {
        return -1;
      }
    } else {
      return temp;
    }
  }

  public class TableIteratorImpl implements TableIterator {
    String[] columnNames;
    Column[] columns;
    ColumnIterator[] iterators;
    private final Predicate<CharSequence> predicate;

    TableIteratorImpl(long maxRows) {
      this(maxRows, Utils.ALWAYS_TRUE_PATTERN);
    }

    TableIteratorImpl(long maxRows, Predicate<CharSequence> predicate) {
      this.predicate = predicate;

      Map<String, Column> allColumns = RCFJSONTable.this.columns;
      int count = 0;
      for (Map.Entry<String, Column> entry : allColumns.entrySet()) {
        Column c = entry.getValue();
        if (predicate.apply(c.getColumnName()) && c.getStartRowNum() < maxRows) {
          count++;
        }
      }

      columnNames = new String[count];
      columns = new Column[count];
      iterators = new ColumnIterator[count];

      count = 0;
      for (Map.Entry<String, Column> entry : allColumns.entrySet()) {
        Column c = entry.getValue();
        if (predicate.apply(c.getColumnName()) && c.getStartRowNum() < maxRows) {
          columnNames[count] = entry.getKey();
          columns[count] = entry.getValue();
          iterators[count] = entry.getValue().getSimpleIterator(maxRows);
          count++;
        }
      }
    }

    @Override
    public Iterator<Object[]> iterator() {
      return this;
    }

    @Override
    public boolean hasNext() {
      for (Iterator i : iterators) {
        if (i.hasNext()) {
          return true;
        }
      }
      return false;
    }

    @Override
    public Object[] next() {
      Object[] obj = new Object[columns.length];
      for (int i = 0; i < iterators.length; i++) {
        if (iterators[i].hasNext()) {
          obj[i] = iterators[i].next();
        }
      }
      return obj;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException("Readonly table");
    }

    @Override
    public int read(Object[][] buffer, int offset, int count) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean primitiveTypeSupport() {
      return false;
    }

    @Override
    public int readNative(Object buffer, int offset, int count) {
      throw new UnsupportedOperationException();
    }

    @Override
    public String[] getColumnNames() {
      return columnNames;
    }

  }

}
