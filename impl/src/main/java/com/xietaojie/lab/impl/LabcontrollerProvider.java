/*
 * Copyright © 2018 xietaojie1992@github and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.xietaojie.lab.impl;

import com.google.common.base.Optional;
import com.xietaojie.lab.impl.extension.LabExtensionRegister;
import com.xietaojie.lab.impl.listeners.DataStoreListener;
import com.xietaojie.lab.impl.listeners.ErrorListener;
import com.xietaojie.lab.impl.listeners.PacketInListener;
import com.xietaojie.lab.impl.service.LabHealthCareServiceImpl;
import com.xietaojie.lab.impl.service.LabTestServiceImpl;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.ProviderContext;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.RpcRegistration;
import org.opendaylight.controller.sal.binding.api.BindingAwareProvider;
import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.controller.sal.binding.api.RpcConsumerRegistry;
import org.opendaylight.openflowjava.nx.api.NiciraExtensionCodecRegistrator;
import org.opendaylight.openflowjava.protocol.spi.connection.SwitchConnectionProvider;
import org.opendaylight.openflowplugin.extension.api.ExtensionConverterRegistrator;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.health.care.rev180324.LabHealthCareService;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.test.rev180317.LabTestService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.experimenter.message.service.rev151020.SalExperimenterMessageService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.service.rev130819.SalFlowService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.statistics.rev130819.OpendaylightFlowStatisticsService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.group.service.rev130918.SalGroupService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.Nodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.NodeKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.service.rev130918.SalMeterService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.statistics.rev131111.OpendaylightMeterStatisticsService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.port.service.rev131107.SalPortService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.port.statistics.rev131214.OpendaylightPortStatisticsService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.queue.service.rev150305.SalQueueService;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author xietaojie1992
 */
public class LabcontrollerProvider implements BindingAwareProvider, AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(LabcontrollerProvider.class);

    private DataBroker                         dataBroker;
    private SalFlowService                     salFlowService;
    private SalMeterService                    salMeterService;
    private SalPortService                     salPortService;
    private SalGroupService                    salGroupService;
    private SalQueueService                    salQueueService;
    private SalExperimenterMessageService      salExperimenterMessageService;
    private PacketProcessingService            packetProcessingService;
    private OpendaylightFlowStatisticsService  flowStatisticsService;
    private OpendaylightMeterStatisticsService meterStatisticsService;
    private OpendaylightPortStatisticsService  portStatisticsService;

    private NotificationProviderService     notificationProviderService;
    private SwitchConnectionProvider        switchConnectionProvider;
    private ExtensionConverterRegistrator   extensionConverterRegistrator;
    private NiciraExtensionCodecRegistrator niciraExtensionCodecRegistrator;

    private RpcRegistration<LabTestService>       labTestServiceRpcRegistration;
    private RpcRegistration<LabHealthCareService> labHealthCareServiceRpcRegistration;

    private LabExtensionRegister labExtensionRegister;
    private DataStoreListener    dataStoreListener;
    private ErrorListener        errorListener;
    private PacketInListener     packetInListener;

    public LabcontrollerProvider(SwitchConnectionProvider switchConnectionProvider, NotificationProviderService notificationProviderService,
                                 ExtensionConverterRegistrator extensionConverterRegistrator,
                                 NiciraExtensionCodecRegistrator niciraExtensionCodecRegistrator) {
        this.notificationProviderService = notificationProviderService;
        this.switchConnectionProvider = switchConnectionProvider;
        this.extensionConverterRegistrator = extensionConverterRegistrator;
        this.niciraExtensionCodecRegistrator = niciraExtensionCodecRegistrator;
        this.labExtensionRegister = new LabExtensionRegister(switchConnectionProvider, niciraExtensionCodecRegistrator,
                extensionConverterRegistrator);
    }

    @Override
    public void onSessionInitiated(ProviderContext session) {
        this.dataBroker = session.getSALService(DataBroker.class);
        this.salFlowService = session.getRpcService(SalFlowService.class);
        this.salMeterService = session.getRpcService(SalMeterService.class);
        this.salPortService = session.getRpcService(SalPortService.class);
        this.salGroupService = session.getRpcService(SalGroupService.class);
        this.salQueueService = session.getRpcService(SalQueueService.class);
        this.salExperimenterMessageService = session.getRpcService(SalExperimenterMessageService.class);
        this.flowStatisticsService = session.getRpcService(OpendaylightFlowStatisticsService.class);
        this.meterStatisticsService = session.getRpcService(OpendaylightMeterStatisticsService.class);
        this.portStatisticsService = session.getRpcService(OpendaylightPortStatisticsService.class);
        this.packetProcessingService = session.getRpcService(PacketProcessingService.class);

        this.labTestServiceRpcRegistration = session.addRpcImplementation(LabTestService.class,
                new LabTestServiceImpl(salFlowService, salExperimenterMessageService));
        this.labHealthCareServiceRpcRegistration = session.addRpcImplementation(LabHealthCareService.class, new LabHealthCareServiceImpl());

        labExtensionRegister.registerExtension();

        this.dataStoreListener = new DataStoreListener(dataBroker);
        this.errorListener = new ErrorListener(notificationProviderService);
        this.packetInListener = new PacketInListener(notificationProviderService);

        this.dataStoreListener.init();
        this.errorListener.init();
        this.packetInListener.init();
        LOG.info("LabcontrollerProvider Session Initiated");
    }

    @Override
    public void close() throws Exception {

        this.dataStoreListener.close();
        this.errorListener.close();
        this.packetInListener.close();

        this.labTestServiceRpcRegistration.close();
        this.labHealthCareServiceRpcRegistration.close();
        labExtensionRegister.unregisterExtension();
        LOG.info("LabcontrollerProvider Closed");
    }

}
