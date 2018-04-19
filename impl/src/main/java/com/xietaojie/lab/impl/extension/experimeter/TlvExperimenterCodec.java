package com.xietaojie.lab.impl.extension.experimeter;

import com.xietaojie.lab.impl.extension.LabExtensionConstants;
import io.netty.buffer.ByteBuf;
import org.opendaylight.openflowjava.protocol.api.extensibility.DeserializerRegistry;
import org.opendaylight.openflowjava.protocol.api.extensibility.DeserializerRegistryInjector;
import org.opendaylight.openflowjava.protocol.api.extensibility.OFDeserializer;
import org.opendaylight.openflowjava.protocol.api.extensibility.OFSerializer;
import org.opendaylight.openflowjava.protocol.api.extensibility.SerializerRegistry;
import org.opendaylight.openflowjava.protocol.api.extensibility.SerializerRegistryInjector;
import org.opendaylight.openflowjava.protocol.api.keys.ExperimenterIdTypeDeserializerKey;
import org.opendaylight.openflowjava.protocol.api.keys.ExperimenterIdTypeSerializerKey;
import org.opendaylight.openflowjava.util.ExperimenterDeserializerKeyFactory;
import org.opendaylight.openflowplugin.api.OFConstants;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.experimenter.ofj.ext.rev180317.LabTlvType;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.experimenter.ofj.ext.rev180317.experimenter.input.experimenter.data.of
        .choice.LabTlvExperimenterMessageCase;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.experimenter.ofj.ext.rev180317.experimenter.input.experimenter.data.of
        .choice.LabTlvExperimenterMessageCaseBuilder;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.experimenter.ofj.ext.rev180317.experimenter.input.experimenter.data.of
        .choice.lab.tlv.experimenter.message._case.LabTlvExperimenterMessageDataBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.protocol.rev130731.experimenter.core.ExperimenterDataOfChoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xietaojie1992
 */
public class TlvExperimenterCodec
        implements DeserializerRegistryInjector, SerializerRegistryInjector, OFSerializer<LabTlvExperimenterMessageCase>,
        OFDeserializer<LabTlvExperimenterMessageCase> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlvExperimenterCodec.class);
    private SerializerRegistry   serializerRegistry;
    private DeserializerRegistry deserializerRegistry;
    public static final ExperimenterIdTypeSerializerKey<ExperimenterDataOfChoice> SERIALIZER_KEY   = new ExperimenterIdTypeSerializerKey<>(
            OFConstants.OFP_VERSION_1_3, LabExtensionConstants.LAB_EXPERIMENTER_ID, LabExtensionConstants.LAB_EXPERIMENTER_TYPE_TLV,
            ExperimenterDataOfChoice.class);

    public static final ExperimenterIdTypeDeserializerKey                         DESERIALIZER_KEY = new ExperimenterIdTypeDeserializerKey(
            OFConstants.OFP_VERSION_1_3, LabExtensionConstants.LAB_EXPERIMENTER_ID, LabExtensionConstants.LAB_EXPERIMENTER_TYPE_TLV,
            ExperimenterDataOfChoice.class);

    @Override
    public void injectSerializerRegistry(SerializerRegistry serializerRegistry) {
        this.serializerRegistry = serializerRegistry;
    }

    @Override
    public void injectDeserializerRegistry(DeserializerRegistry deserializerRegistry) {
        this.deserializerRegistry = deserializerRegistry;
    }

    @Override
    public void serialize(LabTlvExperimenterMessageCase input, ByteBuf outBuffer) {
        LOGGER.debug("serialize LabTlvExperimenterMessageData:{}", input.getLabTlvExperimenterMessageData());
        outBuffer.writeShort(input.getLabTlvExperimenterMessageData().getType().getIntValue());
        outBuffer.writeShort(input.getLabTlvExperimenterMessageData().getLength().shortValue());
        outBuffer.writeBytes(input.getLabTlvExperimenterMessageData().getValue());
    }

    @Override
    public LabTlvExperimenterMessageCase deserialize(ByteBuf message) {
        LabTlvExperimenterMessageDataBuilder dataBuilder = new LabTlvExperimenterMessageDataBuilder();
        dataBuilder.setType(LabTlvType.forValue(Integer.valueOf(message.readUnsignedShort())));
        int length = Integer.valueOf(message.readUnsignedShort());
        dataBuilder.setLength(length);
        dataBuilder.setValue(message.readBytes(length).array());
        LOGGER.debug("deserialize as LabTlvExperimenterMessageData:{}", dataBuilder.build());
        return new LabTlvExperimenterMessageCaseBuilder().setLabTlvExperimenterMessageData(dataBuilder.build()).build();
    }
}
