package common.extension.match;

import io.netty.buffer.ByteBuf;
import org.opendaylight.openflowjava.nx.codec.match.AbstractMatchCodec;
import org.opendaylight.openflowjava.protocol.api.keys.MatchEntryDeserializerKey;
import org.opendaylight.openflowjava.protocol.api.keys.MatchEntrySerializerKey;
import org.opendaylight.openflowjava.protocol.api.util.EncodeConstants;
import org.opendaylight.openflowjava.protocol.api.util.OxmMatchConstants;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.match.ofj.ext.rev180421.TestMatchCondition;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.match.ofj.ext.rev180421.oxm.container.match.entry.value.TestMatchConditionCase;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.match.ofj.ext.rev180421.oxm.container.match.entry.value.TestMatchConditionCaseBuilder;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.match.ofj.ext.rev180421.test.match.condition.grouping.TestMatchConditionValue;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.match.ofj.ext.rev180421.test.match.condition.grouping.TestMatchConditionValueBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.MatchField;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.Nxm1Class;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.OxmClassBase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.match.entries.grouping.MatchEntry;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.match.entries.grouping.MatchEntryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xietaojie1992
 */
public class MatchConditionCodec extends AbstractMatchCodec {

    private static final Logger                                                 LOGGER                    = LoggerFactory.getLogger(
            MatchConditionCodec.class);
    public static final  int                                                    NXM_NX_MATCH_CONDITION_ID = 1000;
    public static final  int                                                    LENGTH                    = 4;
    public static final  MatchEntrySerializerKey<Nxm1Class, TestMatchCondition> SERIALIZER_KEY            = new MatchEntrySerializerKey<>(
            EncodeConstants.OF13_VERSION_ID, Nxm1Class.class, TestMatchCondition.class);
    public static final  MatchEntryDeserializerKey                              DESERIALIZER_KEY          = new MatchEntryDeserializerKey(
            EncodeConstants.OF13_VERSION_ID, OxmMatchConstants.NXM_1_CLASS, NXM_NX_MATCH_CONDITION_ID);

    @Override
    public int getNxmFieldCode() {
        return NXM_NX_MATCH_CONDITION_ID;
    }

    @Override
    public int getOxmClassCode() {
        return OxmMatchConstants.NXM_1_CLASS;
    }

    @Override
    public int getValueLength() {
        return LENGTH;
    }

    @Override
    public Class<? extends MatchField> getNxmField() {
        return TestMatchCondition.class;
    }

    @Override
    public Class<? extends OxmClassBase> getOxmClass() {
        return Nxm1Class.class;
    }

    @Override
    public MatchEntry deserialize(ByteBuf message) {
        MatchEntryBuilder matchEntryBuilder = deserializeHeaderToBuilder(message);
        TestMatchConditionValueBuilder valueBuilder = new TestMatchConditionValueBuilder();
        valueBuilder.setCondition(Long.valueOf(message.readUnsignedInt()));
        TestMatchConditionCaseBuilder caseBuilder = new TestMatchConditionCaseBuilder();
        caseBuilder.setTestMatchConditionValue(valueBuilder.build());
        matchEntryBuilder.setMatchEntryValue(caseBuilder.build());
        return matchEntryBuilder.build();
    }

    @Override
    public void serialize(MatchEntry input, ByteBuf outBuffer) {
        serializeHeader(input, outBuffer);
        TestMatchConditionCase testMatchConditionCase = (TestMatchConditionCase) input.getMatchEntryValue();
        TestMatchConditionValue testMatchConditionValue = testMatchConditionCase.getTestMatchConditionValue();
        outBuffer.writeInt(testMatchConditionValue.getCondition().intValue());
    }
}
