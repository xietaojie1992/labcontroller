package common.extension.action;

import io.netty.buffer.ByteBuf;
import org.opendaylight.openflowjava.nx.api.NiciraActionDeserializerKey;
import org.opendaylight.openflowjava.nx.api.NiciraActionSerializerKey;
import org.opendaylight.openflowjava.nx.codec.action.AbstractActionCodec;
import org.opendaylight.openflowjava.protocol.api.util.EncodeConstants;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.action.ofj.ext.rev180421.action.container.action.choice.TestActionMovementCase;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.action.ofj.ext.rev180421.action.container.action.choice.TestActionMovementCaseBuilder;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.action.ofj.ext.rev180421.test.action.movement.grouping.TestActionMovementValueBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.actions.grouping.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.actions.grouping.ActionBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xietaojie1992
 */
public class ActionMovementCodec extends AbstractActionCodec {

    private static final Logger                      LOGGER           = LoggerFactory.getLogger(ActionMovementCodec.class);
    public static final  int                         LENGTH           = 16;
    public static final  int                         SUBTYPE          = 20001;
    public static final  byte                        PADDING          = EncodeConstants.SIZE_OF_SHORT_IN_BYTES;
    public static final  NiciraActionSerializerKey   SERIALIZER_KEY   = new NiciraActionSerializerKey(EncodeConstants.OF13_VERSION_ID,
            TestActionMovementCase.class);
    public static final  NiciraActionDeserializerKey DESERIALIZER_KEY = new NiciraActionDeserializerKey(EncodeConstants.OF13_VERSION_ID,
            SUBTYPE);

    @Override
    public void serialize(Action input, ByteBuf outBuffer) {
        LOGGER.info("serialize Action : {}", input);
        TestActionMovementCase movementCase = (TestActionMovementCase) input.getActionChoice();
        LOGGER.info("TestActionMovementCase = {}", movementCase);
        serializeHeader(LENGTH, SUBTYPE, outBuffer);
        outBuffer.writeZero(PADDING);
        int movement = movementCase.getTestActionMovementValue().getMovement().intValue();
        outBuffer.writeInt(movement);
    }

    @Override
    public Action deserialize(ByteBuf message) {
        LOGGER.info("deserialize");
        ActionBuilder actionBuilder = deserializeHeader(message);
        message.skipBytes(PADDING);
        TestActionMovementCaseBuilder movementCaseBuilder = new TestActionMovementCaseBuilder();
        TestActionMovementValueBuilder movementValueBuilder = new TestActionMovementValueBuilder();
        movementValueBuilder.setMovement(message.readUnsignedInt());
        movementCaseBuilder.setTestActionMovementValue(movementValueBuilder.build());
        actionBuilder.setActionChoice(movementCaseBuilder.build());
        LOGGER.info("{}", actionBuilder.build());
        return actionBuilder.build();
    }
}
