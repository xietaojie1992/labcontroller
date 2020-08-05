/*
 * Copyright © 2019 xietaojie and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.xietaojie.lab.impl;

import org.opendaylight.mdsal.binding.api.DataBroker;
import org.opendaylight.mdsal.binding.api.NotificationPublishService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.experimenter.message.service.rev151020.SalExperimenterMessageService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.service.rev130819.SalFlowService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.service.rev130918.SalMeterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LabcontrollerProvider {

    private static final Logger LOG = LoggerFactory.getLogger(LabcontrollerProvider.class);

    private final DataBroker                    dataBroker;
    private final NotificationPublishService    notificationPublishService;
    private final SalFlowService                salFlowService;
    private final SalMeterService               salMeterService;
    private final SalExperimenterMessageService salExperimenterMessageService;

    public LabcontrollerProvider(final DataBroker dataBroker, NotificationPublishService notificationPublishService,
                                 SalFlowService salFlowService, SalMeterService salMeterService,
                                 SalExperimenterMessageService salExperimenterMessageService) {
        this.dataBroker = dataBroker;
        this.notificationPublishService = notificationPublishService;
        this.salFlowService = salFlowService;
        this.salMeterService = salMeterService;
        this.salExperimenterMessageService = salExperimenterMessageService;
    }

    /**
     * Method called when the blueprint container is created.
     */
    public void init() {
        LOG.info("LabcontrollerProvider Session Initiated");
    }

    /**
     * Method called when the blueprint container is destroyed.
     */
    public void close() {
        LOG.info("LabcontrollerProvider Closed");
    }
}