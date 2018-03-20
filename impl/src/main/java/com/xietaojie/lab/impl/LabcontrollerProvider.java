/*
 * Copyright © 2018 xietaojie1992@github and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.xietaojie.lab.impl;

import com.xietaojie.lab.impl.extension.TlvExperimenterRegister;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.ProviderContext;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.RpcRegistration;
import org.opendaylight.controller.sal.binding.api.BindingAwareProvider;
import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.openflowjava.nx.api.NiciraExtensionCodecRegistrator;
import org.opendaylight.openflowjava.protocol.spi.connection.SwitchConnectionProvider;
import org.opendaylight.openflowplugin.extension.api.ExtensionConverterRegistrator;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.test.rev180317.LabTestService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.experimenter.message.service.rev151020.SalExperimenterMessageService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.service.rev130819.SalFlowService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.statistics.rev130819.OpendaylightFlowStatisticsService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.group.service.rev130918.SalGroupService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.service.rev130918.SalMeterService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.statistics.rev131111.OpendaylightMeterStatisticsService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.port.service.rev131107.SalPortService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.port.statistics.rev131214.OpendaylightPortStatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LabcontrollerProvider implements BindingAwareProvider, AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(LabcontrollerProvider.class);

    private DataBroker                         dataBroker;
    private SalFlowService                     salFlowService;
    private SalMeterService                    salMeterService;
    private SalPortService                     salPortService;
    private SalGroupService                    salGroupService;
    private PacketProcessingService            packetProcessingService;
    private SalExperimenterMessageService      salExperimenterMessageService;
    private OpendaylightFlowStatisticsService  flowStatisticsService;
    private OpendaylightMeterStatisticsService meterStatisticsService;
    private OpendaylightPortStatisticsService  portStatisticsService;

    private NotificationProviderService     notificationProviderService;
    private SwitchConnectionProvider        switchConnectionProvider;
    private ExtensionConverterRegistrator   extensionConverterRegistrator;
    private NiciraExtensionCodecRegistrator niciraExtensionCodecRegistrator;

    private RpcRegistration<LabTestService> labTestServiceRpcRegistration;

    public LabcontrollerProvider(SwitchConnectionProvider switchConnectionProvider, NotificationProviderService notificationProviderService,
                                 ExtensionConverterRegistrator extensionConverterRegistrator,
                                 NiciraExtensionCodecRegistrator niciraExtensionCodecRegistrator) {
        this.notificationProviderService = notificationProviderService;
        this.switchConnectionProvider = switchConnectionProvider;
        this.extensionConverterRegistrator = extensionConverterRegistrator;
        this.niciraExtensionCodecRegistrator = niciraExtensionCodecRegistrator;
    }

    @Override
    public void onSessionInitiated(ProviderContext session) {
        this.dataBroker = session.getSALService(DataBroker.class);
        this.salFlowService = session.getRpcService(SalFlowService.class);
        this.salMeterService = session.getRpcService(SalMeterService.class);
        this.salPortService = session.getRpcService(SalPortService.class);
        this.salGroupService = session.getRpcService(SalGroupService.class);
        this.salExperimenterMessageService = session.getRpcService(SalExperimenterMessageService.class);
        this.flowStatisticsService = session.getRpcService(OpendaylightFlowStatisticsService.class);
        this.meterStatisticsService = session.getRpcService(OpendaylightMeterStatisticsService.class);
        this.portStatisticsService = session.getRpcService(OpendaylightPortStatisticsService.class);
        this.packetProcessingService = session.getRpcService(PacketProcessingService.class);

        this.labTestServiceRpcRegistration = session.addRpcImplementation(LabTestService.class, new LabTestServiceImpl(salExperimenterMessageService));

        new TlvExperimenterRegister(switchConnectionProvider, extensionConverterRegistrator).init();

        LOG.info("LabcontrollerProvider Session Initiated");
    }

    @Override
    public void close() throws Exception {
        labTestServiceRpcRegistration.close();

        LOG.info("LabcontrollerProvider Closed");
    }

}
