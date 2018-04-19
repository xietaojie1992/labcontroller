package com.xietaojie.lab.impl.extension.experimeter;

import com.xietaojie.lab.impl.extension.LabExtensionConstants;
import org.opendaylight.openflowjava.protocol.api.keys.ExperimenterIdTypeSerializerKey;
import org.opendaylight.openflowjava.protocol.spi.connection.SwitchConnectionProvider;
import org.opendaylight.openflowplugin.api.OFConstants;
import org.opendaylight.openflowplugin.extension.api.ExtensionConverterRegistrator;
import org.opendaylight.openflowplugin.extension.api.TypeVersionKey;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.experimenter.mdsal.ext.rev180317.send.experimenter.input.experimenter
        .message.of.choice.LabTlvExperimenterMessageSalCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.protocol.rev130731.experimenter.core.ExperimenterDataOfChoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xietaojie1992
 */
public class TlvExperimenterRegister {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlvExperimenterRegister.class);
    private final SwitchConnectionProvider      switchConnectionProvider;
    private final ExtensionConverterRegistrator converterRegistrator;
    private static final TlvExperimenterCodec     TLV_EXPERIMENTER_CODEC     = new TlvExperimenterCodec();
    private static final TlvExperimenterConverter TLV_EXPERIMENTER_CONVERTER = new TlvExperimenterConverter();

    public TlvExperimenterRegister(final SwitchConnectionProvider switchConnectionProvider,
                                   final ExtensionConverterRegistrator converterRegistrator) {
        this.switchConnectionProvider = switchConnectionProvider;
        this.converterRegistrator = converterRegistrator;
    }

    public void init() {
        LOGGER.info("TlvExperimenterRegister init");
        registerCodecs();
        registerConverters();
    }

    private void registerCodecs() {
        switchConnectionProvider.registerExperimenterMessageSerializer(
                new ExperimenterIdTypeSerializerKey<>(OFConstants.OFP_VERSION_1_3, LabExtensionConstants.LAB_EXPERIMENTER_ID,
                        LabExtensionConstants.LAB_EXPERIMENTER_TYPE_TLV, ExperimenterDataOfChoice.class), TLV_EXPERIMENTER_CODEC);
    }

    private void registerConverters() {
        converterRegistrator.registerMessageConvertor(
                new TypeVersionKey<>(LabTlvExperimenterMessageSalCase.class, OFConstants.OFP_VERSION_1_3), TLV_EXPERIMENTER_CONVERTER);
    }
}
