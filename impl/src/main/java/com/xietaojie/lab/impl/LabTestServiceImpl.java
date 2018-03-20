package com.xietaojie.lab.impl;

import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.extension.mdsal.models.rev180317.send.experimenter.input.experimenter
        .message.of.choice.LabTlvExperimenterMessageSalCaseBuilder;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.extension.mdsal.models.rev180317.send.experimenter.input.experimenter
        .message.of.choice.lab.tlv.experimenter.message.sal._case.LabTlvExperimenterMessageSalDataBuilder;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.extension.ofj.models.rev180317.LabTlvType;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.test.rev180317.LabTestService;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.test.rev180317.PingOutput;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.test.rev180317.PingOutputBuilder;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.test.rev180317.SayHelloToDeviceInput;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.test.rev180317.SayHelloToDeviceOutput;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.test.rev180317.SayHelloToDeviceOutputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.experimenter.message.service.rev151020.SalExperimenterMessageService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.experimenter.message.service.rev151020.SendExperimenterInputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeRef;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.Nodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.NodeKey;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author xietaojie1992
 */
public class LabTestServiceImpl implements LabTestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LabTestService.class);
    private SalExperimenterMessageService salExperimenterMessageService;

    public LabTestServiceImpl(SalExperimenterMessageService salExperimenterMessageService) {
        this.salExperimenterMessageService = salExperimenterMessageService;
        LOGGER.info("LabTestServiceImpl init.");
    }

    @Override
    public Future<RpcResult<PingOutput>> ping() {
        return RpcResultBuilder.success(new PingOutputBuilder().setResponse("PONG").build()).buildFuture();
    }

    @Override
    public Future<RpcResult<SayHelloToDeviceOutput>> sayHelloToDevice(SayHelloToDeviceInput input) {
        SayHelloToDeviceOutputBuilder outputBuilder = new SayHelloToDeviceOutputBuilder();

        LabTlvExperimenterMessageSalDataBuilder dataBuilder = new LabTlvExperimenterMessageSalDataBuilder();
        dataBuilder.setType(LabTlvType.LABTYPEHELLO);
        String value = "Hello, switch!";
        dataBuilder.setLength(value.length());
        dataBuilder.setValue(value.getBytes());

        SendExperimenterInputBuilder inputBuilder = new SendExperimenterInputBuilder();
        inputBuilder.setNode(createNodeRef(input.getDpid()));
        inputBuilder.setExperimenterMessageOfChoice(
                new LabTlvExperimenterMessageSalCaseBuilder().setLabTlvExperimenterMessageSalData(dataBuilder.build()).build());

        try {
            RpcResult<Void> rpcResult = salExperimenterMessageService.sendExperimenter(inputBuilder.build()).get();
            LOGGER.info("sendExperimenter result: {}", rpcResult);
            if (rpcResult.isSuccessful()) {
                outputBuilder.setFlag(true);
            } else {
                LOGGER.error("testExtension Error : {}", rpcResult.getErrors());
                outputBuilder.setCode("");
                outputBuilder.setMessage(rpcResult.getErrors().toString());
            }
        } catch (InterruptedException | ExecutionException e) {
            outputBuilder.setCode(e.getClass().getName());
            outputBuilder.setMessage(e.getMessage());
            LOGGER.error("{}", e);
        }
        return RpcResultBuilder.success(outputBuilder.build()).buildFuture();
    }

    public static NodeRef createNodeRef(String datapathId) {
        NodeKey key = new NodeKey(new NodeId(datapathId));
        InstanceIdentifier<Node> identifier = InstanceIdentifier.create(Nodes.class).child(Node.class, key);
        return new NodeRef(identifier);
    }
}
