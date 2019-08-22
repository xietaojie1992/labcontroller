/*
 * Copyright © 2019 xietaojie and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.xietaojie.lab.cli.impl;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import com.xietaojie.lab.cli.api.LabcontrollerCliCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LabcontrollerCliCommandsImpl implements LabcontrollerCliCommands {

    private static final Logger LOG = LoggerFactory.getLogger(LabcontrollerCliCommandsImpl.class);
    private final DataBroker dataBroker;

    public LabcontrollerCliCommandsImpl(final DataBroker db) {
        this.dataBroker = db;
        LOG.info("LabcontrollerCliCommandImpl initialized");
    }

    @Override
    public Object testCommand(Object testArgument) {
        return "This is a test implementation of test-command";
    }
}
