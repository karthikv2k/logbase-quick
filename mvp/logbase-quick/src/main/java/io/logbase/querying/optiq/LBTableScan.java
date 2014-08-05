package io.logbase.querying.optiq;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;

import net.hydromatic.linq4j.expressions.Blocks;
import net.hydromatic.linq4j.expressions.Expression;
import net.hydromatic.linq4j.expressions.Expressions;
import net.hydromatic.optiq.rules.java.EnumerableConvention;
import net.hydromatic.optiq.rules.java.EnumerableRel;
import net.hydromatic.optiq.rules.java.EnumerableRelImplementor;
import net.hydromatic.optiq.rules.java.PhysType;
import net.hydromatic.optiq.rules.java.PhysTypeImpl;

import org.eigenbase.rel.RelNode;
import org.eigenbase.rel.RelWriter;
import org.eigenbase.rel.TableAccessRelBase;
import org.eigenbase.relopt.RelOptCluster;
import org.eigenbase.relopt.RelOptPlanner;
import org.eigenbase.relopt.RelOptTable;
import org.eigenbase.relopt.RelTraitSet;
import org.eigenbase.reltype.RelDataType;
import org.eigenbase.reltype.RelDataTypeFactory;
import org.eigenbase.reltype.RelDataTypeField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LBTableScan extends TableAccessRelBase implements
    EnumerableRel {

  static final Logger logger = LoggerFactory.getLogger(LBTableScan.class);
  final LBSmartTable lbSmartTable;
  final List<String> projection;
  final String filter;


  protected LBTableScan(RelOptCluster cluster, RelOptTable table,
      LBSmartTable lbSmartTable, List<String> projection, String filter) {
    super(cluster, cluster.traitSetOf(EnumerableConvention.INSTANCE), table);
    this.lbSmartTable = lbSmartTable;
    this.projection = projection;
    this.filter = filter;

    assert lbSmartTable != null;
  }

  @Override
  public RelNode copy(RelTraitSet traitSet, List<RelNode> inputs) {
    logger.debug("JavaBean table scan copy call received.");
    assert inputs.isEmpty();
    return new LBTableScan(getCluster(), table, lbSmartTable, projection,
        filter);
  }


  @Override
  public RelWriter explainTerms(RelWriter pw) {
    logger.debug("Table Scan explain terms call received.");
    return super.explainTerms(pw).item("projection", projection)
        .item("filter", filter);
  }


  
  @Override
  public RelDataType deriveRowType() {
    logger.debug("Table scan derive row type call received.");
    if (projection == null)
      return table.getRowType();
    else {
      final List<RelDataTypeField> fieldList = table.getRowType()
          .getFieldList();
      final RelDataTypeFactory.FieldInfoBuilder builder = getCluster()
          .getTypeFactory().builder();
      for (RelDataTypeField field : fieldList) {
        if (projection.contains(field.getName())) {
          logger.debug("Adding projected column: " + field.getName());
          builder.add(field);
        }
      }
      return builder.build();
    }
  }


  @Override
  public void register(RelOptPlanner planner) {
    planner.addRule(LBPushDownRule.PROJECT_ON_FILTER);
    planner.addRule(LBPushDownRule.FILTER_ON_PROJECT);
    planner.addRule(LBPushDownRule.FILTER);
    planner.addRule(LBPushDownRule.PROJECT);
    logger.debug("JavaBean Smart Table rules added.");
  }



  /**
   * This method is called when a Push down rule is fired and transformed. This
   * method specifies which method in LBSmartTable will be called and what
   * operands will be sent to it. Sending Strings, int[] are straing forward,
   * but sending a List<String> requires changing it using the
   * constantStringList method.
   */
  public Result implement(EnumerableRelImplementor implementor, Prefer pref) {
    PhysType physType = PhysTypeImpl.of(implementor.getTypeFactory(),
        getRowType(), pref.preferCustom());
    logger.debug("Implement call received");
    return implementor.result(physType, Blocks.toBlock(Expressions.call(
        table.getExpression(LBSmartTable.class), "pushdown",
        filter == null ? Expressions.constant(null)
            : constantStringList(projection), Expressions.constant(filter))));

  }

  /**
   * Utility method to convert a List<String> to be passed down to LBSmartTable
   * pushdown method.
   * 
   * @param strings
   *          List of strings to be converted to Expression.
   * @return Expression required to be sent to pushdown method call.
   */
  private static Expression constantStringList(final List<String> strings) {
    return Expressions.call(Arrays.class, "asList",
        Expressions.newArrayInit(Object.class, new AbstractList<Expression>() {
          @Override
          public Expression get(int index) {
            return Expressions.constant(strings.get(index));
          }

          @Override
          public int size() {
            return strings.size();
          }
        }));
  }

}
