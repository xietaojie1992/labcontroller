package com.xietaojie.lab.impl.extension;

import com.labcontroller.common.mdsal.factory.FlowActionBuilderFactory;
import com.labcontroller.common.mdsal.factory.FlowInstructionsBuilder;
import com.labcontroller.common.mdsal.factory.FlowMatchBuilder;
import org.junit.Test;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.experimenter.mdsal.ext.rev180317.send.experimenter.input.experimenter
        .message.of.choice.LabTlvExperimenterMessageSalCaseBuilder;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.experimenter.mdsal.ext.rev180317.send.experimenter.input.experimenter
        .message.of.choice.lab.tlv.experimenter.message.sal._case.LabTlvExperimenterMessageSalDataBuilder;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.experimenter.ofj.ext.rev180317.LabTlvType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.Instructions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.Match;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.experimenter.types.rev151020.experimenter.core.message
        .ExperimenterMessageOfChoice;

/**
 * @author xietaojie1992
 */
public class LabExtensionTest {

    @Test
    public void testMatch() {
        Match testMatch = FlowMatchBuilder.builder().createTestMatchConditionMatch(123).build();
        System.out.println(testMatch);

    }

    @Test
    public void testAction() {
        Instructions testInstructions = FlowInstructionsBuilder.builder().addAction(
                FlowActionBuilderFactory.createTestActionMovementAction(124, 0).build()).addAction(
                FlowActionBuilderFactory.createOutputAction("openflow:1", 1).build()).build();
        System.out.println(testInstructions);
    }

    @Test
    public void testExperimenter() {
        LabTlvExperimenterMessageSalDataBuilder dataBuilder = new LabTlvExperimenterMessageSalDataBuilder();
        dataBuilder.setType(LabTlvType.LABTYPEHELLO);
        String value = "Hello, switch!";
        dataBuilder.setLength(value.length());
        dataBuilder.setValue(value.getBytes());

        ExperimenterMessageOfChoice choice = new LabTlvExperimenterMessageSalCaseBuilder().setLabTlvExperimenterMessageSalData(dataBuilder.build()).build();
        System.out.println(choice);
    }
}

