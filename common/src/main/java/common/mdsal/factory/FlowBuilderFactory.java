package common.mdsal.factory;

import common.constants.LabControllerConstants;
import common.constants.ProtocolNumber;
import org.opendaylight.openflowplugin.api.OFConstants;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.FlowId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.FlowBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.FlowKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.FlowCookie;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.FlowModFlags;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.Instructions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.Match;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.MatchBuilder;

import java.math.BigInteger;

/**
 * @author xietaojie1992
 */
public class FlowBuilderFactory {

    public static FlowBuilder createAddFlowBuilder(BigInteger cookie, BigInteger cookieMask, Integer priority, Match match,
                                                   Instructions instructions, boolean isBarrier) {
        FlowBuilder flowBuilder = new FlowBuilder();
        flowBuilder.setId(new FlowId(cookie.toString())).withKey(new FlowKey(flowBuilder.getId()));
        flowBuilder.setPriority(priority).setMatch(match).setInstructions(instructions);
        flowBuilder.setBarrier(isBarrier);
        flowBuilder.setCookie(new FlowCookie(cookie)).setCookieMask(new FlowCookie(cookieMask));
        flowBuilder.setHardTimeout(0).setIdleTimeout(0);
        flowBuilder.setInstallHw(false);
        flowBuilder.setStrict(false);
        flowBuilder.setFlags(new FlowModFlags(false, false, false, false, true));
        flowBuilder.setTableId(LabControllerConstants.DEFAULT_TABLE_ID).setBufferId(OFConstants.OFP_NO_BUFFER);

        return flowBuilder;
    }

    public static FlowBuilder createRemoveFlowBuilder(BigInteger cookie, BigInteger cookieMask, MatchBuilder matchBuilder,
                                                      boolean isBarrier) {
        FlowBuilder flowBuilder = new FlowBuilder();
        flowBuilder.setId(new FlowId(cookie.toString())).withKey(new FlowKey(flowBuilder.getId()));
        flowBuilder.setMatch(matchBuilder.build());
        flowBuilder.setCookie(new FlowCookie(cookie)).setCookieMask(new FlowCookie(cookieMask));
        flowBuilder.setTableId(LabControllerConstants.DEFAULT_TABLE_ID);
        flowBuilder.setBarrier(isBarrier);
        return flowBuilder;
    }

    /////////////////////// Examples ////////////////////////////

    public static FlowBuilder createLldpFlowBuilder(String cookieString, int priority, boolean isBarrier) {
        BigInteger cookie = new BigInteger(cookieString, 16);
        BigInteger cookieMask = LabControllerConstants.COOKIE_MASK;
        Match match = FlowMatchBuilder.builder().createEtherTypeMatch(ProtocolNumber.TYPE_LLDP).build();
        Instructions instructions = FlowInstructionsBuilder.builder().createControllerInstructions().build();
        return createAddFlowBuilder(cookie, cookieMask, priority, match, instructions, isBarrier);
    }

}
