package com.xietaojie.lab.impl;

import org.opendaylight.openflowplugin.api.openflow.md.core.session.SessionContext;
import org.opendaylight.openflowplugin.api.openflow.md.core.session.SwitchSessionKeyOF;
import org.opendaylight.openflowplugin.openflow.md.core.session.SessionManagerOFImpl;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.health.care.rev180324.IsConnectionValidInput;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.health.care.rev180324.IsConnectionValidOutput;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.health.care.rev180324.IsConnectionValidOutputBuilder;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.health.care.rev180324.LabHealthCareService;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.health.care.rev180324.ReconnectSessionInput;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.health.care.rev180324.ReconnectSessionOutput;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.health.care.rev180324.ReconnectSessionOutputBuilder;
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
public class LabHealthCareServiceImpl implements LabHealthCareService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LabHealthCareServiceImpl.class);

    @Override
    public Future<RpcResult<IsConnectionValidOutput>> isConnectionValid(IsConnectionValidInput input) {
        IsConnectionValidOutputBuilder outputBuilder = new IsConnectionValidOutputBuilder();
        SwitchSessionKeyOF sessionKeyOF = new SwitchSessionKeyOF();
        sessionKeyOF.setDatapathId(new BigInteger(input.getDpid().split(":")[1], 10));
        SessionContext sessionContext = SessionManagerOFImpl.getInstance().getSessionContext(sessionKeyOF);
        if (sessionContext == null) {
            LOGGER.error("session is null");
            outputBuilder.setCode("SESSION_NOT_FOUND");
            outputBuilder.setMessage("Session is null");
        } else {
            outputBuilder.setFlag(sessionContext.isValid());
        }

        return RpcResultBuilder.success(outputBuilder.build()).buildFuture();
    }

    @Override
    public Future<RpcResult<ReconnectSessionOutput>> reconnectSession(ReconnectSessionInput input) {
        ReconnectSessionOutputBuilder outputBuilder = new ReconnectSessionOutputBuilder();
        SwitchSessionKeyOF sessionKeyOF = new SwitchSessionKeyOF();
        sessionKeyOF.setDatapathId(new BigInteger(input.getDpid().split(":")[1], 10));
        SessionContext sessionContext = SessionManagerOFImpl.getInstance().getSessionContext(sessionKeyOF);
        if (sessionContext == null) {
            LOGGER.error("session is null");
            outputBuilder.setCode("SESSION_NOT_FOUND");
            outputBuilder.setMessage("Session is null");
        } else {
            LOGGER.warn("session[{}] isValid={}, reconnect.", sessionKeyOF, sessionContext.isValid());
            try {
                boolean result = sessionContext.getPrimaryConductor().getConnectionAdapter().disconnect().get();
                outputBuilder.setFlag(result);
                outputBuilder.setCode("DISCONNECT_SESSION");
                outputBuilder.setMessage(result ? "reconnect success" : "reconnect fail");
            } catch (InterruptedException | ExecutionException e) {
                LOGGER.error("", e);
                outputBuilder.setCode("DISCONNECT_SESSION");
                outputBuilder.setMessage(e.getMessage());
            }
        }
        return RpcResultBuilder.success(outputBuilder.build()).buildFuture();
    }
}
