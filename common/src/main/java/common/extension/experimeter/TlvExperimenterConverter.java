package common.extension.experimeter;

import common.extension.LabExtensionConstants;
import org.opendaylight.openflowplugin.extension.api.ConverterMessageToOFJava;
import org.opendaylight.openflowplugin.extension.api.ConvertorMessageFromOFJava;
import org.opendaylight.openflowplugin.extension.api.ExtensionConvertorData;
import org.opendaylight.openflowplugin.extension.api.exception.ConversionException;
import org.opendaylight.openflowplugin.extension.api.path.MessagePath;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.experimenter.mdsal.ext.rev180317.send.experimenter.input.experimenter.message.of.choice.LabTlvExperimenterMessageSalCase;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.experimenter.mdsal.ext.rev180317.send.experimenter.input.experimenter.message.of.choice.LabTlvExperimenterMessageSalCaseBuilder;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.experimenter.mdsal.ext.rev180317.send.experimenter.input.experimenter.message.of.choice.lab.tlv.experimenter.message.sal._case.LabTlvExperimenterMessageSalDataBuilder;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.experimenter.ofj.ext.rev180317.experimenter.input.experimenter.data.of.choice.LabTlvExperimenterMessageCase;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.experimenter.ofj.ext.rev180317.experimenter.input.experimenter.data.of.choice.LabTlvExperimenterMessageCaseBuilder;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.experimenter.ofj.ext.rev180317.experimenter.input.experimenter.data.of.choice.lab.tlv.experimenter.message._case.LabTlvExperimenterMessageDataBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.types.rev130731.ExperimenterId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.experimenter.types.rev151020.experimenter.core.message.ExperimenterMessageOfChoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xietaojie1992
 */
public class TlvExperimenterConverter
        implements ConverterMessageToOFJava<LabTlvExperimenterMessageSalCase, LabTlvExperimenterMessageCase, ExtensionConvertorData>,
        ConvertorMessageFromOFJava<LabTlvExperimenterMessageSalCase, MessagePath> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlvExperimenterConverter.class);

    public ExperimenterMessageOfChoice convert(LabTlvExperimenterMessageCase input, MessagePath path) throws ConversionException {
        LOGGER.info("convert LabTlvExperimenterMessageCase:{} into ExperimenterMessageOfChoice:{}");
        return new LabTlvExperimenterMessageSalCaseBuilder().setLabTlvExperimenterMessageSalData(
                new LabTlvExperimenterMessageSalDataBuilder(input.getLabTlvExperimenterMessageData()).build()).build();
    }

    public LabTlvExperimenterMessageCase convert(LabTlvExperimenterMessageSalCase experimenterMessageCase) throws ConversionException {
        LOGGER.info("convert LabTlvExperimenterMessageSalCase:{} into LabTlvExperimenterMessageCase:{}");
        return new LabTlvExperimenterMessageCaseBuilder().setLabTlvExperimenterMessageData(
                new LabTlvExperimenterMessageDataBuilder(experimenterMessageCase.getLabTlvExperimenterMessageSalData()).build()).build();
    }

    @Override
    public LabTlvExperimenterMessageCase convert(LabTlvExperimenterMessageSalCase experimenterMessageCase, ExtensionConvertorData data)
            throws ConversionException {
        LOGGER.info("convert LabTlvExperimenterMessageSalCase:{} into LabTlvExperimenterMessageCase:{}");
        return new LabTlvExperimenterMessageCaseBuilder().setLabTlvExperimenterMessageData(
                new LabTlvExperimenterMessageDataBuilder(experimenterMessageCase.getLabTlvExperimenterMessageSalData()).build()).build();
    }

    @Override
    public ExperimenterId getExperimenterId() {
        LOGGER.info("getExperimenterId: {}", LabExtensionConstants.LAB_EXPERIMENTER_ID);
        return new ExperimenterId(LabExtensionConstants.LAB_EXPERIMENTER_ID);
    }

    @Override
    public long getType() {
        LOGGER.info("getType:{}", LabExtensionConstants.LAB_EXPERIMENTER_TYPE_TLV);
        return LabExtensionConstants.LAB_EXPERIMENTER_TYPE_TLV;
    }

    @Override
    public LabTlvExperimenterMessageSalCase convert(LabTlvExperimenterMessageSalCase input, MessagePath path) throws ConversionException {
        LOGGER.info("convert LabTlvExperimenterMessageCase:{} into ExperimenterMessageOfChoice:{}");
        return new LabTlvExperimenterMessageSalCaseBuilder().setLabTlvExperimenterMessageSalData(
                new LabTlvExperimenterMessageSalDataBuilder(input.getLabTlvExperimenterMessageSalData()).build()).build();
    }
}
