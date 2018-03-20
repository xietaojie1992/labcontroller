/*
 * Copyright © 2018 xietaojie1992@github and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.labcontroller.impl.rev141210;

import com.google.common.base.Preconditions;
import com.xietaojie.lab.impl.LabcontrollerProvider;
import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.openflowjava.nx.api.NiciraExtensionCodecRegistrator;
import org.opendaylight.openflowjava.protocol.spi.connection.SwitchConnectionProvider;
import org.opendaylight.openflowplugin.extension.api.ExtensionConverterRegistrator;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingService;

public class LabcontrollerModule
        extends org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.labcontroller.impl.rev141210.AbstractLabcontrollerModule {
    public LabcontrollerModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier,
                               org.opendaylight.controller.config.api.DependencyResolver dependencyResolver) {
        super(identifier, dependencyResolver);
    }

    public LabcontrollerModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier,
                               org.opendaylight.controller.config.api.DependencyResolver dependencyResolver,
                               org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.labcontroller.impl
                                       .rev141210.LabcontrollerModule oldModule,
                               java.lang.AutoCloseable oldInstance) {
        super(identifier, dependencyResolver, oldModule, oldInstance);
    }

    @Override
    public void customValidation() {
        // add custom validation form module attributes here.
    }

    @Override
    public java.lang.AutoCloseable createInstance() {
        NiciraExtensionCodecRegistrator niciraExtensionCodecRegistrator = getNiciraExtensionCodecRegistratorDependency();
        SwitchConnectionProvider switchConnectionProvider = getOpenflowSwitchConnectionProviderDependency();
        ExtensionConverterRegistrator extensionConverterRegistrator = getOpenflowPluginExtensionRegistryDependency()
                .getExtensionConverterRegistrator();
        NotificationProviderService notificationProviderService = getNotificationServiceDependency();
        Preconditions.checkNotNull(niciraExtensionCodecRegistrator, " can not be null");
        Preconditions.checkNotNull(switchConnectionProvider, " can not be null");
        Preconditions.checkNotNull(extensionConverterRegistrator, " can not be null");
        Preconditions.checkNotNull(notificationProviderService, "NotificationProviderService can not be null");

        LabcontrollerProvider provider = new LabcontrollerProvider(switchConnectionProvider, notificationProviderService,
                extensionConverterRegistrator, niciraExtensionCodecRegistrator);
        getBrokerDependency().registerProvider(provider);
        return provider;
    }

}
