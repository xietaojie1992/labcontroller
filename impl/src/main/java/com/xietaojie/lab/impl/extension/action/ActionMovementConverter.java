package com.xietaojie.lab.impl.extension.action;

import com.google.common.base.Preconditions;
import org.opendaylight.openflowplugin.extension.api.ConvertorActionFromOFJava;
import org.opendaylight.openflowplugin.extension.api.ConvertorActionToOFJava;
import org.opendaylight.openflowplugin.extension.api.path.ActionPath;
import org.opendaylight.openflowplugin.extension.vendor.nicira.convertor.CodecPreconditionException;
import org.opendaylight.openflowplugin.extension.vendor.nicira.convertor.action.ActionUtil;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.action.mdsal.ext.rev180421.flows.statistics.update.flow.and.statistics.map
        .list.instructions.instruction.instruction.apply.actions._case.apply.actions.action.action
        .TestActionMovementNotifFlowsStatisticsUpdateApplyActionsCaseBuilder;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.action.mdsal.ext.rev180421.flows.statistics.update.flow.and.statistics.map
        .list.instructions.instruction.instruction.write.actions._case.write.actions.action.action
        .TestActionMovementNotifFlowsStatisticsUpdateWriteActionsCaseBuilder;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.action.mdsal.ext.rev180421.group.desc.stats.updated.group.desc.stats
        .buckets.bucket.action.action.TestActionMovementNotifGroupDescStatsUpdatedCaseBuilder;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.action.mdsal.ext.rev180421.nodes.node.table.flow.instructions.instruction
        .instruction.apply.actions._case.apply.actions.action.action.TestActionMovementNodesNodeTableFlowApplyActionsCaseBuilder;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.action.mdsal.ext.rev180421.nodes.node.table.flow.instructions.instruction
        .instruction.write.actions._case.write.actions.action.action.TestActionMovementNodesNodeTableFlowWriteActionsCaseBuilder;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.action.ofj.ext.rev180421.TestActionMovementGrouping;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.action.ofj.ext.rev180421.action.container.action.choice
        .TestActionMovementCase;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.action.ofj.ext.rev180421.action.container.action.choice
        .TestActionMovementCaseBuilder;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.action.ofj.ext.rev180421.test.action.movement.grouping
        .TestActionMovementValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.actions.grouping.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xietaojie1992
 */
public class ActionMovementConverter
        implements ConvertorActionToOFJava<org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.Action, Action>,
        ConvertorActionFromOFJava<Action, ActionPath> {

    private static final Logger logger = LoggerFactory.getLogger(ActionMovementConverter.class);

    @Override
    public org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.Action convert(Action input, ActionPath path) {
        logger.info("convert action: {}", input);

        TestActionMovementValue movementValue = (TestActionMovementValue) input.getActionChoice();
        logger.info("TestActionMovementValue = {}", movementValue);

        TestActionMovementCase movementCase = (TestActionMovementCase) input.getActionChoice();
        logger.info("TestActionMovementCase = {}", movementCase);

        switch (path) {
            case NODES_NODE_TABLE_FLOW_INSTRUCTIONS_INSTRUCTION_WRITEACTIONSCASE_WRITEACTIONS_ACTION_ACTION_EXTENSIONLIST_EXTENSION:
                return new TestActionMovementNodesNodeTableFlowWriteActionsCaseBuilder().setTestActionMovementValue(movementValue)
                        .build();
            case NODES_NODE_TABLE_FLOW_INSTRUCTIONS_INSTRUCTION_APPLYACTIONSCASE_APPLYACTIONS_ACTION_ACTION_EXTENSIONLIST_EXTENSION:
                return new TestActionMovementNodesNodeTableFlowApplyActionsCaseBuilder().setTestActionMovementValue(movementValue)
                        .build();
            case FLOWSSTATISTICSUPDATE_FLOWANDSTATISTICSMAPLIST_INSTRUCTIONS_INSTRUCTION_INSTRUCTION_WRITEACTIONSCASE_WRITEACTIONS_ACTION_ACTION:
                return new TestActionMovementNotifFlowsStatisticsUpdateWriteActionsCaseBuilder().setTestActionMovementValue(movementValue)
                        .build();
            case FLOWSSTATISTICSUPDATE_FLOWANDSTATISTICSMAPLIST_INSTRUCTIONS_INSTRUCTION_INSTRUCTION_APPLYACTIONSCASE_APPLYACTIONS_ACTION_ACTION:
                return new TestActionMovementNotifFlowsStatisticsUpdateApplyActionsCaseBuilder().setTestActionMovementValue(movementValue)
                        .build();
            case GROUPDESCSTATSUPDATED_GROUPDESCSTATS_BUCKETS_BUCKET_ACTION:
                return new TestActionMovementNotifGroupDescStatsUpdatedCaseBuilder().setTestActionMovementValue(movementValue).build();
            default:
                throw new CodecPreconditionException(path);
        }
    }

    @Override
    public Action convert(org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.Action actionCase) {
        logger.info("convert action to ofjava: {}", actionCase);
        Preconditions.checkArgument(actionCase instanceof TestActionMovementGrouping);
        TestActionMovementValue movementValue = ((TestActionMovementGrouping) actionCase).getTestActionMovementValue();
        TestActionMovementCaseBuilder movementCaseBuilder = new TestActionMovementCaseBuilder().setTestActionMovementValue(
                movementValue);
        Action action = ActionUtil.createAction(movementCaseBuilder.build());
        logger.info("action = {}", action);
        return action;
    }
}
