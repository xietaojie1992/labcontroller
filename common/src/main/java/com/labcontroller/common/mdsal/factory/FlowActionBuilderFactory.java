package com.labcontroller.common.mdsal.factory;

import com.labcontroller.common.constants.ProtocolNumber;
import org.opendaylight.openflowplugin.api.OFConstants;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.action.mdsal.ext.rev180421.add.flow.input.instructions.instruction
        .instruction.apply.actions._case.apply.actions.action.action.TestActionMovementRpcAddFlowApplyActionsCase;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.action.mdsal.ext.rev180421.add.flow.input.instructions.instruction
        .instruction.apply.actions._case.apply.actions.action.action.TestActionMovementRpcAddFlowApplyActionsCaseBuilder;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.action.ofj.ext.rev180421.test.action.movement.grouping
        .TestActionMovementValue;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.action.ofj.ext.rev180421.test.action.movement.grouping
        .TestActionMovementValueBuilder;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Ipv4Prefix;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Uri;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.yang.types.rev100924.MacAddress;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.DropActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.OutputActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.PopMplsActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.PopVlanActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.PushMplsActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.PushVlanActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.SetDlDstActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.SetFieldCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.SetNwSrcActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.drop.action._case.DropActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.output.action._case.OutputActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.pop.mpls.action._case.PopMplsActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.pop.vlan.action._case.PopVlanActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.push.mpls.action._case.PushMplsActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.push.vlan.action._case.PushVlanActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.set.dl.dst.action._case.SetDlDstActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.set.field._case.SetFieldBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.set.nw.src.action._case.SetNwSrcActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.ActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.ActionKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.address.address.Ipv4Builder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.OutputPortValues;
import org.opendaylight.yang.gen.v1.urn.opendaylight.l2.types.rev130827.VlanId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.ProtocolMatchFieldsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.TunnelBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.VlanMatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.vlan.match.fields.VlanIdBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.extension.nicira.action.rev140714.nodes.node.table.flow.instructions
        .instruction.instruction.apply.actions._case.apply.actions.action.action.NxActionResubmitNodesNodeTableFlowApplyActionsCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.extension.nicira.action.rev140714.nx.action.resubmit.grouping
        .NxResubmitBuilder;
import org.opendaylight.yangtools.yang.binding.Augmentation;
import org.opendaylight.yangtools.yang.binding.DataContainer;

import java.math.BigInteger;

/**
 * @author xietaojie1992
 */
public class FlowActionBuilderFactory {

    public static ActionBuilder createPushMplsAction(int order) {
        PushMplsActionBuilder pushMplsActionBuilder = new PushMplsActionBuilder().setEthernetType(0x8847);
        ActionBuilder actionMplsBuilder = new ActionBuilder().setKey(new ActionKey(order)).setOrder(order);
        actionMplsBuilder.setAction(new PushMplsActionCaseBuilder().setPushMplsAction(pushMplsActionBuilder.build()).build());
        return actionMplsBuilder;
    }

    public static ActionBuilder createSetMplsLabelAction(Long label, int order) {
        SetFieldBuilder setFieldMplsBuilder = new SetFieldBuilder().setProtocolMatchFields(
                new ProtocolMatchFieldsBuilder().setMplsLabel(label).build());
        ActionBuilder actionFieldBuilder = new ActionBuilder().setKey(new ActionKey(order)).setOrder(order);
        actionFieldBuilder.setAction(new SetFieldCaseBuilder().setSetField(setFieldMplsBuilder.build()).build());
        return actionFieldBuilder;
    }

    public static ActionBuilder createSetDLDstAction(String macAddress, int order) {
        SetDlDstActionBuilder setDlDstActionBuilder = new SetDlDstActionBuilder().setAddress(new MacAddress(macAddress));
        ActionBuilder actionDlDstBuilder = new ActionBuilder().setKey(new ActionKey(order)).setOrder(order);
        actionDlDstBuilder.setAction(new SetDlDstActionCaseBuilder().setSetDlDstAction(setDlDstActionBuilder.build()).build());
        return actionDlDstBuilder;
    }

    public static ActionBuilder createSetSrcIpAction(String ipAddress, int order) {
        SetNwSrcActionBuilder setNwSrcActionBuilder = new SetNwSrcActionBuilder();
        setNwSrcActionBuilder.setAddress(new Ipv4Builder().setIpv4Address(new Ipv4Prefix(ipAddress)).build());
        ActionBuilder actionBuilder = new ActionBuilder().setKey(new ActionKey(order)).setOrder(order);
        actionBuilder.setAction(new SetNwSrcActionCaseBuilder().setSetNwSrcAction(setNwSrcActionBuilder.build()).build());
        return actionBuilder;
    }

    public static ActionBuilder createOutputAction(String output, int order) {
        OutputActionBuilder outputActionBuilder = new OutputActionBuilder().setOutputNodeConnector(new Uri(output)).setMaxLength(
                OFConstants.OFPCML_NO_BUFFER);
        ActionBuilder actionOutputBuilder = new ActionBuilder().setKey(new ActionKey(order)).setOrder(order);
        actionOutputBuilder.setAction(new OutputActionCaseBuilder().setOutputAction(outputActionBuilder.build()).build());
        return actionOutputBuilder;
    }

    public static ActionBuilder createPopMplsAction(Integer etherType, int order) {
        PopMplsActionBuilder popMplsActionBuilder = new PopMplsActionBuilder().setEthernetType(etherType);
        ActionBuilder actionMplsBuilder = new ActionBuilder().setKey(new ActionKey(order)).setOrder(order);
        actionMplsBuilder.setAction(new PopMplsActionCaseBuilder().setPopMplsAction(popMplsActionBuilder.build()).build());
        return actionMplsBuilder;
    }

    public static ActionBuilder createDropAction(int order) {
        DropActionBuilder dropActionBuilder = new DropActionBuilder();
        ActionBuilder actionDropputBuilder = new ActionBuilder().setKey(new ActionKey(order)).setOrder(order);
        actionDropputBuilder.setAction(new DropActionCaseBuilder().setDropAction(dropActionBuilder.build()).build());
        return actionDropputBuilder;
    }

    public static ActionBuilder createTunnelAction(BigInteger tunnelId, int order) {
        SetFieldBuilder setFieldBuilder = new SetFieldBuilder().setTunnel(new TunnelBuilder().setTunnelId(tunnelId).build());
        ActionBuilder actionTunnelBuilder = new ActionBuilder().setKey(new ActionKey(order)).setOrder(order);
        actionTunnelBuilder.setAction(new SetFieldCaseBuilder().setSetField(setFieldBuilder.build()).build());
        return actionTunnelBuilder;
    }

    public static ActionBuilder createPushVlanAction(int order) {
        PushVlanActionBuilder pushVlanActionBuilder = new PushVlanActionBuilder().setEthernetType(ProtocolNumber.TYPE_VLAN.intValue());
        ActionBuilder actionBuilder = new ActionBuilder().setKey(new ActionKey(order)).setOrder(order);
        actionBuilder.setAction(new PushVlanActionCaseBuilder().setPushVlanAction(pushVlanActionBuilder.build()).build());
        return actionBuilder;
    }

    public static ActionBuilder createPopVlanAction(int order) {
        ActionBuilder actionBuilder = new ActionBuilder().setKey(new ActionKey(order)).setOrder(order);
        actionBuilder.setAction(new PopVlanActionCaseBuilder().setPopVlanAction(new PopVlanActionBuilder().build()).build());
        return actionBuilder;
    }

    public static ActionBuilder createSetVlanAction(int vlanId, int order) {
        VlanIdBuilder vlanIdBuilder = new VlanIdBuilder().setVlanId(new VlanId(vlanId)).setVlanIdPresent(true);
        VlanMatchBuilder vlanMatchBuilder = new VlanMatchBuilder().setVlanId(vlanIdBuilder.build());
        SetFieldBuilder setFieldBuilder = new SetFieldBuilder().setVlanMatch(vlanMatchBuilder.build());
        ActionBuilder actionBuilder = new ActionBuilder().setKey(new ActionKey(order)).setOrder(order);
        actionBuilder.setAction(new SetFieldCaseBuilder().setSetField(setFieldBuilder.build()).build());
        return actionBuilder;
    }

    public static ActionBuilder createResubmitAction(int order) {
        NxResubmitBuilder nxResubmitBuilder = new NxResubmitBuilder();
        //        nxResubmitBuilder.setTable((short) 0);
        //        nxResubmitBuilder.setInPort(OutputPortValues.LOCAL.getIntValue());
        ActionBuilder actionResubmitBuilder = new ActionBuilder().setKey(new ActionKey(order)).setOrder(order);
        actionResubmitBuilder.setAction(
                new NxActionResubmitNodesNodeTableFlowApplyActionsCaseBuilder().setNxResubmit(nxResubmitBuilder.build()).build());
        return actionResubmitBuilder;
    }

    public static ActionBuilder createNormalAction(int order) {
        return createOutputAction(OutputPortValues.NORMAL.toString(), order);
    }

    public static ActionBuilder createControllerAction(int order) {
        return createOutputAction(OutputPortValues.CONTROLLER.toString(), order);
    }

    public static ActionBuilder createLocalAction(int order) {
        return createOutputAction(OutputPortValues.LOCAL.toString(), order);
    }

    public static ActionBuilder createFloodAction(int order) {
        return createOutputAction(OutputPortValues.FLOOD.toString(), order);
    }

    public static ActionBuilder createTestActionMovementAction(Integer movement, int order) {
        TestActionMovementRpcAddFlowApplyActionsCaseBuilder caseBuilder = new TestActionMovementRpcAddFlowApplyActionsCaseBuilder();
        caseBuilder.setTestActionMovementValue(new TestActionMovementValueBuilder().setMovement(movement.longValue()).build());
        return new ActionBuilder().setAction(caseBuilder.build()).setOrder(order).setKey(new ActionKey(order));
    }
}
