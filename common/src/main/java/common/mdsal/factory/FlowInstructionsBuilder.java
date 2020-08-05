package common.mdsal.factory;

import com.google.common.collect.Lists;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.Instructions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.InstructionsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.ApplyActionsCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.MeterCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.apply.actions._case.ApplyActionsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.meter._case.MeterBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.Instruction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.InstructionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.InstructionKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.MeterId;

import java.util.List;

/**
 * @author xietaojie1992
 */
public class FlowInstructionsBuilder {

    private InstructionsBuilder instructionsBuilder = new InstructionsBuilder();
    private List<Action>        actions             = Lists.newArrayList();
    private Long                meterId             = 0L;

    private FlowInstructionsBuilder() {
    }

    public static FlowInstructionsBuilder builder() {
        return new FlowInstructionsBuilder();
    }

    public FlowInstructionsBuilder setMeterId(Long meterId) {
        this.meterId = meterId;
        return this;
    }

    public FlowInstructionsBuilder addAction(Action action) {
        this.actions.add(action);
        return this;
    }

    public Instructions build() {
        List<Instruction> instructionList = Lists.newArrayList();

        // 1. build apply action
        ApplyActionsBuilder applyActionsBuilder = new ApplyActionsBuilder().setAction(actions);
        InstructionBuilder applyActionInstructionBuilder = new InstructionBuilder().withKey(new InstructionKey(1)).setOrder(1);
        applyActionInstructionBuilder.setInstruction(new ApplyActionsCaseBuilder().setApplyActions(applyActionsBuilder.build()).build());
        instructionList.add(applyActionInstructionBuilder.build());

        // 2. build meter action
        if (meterId > 0) {
            MeterBuilder meterBuilder = new MeterBuilder().setMeterId(new MeterId(meterId));
            MeterCaseBuilder meterCaseBuilder = new MeterCaseBuilder().setMeter(meterBuilder.build());
            InstructionBuilder meterInstructionBuilder = new InstructionBuilder().withKey(new InstructionKey(0)).setOrder(0);
            meterInstructionBuilder.setInstruction(meterCaseBuilder.build());
            instructionList.add(meterInstructionBuilder.build());
        }

        // set instructionList to instructionsBuilder
        instructionsBuilder.setInstruction(instructionList);
        return instructionsBuilder.build();
    }

    ///////////////////////// Combine Examples //////////////////////////////
    public FlowInstructionsBuilder setVlanAndOutput(Integer vlanId, String outport) {
        actions.add(FlowActionBuilderFactory.createSetVlanAction(vlanId, 0).build());
        actions.add(FlowActionBuilderFactory.createOutputAction(outport, 1).build());
        return this;
    }

    public FlowInstructionsBuilder createControllerInstructions() {
        actions.add(FlowActionBuilderFactory.createControllerAction(0).build());
        return this;
    }

}
