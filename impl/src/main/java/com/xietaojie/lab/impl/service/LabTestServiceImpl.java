package com.xietaojie.lab.impl.service;

import com.labcontroller.common.mdsal.factory.FlowActionBuilderFactory;
import com.labcontroller.common.mdsal.factory.FlowBuilderFactory;
import com.labcontroller.common.mdsal.factory.FlowInstructionsBuilder;
import com.labcontroller.common.mdsal.factory.FlowMatchBuilder;
import com.labcontroller.common.mdsal.factory.NodeFactory;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.experimenter.mdsal.ext.rev180317.send.experimenter.input.experimenter
        .message.of.choice.LabTlvExperimenterMessageSalCaseBuilder;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.experimenter.mdsal.ext.rev180317.send.experimenter.input.experimenter
        .message.of.choice.lab.tlv.experimenter.message.sal._case.LabTlvExperimenterMessageSalDataBuilder;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.experimenter.ofj.ext.rev180317.LabTlvType;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.test.rev180317.AddExtensionFlowInput;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.test.rev180317.AddExtensionFlowOutput;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.test.rev180317.AddExtensionFlowOutputBuilder;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.test.rev180317.LabTestService;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.test.rev180317.PingOutput;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.test.rev180317.PingOutputBuilder;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.test.rev180317.SayHelloToDeviceInput;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.test.rev180317.SayHelloToDeviceOutput;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.test.rev180317.SayHelloToDeviceOutputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.experimenter.message.service.rev151020.SalExperimenterMessageService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.experimenter.message.service.rev151020.SendExperimenterInputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.service.rev130819.AddFlowInputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.service.rev130819.AddFlowOutput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.service.rev130819.SalFlowService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.Instructions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.Match;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author xietaojie1992
 */
public class LabTestServiceImpl implements LabTestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LabTestService.class);
    private SalExperimenterMessageService salExperimenterMessageService;
    private SalFlowService                salFlowService;

    public LabTestServiceImpl(SalFlowService salFlowService, SalExperimenterMessageService salExperimenterMessageService) {
        this.salFlowService = salFlowService;
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
        inputBuilder.setNode(NodeFactory.createNodeRef(input.getDpid()));
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

    @Override
    public Future<RpcResult<AddExtensionFlowOutput>> addExtensionFlow(AddExtensionFlowInput input) {
        AddExtensionFlowOutputBuilder outputBuilder = new AddExtensionFlowOutputBuilder();
        Match testMatch = FlowMatchBuilder.builder().createTestMatchConditionMatch(input.getMatchConditionValue().intValue()).build();
        Instructions testInstructions = FlowInstructionsBuilder.builder().addAction(
                FlowActionBuilderFactory.createTestActionMovementAction(input.getActionMovementValue().intValue(), 0).build()).addAction(
                FlowActionBuilderFactory.createOutputAction(input.getOfPort(), 1).build()).build();
        Flow testFlow = FlowBuilderFactory.createAddFlowBuilder(BigInteger.ZERO, new BigInteger("ffffffffffffffff", 16), 100, testMatch,
                testInstructions, true).build();

        AddFlowInputBuilder inputBuilder = new AddFlowInputBuilder(testFlow);
        inputBuilder.setNode(NodeFactory.createNodeRef(input.getDpid()));
        try {
            LOGGER.info("addFlow: {}", testFlow);
            RpcResult<AddFlowOutput> rpcResult = salFlowService.addFlow(inputBuilder.build()).get();
            if (rpcResult.isSuccessful()) {
                LOGGER.info("sendExperimenter result: {}", rpcResult.getResult());
                outputBuilder.setFlag(true);
            } else {
                LOGGER.error("testExtension Error : {}", rpcResult.getErrors());
                outputBuilder.setMessage(rpcResult.getErrors().toString());
            }
        } catch (InterruptedException e) {
            LOGGER.error("{}", e);
        } catch (ExecutionException e) {
            LOGGER.error("{}", e);
        }

        return RpcResultBuilder.success(outputBuilder.build()).buildFuture();
    }

}
