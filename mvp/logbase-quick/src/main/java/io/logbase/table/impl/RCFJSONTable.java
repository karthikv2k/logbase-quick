package io.logbase.table.impl;

import com.google.common.base.Predicate;
import com.google.gson.Gson;
import io.logbase.column.Column;
import io.logbase.column.ColumnFactory;
import io.logbase.column.TypeUtils;
import io.logbase.event.JSONEvent;
import io.logbase.table.Table;
import io.logbase.table.TableIterator;
import io.logbase.utils.GlobalConfig;
import io.logbase.utils.TimeStamp.JSONTimeStampExtractor;
import io.logbase.utils.TimeStamp.TimeStampExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;

public class RCFJSONTable implements Table<JSONEvent> {

  final Logger logger = LoggerFactory.getLogger(RCFJSONTable.class);
  private final Gson gson;

  private String tableName;
  private Map<String, Column> columns;
  private int[] arrayidx = new int[GlobalConfig.MAX_ARRAYS_IN_COLUMN];
  private int arrayDepth = 0;
  private int rowNum = 0;
  private final ColumnFactory columnFactory;
  private long latestTime;
  private TimeStampExtractor tsExtactor;

  public RCFJSONTable(ColumnFactory columnFactory) {
    columns = new ConcurrentSkipListMap<String, Column>();
    this.columnFactory = columnFactory;
    gson = new Gson();
    tsExtactor = new JSONTimeStampExtractor();
  }

  @Override
  public void insert(JSONEvent event) {
    latestTime = Math.max(latestTime, event.getTimestamp());
    Map json = gson.fromJson(event.getJSONString(), Map.class);
    tsExtactor.timestamp(event);
    // logger.debug("Received event data: " + gson.toJson(json));
    // Parse json and create / append to columns
    arrayDepth = 0;
    //traverse(data, "");
    appendRawEvent(event);
    appendTimeStamp(event);
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

  private void appendRawEvent(JSONEvent event) {
    String jsonColumnName = "RawEvent.String";
    Column jsonColumn = columns.get(jsonColumnName);
    if (jsonColumn == null) {
      jsonColumn = columnFactory.createAppendOnlyColumn(String.class,
          jsonColumnName, 0);
      columns.put(jsonColumnName, jsonColumn);
    }
    jsonColumn.append(event.getJSONString().toString(), rowNum);
  }

  private void appendTimeStamp(JSONEvent event) {
    String jsonColumnName = "TimeStamp.Long.LBM";
    Column jsonColumn = columns.get(jsonColumnName);
    if (jsonColumn == null) {
      jsonColumn = columnFactory.createAppendOnlyColumn(Long.class,
        jsonColumnName, 0);
      columns.put(jsonColumnName, jsonColumn);
    }
    jsonColumn.append(tsExtactor.timestamp(event), rowNum);
  }

  private void traverse(Object json, String parent) {
    if (json instanceof Map) {
      Map<String, Object> map = (Map<String, Object>) json;
      if (map.size() == 0) {
        String columnName = parent.substring(1) + ".EmptyMap";
        Column columnGeneric = columns.get(columnName);
        if (columnGeneric == null) {
          columnGeneric = columnFactory.createAppendOnlyColumn(Map.class, columnName, arrayDepth);
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
          columnGeneric = columnFactory.createAppendOnlyColumn(List.class, columnName, arrayDepth);
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
      Class type = TypeUtils.castToLB(json).getClass();
      columnName = parent.substring(1) + "." + TypeUtils.getSQLType(json).getSimpleName();
      Column columnGeneric = columns.get(columnName);
      if (columnGeneric == null) {
        columnGeneric = columnFactory.createAppendOnlyColumn(type, columnName, arrayDepth);
        columns.put(columnName, columnGeneric);
      }
      if (arrayDepth > 0) {
        columnGeneric.append(TypeUtils.castToLB(json), rowNum, arrayidx);
      } else {
        try {
          columnGeneric.append(TypeUtils.castToLB(json), rowNum);
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
    return new SimpleTableIterator(this, maxRows);
  }

  @Override
  public TableIterator getIterator(Predicate<CharSequence> filter) {
    return new SimpleTableIterator(this, this.rowNum, filter);
  }

  @Override
  public TableIterator getIterator(Predicate<CharSequence> filter, Column validRows) {
    return new SimpleTableIterator(this, this.rowNum, filter, validRows);
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
  public Map<String, Column> getColumns() {
    return columns;
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

}
