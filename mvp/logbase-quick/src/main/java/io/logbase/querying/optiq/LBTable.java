package io.logbase.querying.optiq;

import io.logbase.view.View;
import net.hydromatic.linq4j.Enumerator;
import net.hydromatic.linq4j.QueryProvider;
import net.hydromatic.linq4j.Queryable;
import net.hydromatic.optiq.SchemaPlus;
import net.hydromatic.optiq.TranslatableTable;
import net.hydromatic.optiq.impl.AbstractTableQueryable;
import net.hydromatic.optiq.impl.java.AbstractQueryableTable;
import net.hydromatic.optiq.rules.java.EnumerableConvention;
import net.hydromatic.optiq.rules.java.JavaRules;
import org.eigenbase.rel.RelNode;
import org.eigenbase.relopt.RelOptTable;
import org.eigenbase.relopt.RelOptTable.ToRelContext;
import org.eigenbase.reltype.RelDataType;
import org.eigenbase.reltype.RelDataTypeFactory;
import org.eigenbase.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * LBTable is an Optiq table that accepts a Logbase View and creates the table
 * out of it.
 *
 * @author Abishek Baskaran
 */
public class LBTable extends AbstractQueryableTable implements
  TranslatableTable {

  static final Logger logger = LoggerFactory.getLogger(LBTable.class);
  private View view;

  /**
   * Constructor
   *
   * @param view A Logbase View
   */
  public LBTable(View view) {
    super(Object[].class);
    this.view = view;
  }

  // This method has to return the column names and types
  @Override
  public RelDataType getRowType(RelDataTypeFactory typeFactory) {
    List<String> names = new ArrayList<String>();
    List<RelDataType> types = new ArrayList<RelDataType>();
    // Set<String> allColumnNames = view.getColumnNames();
    String[] allColumnNames = view.getIterator().getColumnNames();
    logger.debug("Total no. of columns in view: " + allColumnNames.length);
    RelDataType type;
    Class clazz;
    for (String columnName : allColumnNames) {
      clazz = getJavaColumnType(columnName);
      if (clazz != null) {
        names.add(columnName);
        type = typeFactory.createJavaType(clazz);
        types.add(type);
      }
    }
    return typeFactory.createStructType(Pair.zip(names, types));
  }

  /**
   * This method determines if a LogBase column will be added to the Optiq table
   * and returns the column data type.
   *
   * @param columnName Logbase column name
   * @return The java class of the column. Returns null if not an applicable
   *         type.
   */
  // TODO This logic has to be improved
  public static Class getJavaColumnType(String columnName) {
    if (columnName.endsWith(".String") || columnName.endsWith(".String.LBM"))
      return String.class;
    if (columnName.endsWith(".Double") || columnName.endsWith(".Double.LBM"))
      return Double.class;
    if (columnName.endsWith(".Float") || columnName.endsWith(".Float.LBM"))
      return Float.class;
    if (columnName.endsWith(".Int") || columnName.endsWith(".Int.LBM"))
      return Integer.class;
    if (columnName.endsWith(".Long") || columnName.endsWith(".Long.LBM"))
      return Long.class;
    else
      return null;
  }

  // This method has to return an Enumerator which contains an iterator
  @Override
  public <T> Queryable<T> asQueryable(QueryProvider queryProvider,
                                      SchemaPlus schema, String tableName) {
    logger.info("Got query request for: " + tableName);
    return new AbstractTableQueryable<T>(queryProvider, schema, this, tableName) {
      public Enumerator<T> enumerator() {
        // noinspection unchecked
        try {
          logger.debug("Creating simple enumerator");
          LBEnumerator enumerator = new LBEnumerator(view);
          return (Enumerator<T>) enumerator;
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }
    };
  }

  // If smart table this method has to push applicable rules
  @Override
  public RelNode toRel(ToRelContext context, RelOptTable relOptTable) {
    return new JavaRules.EnumerableTableAccessRel(context.getCluster(), context
      .getCluster().traitSetOf(EnumerableConvention.INSTANCE), relOptTable,
      (Class) getElementType());
  }

}
