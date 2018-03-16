/*
 * Copyright © 2018 xietaojie1992@github and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.xietaojie.lab.impl;

import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.ProviderContext;
import org.opendaylight.controller.sal.binding.api.BindingAwareProvider;
import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.openflowjava.nx.api.NiciraExtensionCodecRegistrator;
import org.opendaylight.openflowjava.protocol.spi.connection.SwitchConnectionProvider;
import org.opendaylight.openflowplugin.extension.api.ExtensionConverterRegistrator;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LabcontrollerProvider implements BindingAwareProvider, AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(LabcontrollerProvider.class);

    private NotificationProviderService     notificationProviderService;
    private PacketProcessingService         packetProcessingService;
    private SwitchConnectionProvider        switchConnectionProvider;
    private ExtensionConverterRegistrator   extensionConverterRegistrator;
    private NiciraExtensionCodecRegistrator niciraExtensionCodecRegistrator;

    public LabcontrollerProvider(SwitchConnectionProvider switchConnectionProvider, NotificationProviderService notificationProviderService,
                                 PacketProcessingService packetProcessingService,
                                 ExtensionConverterRegistrator extensionConverterRegistrator,
                                 NiciraExtensionCodecRegistrator niciraExtensionCodecRegistrator) {
        this.notificationProviderService = notificationProviderService;
        this.packetProcessingService = packetProcessingService;
        this.switchConnectionProvider = switchConnectionProvider;
        this.extensionConverterRegistrator = extensionConverterRegistrator;
        this.niciraExtensionCodecRegistrator = niciraExtensionCodecRegistrator;
    }

    @Override
    public void onSessionInitiated(ProviderContext session) {
        LOG.info("LabcontrollerProvider Session Initiated");
    }

    @Override
    public void close() throws Exception {
        LOG.info("LabcontrollerProvider Closed");
    }

}
