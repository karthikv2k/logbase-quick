package io.logbase.querying.optiq;

import io.logbase.datamodel.View;
import io.logbase.querying.optiq.LBTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.hydromatic.optiq.Table;
import net.hydromatic.optiq.impl.AbstractSchema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;

/**
 * LBSchema is a type of Optiq Schema that contains a list of Logbase tables.
 *
 * @author Abishek Baskaran
 */
public class LBSchema extends AbstractSchema {

  static final Logger logger = LoggerFactory.getLogger(LBSchema.class);
  private String schemaName;
  private Map<String, View> viewMap = new HashMap<String, View>();
  private List<String> smartTables = new ArrayList<String>();

  /**
   * Constructor
   * 
   * @param schemaName
   *          The schema name which is like database name.
   */
  public LBSchema(String schemaName) {
    super();
    this.schemaName = schemaName;
  }

  /**
   * Adds a table/view to the schema.
   * 
   * @param tableName
   *          The name of the table, has to be unique else will overwrite.
   * @param javaBeanList
   *          A List of JavaBeans of same type that's to be seen as table.
   */
  public void addAsTable(String tableName, View view) {
    viewMap.put(tableName, view);
    logger.info("Added table: " + tableName + " to Schema: " + schemaName);
  }

  /**
   * Adds a smart table to the schema.
   * 
   * @param tableName
   *          The name of the table, has to be unique else will overwrite.
   * @param javaBeanList
   *          A List of JavaBeans of same type that's to be seen as table.
   */
  public <E> void addAsSmartTable(String tableName, View view) {
    viewMap.put(tableName, view);
    smartTables.add(tableName);
    logger
        .info("Added smart table: " + tableName + " to Schema: " + schemaName);
  }

  /**
   * @return The name of the schema
   */
  public String getName() {
    return schemaName;
  }

  @Override
  protected Map<String, Table> getTableMap() {
    final ImmutableMap.Builder<String, Table> builder = ImmutableMap.builder();
    for (String tableName : viewMap.keySet()) {
      Table lbTable = null;
      // TODO for smart table
      // if (smartTables.contains(tableName))
      // lbTable = new JavaBeanSmartTable(javaBeanListMap.get(tableName));
      // else
      lbTable = new LBTable(viewMap.get(tableName));
      builder.put(tableName, lbTable);
      logger.debug("Initialized LBTable for: " + tableName);
    }
    return builder.build();
  }

}
