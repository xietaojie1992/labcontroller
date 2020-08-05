package com.xietaojie.lab.impl.service;

import com.google.common.util.concurrent.ListenableFuture;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.test.rev180317.AddExtensionFlowInput;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.test.rev180317.AddExtensionFlowOutput;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.test.rev180317.LabTestService;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.test.rev180317.PingInput;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.test.rev180317.PingOutput;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.test.rev180317.SayHelloToDeviceInput;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.test.rev180317.SayHelloToDeviceOutput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.experimenter.message.service.rev151020.SalExperimenterMessageService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.service.rev130819.SalFlowService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.service.rev130918.SalMeterService;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xietaojie
 * @date 2019-08-23 11:21:03
 * @version $ Id: LabTestServiceImpl.java, v 0.1  xietaojie Exp $
 */
public class LabTestServiceImpl implements LabTestService {

    private final static Logger LOGGER = LoggerFactory.getLogger(LabTestServiceImpl.class);

    private final SalFlowService                salFlowService;
    private final SalMeterService               salMeterService;
    private final SalExperimenterMessageService salExperimenterMessageService;

    public LabTestServiceImpl(SalFlowService salFlowService, SalMeterService salMeterService,
                              SalExperimenterMessageService salExperimenterMessageService) {
        this.salFlowService = salFlowService;
        this.salMeterService = salMeterService;
        this.salExperimenterMessageService = salExperimenterMessageService;
    }

    public void init() {
        LOGGER.info("init");
    }

    @Override
    public ListenableFuture<RpcResult<SayHelloToDeviceOutput>> sayHelloToDevice(SayHelloToDeviceInput input) {
        return null;
    }

    @Override
    public ListenableFuture<RpcResult<PingOutput>> ping(PingInput input) {
        return null;
    }

    @Override
    public ListenableFuture<RpcResult<AddExtensionFlowOutput>> addExtensionFlow(AddExtensionFlowInput input) {
        return null;
    }
}
