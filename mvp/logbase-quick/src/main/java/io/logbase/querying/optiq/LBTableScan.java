package io.logbase.querying.optiq;

import java.util.List;

import net.hydromatic.linq4j.expressions.Blocks;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LBTableScan extends TableAccessRelBase implements
    EnumerableRel {

  static final Logger logger = LoggerFactory.getLogger(LBTableScan.class);
  final LBSmartTable lbSmartTable;


  protected LBTableScan(RelOptCluster cluster, RelOptTable table,
      LBSmartTable lbSmartTable) {
    super(cluster, cluster.traitSetOf(EnumerableConvention.INSTANCE), table);
    this.lbSmartTable = lbSmartTable;

    assert lbSmartTable != null;
  }

  @Override
  public RelNode copy(RelTraitSet traitSet, List<RelNode> inputs) {
    logger.debug("JavaBean table scan copy call received.");
    assert inputs.isEmpty();
    return new LBTableScan(getCluster(), table, lbSmartTable);
  }


  @Override
  public RelWriter explainTerms(RelWriter pw) {
    logger.debug("Table Scan explain terms call received.");
    return super.explainTerms(pw);
  }


  
  @Override
  public RelDataType deriveRowType() {
    logger.debug("Table scan derive row type call received.");
    // TODO Return only projected fields
    return table.getRowType();
  }


  @Override
  public void register(RelOptPlanner planner) {
    // planner.addRule(PushProjectOntoTableRule.INSTANCE);
    planner.addRule(LBPushDownRule.PROJECT_ON_FILTER);
    planner.addRule(LBPushDownRule.FILTER_ON_PROJECT);
    planner.addRule(LBPushDownRule.FILTER);
    planner.addRule(LBPushDownRule.PROJECT);
    logger.debug("JavaBean Smart Table rules added.");
  }


  public Result implement(EnumerableRelImplementor implementor, Prefer pref) {
    PhysType physType = PhysTypeImpl.of(implementor.getTypeFactory(),
        getRowType(), pref.preferArray());
    // TODO pass projections, filter
    return implementor.result(physType, Blocks.toBlock(Expressions.call(
        table.getExpression(LBSmartTable.class), "project",
        Expressions.constant(new int[] {}))));

  }

}
