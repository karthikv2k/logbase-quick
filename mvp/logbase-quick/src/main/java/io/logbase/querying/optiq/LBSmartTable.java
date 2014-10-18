package io.logbase.querying.optiq;

import io.logbase.view.View;
import net.hydromatic.linq4j.*;
import net.hydromatic.optiq.SchemaPlus;
import net.hydromatic.optiq.TranslatableTable;
import net.hydromatic.optiq.impl.AbstractTableQueryable;
import net.hydromatic.optiq.impl.java.AbstractQueryableTable;
import org.eigenbase.rel.RelNode;
import org.eigenbase.relopt.RelOptCluster;
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
public class LBSmartTable extends AbstractQueryableTable implements
  TranslatableTable {

  static final Logger logger = LoggerFactory.getLogger(LBSmartTable.class);
  private View view;

  /**
   * Constructor
   *
   * @param view A Logbase View
   */
  public LBSmartTable(View view) {
    super(Object[].class);
    this.view = view;
  }

  // This method has to return the column names and types
  @Override
  public RelDataType getRowType(RelDataTypeFactory typeFactory) {
    List<String> names = new ArrayList<String>();
    List<RelDataType> types = new ArrayList<RelDataType>();
    String[] allColumnNames = view.getIterator().getColumnNames();
    RelDataType type;
    Class clazz;
    for (String columnName : allColumnNames) {
      clazz = LBTable.getJavaColumnType(columnName);
      if (clazz != null) {
        names.add(columnName);
        type = typeFactory.createJavaType(clazz);
        types.add(type);
      }
    }
    logger.debug("Total no. of columns in view: " + names.size());
    logger.debug("Columns in view: " + names);
    return typeFactory.createStructType(Pair.zip(names, types));
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
    logger.debug("Smart table toRel call received");
    final RelOptCluster cluster = context.getCluster();


    // Passing projection fields as null initially, after rules are fired and
    // rule match
    // the projection field will be used to create a new Table scan with values.
    return new LBTableScan(cluster, relOptTable, this, null, null,
      "Scan for rule fire");
  }

  /**
   * Returns an enumerable over a given projection of the fields. This method is
   * called when a push down rule is successfully fired and transformed.
   *
   * @param projectFields Projections
   * @return The Enumerator
   */
  public Enumerable<Object> pushdown(final List<String> projection,
                                     final String filter) {
    logger.debug("Smart table pushdown call received");
    logger.debug("Pushdown Projection: " + projection);
    logger.debug("Pushdown Filter: " + filter);
    return new AbstractEnumerable<Object>() {
      public Enumerator<Object> enumerator() {
        return new LBEnumerator(view, projection, filter);
      }
    };
  }

}
