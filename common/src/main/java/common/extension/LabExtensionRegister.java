package common.extension;

import common.extension.action.ActionMovementCodec;
import common.extension.action.ActionMovementConverter;
import common.extension.experimeter.TlvExperimenterCodec;
import common.extension.experimeter.TlvExperimenterConverter;
import common.extension.match.MatchConditionCodec;
import common.extension.match.MatchConditionConverter;
import org.opendaylight.openflowjava.nx.api.NiciraActionSerializerKey;
import org.opendaylight.openflowjava.nx.api.NiciraExtensionCodecRegistrator;
import org.opendaylight.openflowjava.nx.api.NiciraUtil;
import org.opendaylight.openflowjava.protocol.api.keys.ActionSerializerKey;
import org.opendaylight.openflowjava.protocol.api.keys.MessageTypeKey;
import org.opendaylight.openflowjava.protocol.api.util.EncodeConstants;
import org.opendaylight.openflowjava.protocol.spi.connection.SwitchConnectionProvider;
import org.opendaylight.openflowplugin.extension.api.ConverterExtensionKey;
import org.opendaylight.openflowplugin.extension.api.ConverterMessageToOFJava;
import org.opendaylight.openflowplugin.extension.api.ConvertorActionFromOFJava;
import org.opendaylight.openflowplugin.extension.api.ConvertorActionToOFJava;
import org.opendaylight.openflowplugin.extension.api.ConvertorMessageFromOFJava;
import org.opendaylight.openflowplugin.extension.api.ExtensionConverterRegistrator;
import org.opendaylight.openflowplugin.extension.api.TypeVersionKey;
import org.opendaylight.openflowplugin.extension.api.path.ActionPath;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.action.mdsal.ext.rev180421.add.flow.input.instructions.instruction.instruction.apply.actions._case.apply.actions.action.action.TestActionMovementRpcAddFlowApplyActionsCase;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.experimenter.mdsal.ext.rev180317.send.experimenter.input.experimenter.message.of.choice.LabTlvExperimenterMessageSalCase;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.experimenter.ofj.ext.rev180317.experimenter.input.experimenter.data.of.choice.LabTlvExperimenterMessageCase;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.match.mdsal.ext.rev180421.TestMatchConditionKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.protocol.rev130731.experimenter.core.ExperimenterDataOfChoice;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.experimenter.types.rev151020.experimenter.core.message.ExperimenterMessageOfChoice;
import org.opendaylight.yangtools.concepts.ObjectRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @author xietaojie1992
 */
public class LabExtensionRegister {

    private static final Logger                          LOGGER = LoggerFactory.getLogger(LabExtensionRegister.class);
    private final        SwitchConnectionProvider        switchConnectionProvider;
    private final        NiciraExtensionCodecRegistrator niciraExtensionCodecRegistrator;
    private final        ExtensionConverterRegistrator   extensionConverterRegistrator;
    private              Set<ObjectRegistration<?>>      registrations;

    private MatchConditionCodec     matchConditionCodec;
    private MatchConditionConverter matchConditionConverter;

    private ActionMovementCodec     actionMovementCodec;
    private ActionMovementConverter actionMovementConverter;

    private TlvExperimenterCodec     tlvExperimenterCodec;
    private TlvExperimenterConverter tlvExperimenterConverter;

    public LabExtensionRegister(SwitchConnectionProvider switchConnectionProvider,
                                NiciraExtensionCodecRegistrator niciraExtensionCodecRegistrator,
                                ExtensionConverterRegistrator extensionConverterRegistrator) {
        this.switchConnectionProvider = switchConnectionProvider;
        this.niciraExtensionCodecRegistrator = niciraExtensionCodecRegistrator;
        this.extensionConverterRegistrator = extensionConverterRegistrator;
        this.registrations = new HashSet<>();

        this.matchConditionCodec = new MatchConditionCodec();
        this.matchConditionConverter = new MatchConditionConverter();
        this.actionMovementCodec = new ActionMovementCodec();
        this.actionMovementConverter = new ActionMovementConverter();
        this.tlvExperimenterCodec = new TlvExperimenterCodec();
        this.tlvExperimenterConverter = new TlvExperimenterConverter();

    }

    public void registerExtension() {
        LOGGER.info("registerExtension");
        registerMatchExtersion();
        registerActionExtersion();
        registerExperimenterExtension();
    }

    private void registerMatchExtersion() {
        niciraExtensionCodecRegistrator.registerMatchEntrySerializer(MatchConditionCodec.SERIALIZER_KEY, matchConditionCodec);
        niciraExtensionCodecRegistrator.registerMatchEntryDeserializer(MatchConditionCodec.DESERIALIZER_KEY, matchConditionCodec);

        registrations.add(extensionConverterRegistrator
                .registerMatchConvertor(new ConverterExtensionKey<>(TestMatchConditionKey.class, EncodeConstants.OF13_VERSION_ID),
                        matchConditionConverter));
        registrations.add(
                extensionConverterRegistrator.registerMatchConvertor(MatchConditionCodec.SERIALIZER_KEY, matchConditionConverter));

    }

    private void registerActionExtersion() {
        niciraExtensionCodecRegistrator.registerActionSerializer(ActionMovementCodec.SERIALIZER_KEY, actionMovementCodec);
        niciraExtensionCodecRegistrator.registerActionDeserializer(ActionMovementCodec.DESERIALIZER_KEY, actionMovementCodec);

        registrations.add(registerActionToOFJava(TestActionMovementRpcAddFlowApplyActionsCase.class, actionMovementConverter));
        registrations.add(registerActionFromOFJave(ActionMovementCodec.SERIALIZER_KEY, actionMovementConverter));
    }

    private void registerExperimenterExtension() {
        switchConnectionProvider.registerExperimenterMessageSerializer(TlvExperimenterCodec.SERIALIZER_KEY, tlvExperimenterCodec);
        switchConnectionProvider.registerExperimenterMessageDeserializer(TlvExperimenterCodec.DESERIALIZER_KEY, tlvExperimenterCodec);

        registrations.add(registerMessageToOFJava(LabTlvExperimenterMessageSalCase.class, tlvExperimenterConverter));
        registrations.add(registerMessageFromOFJave(LabTlvExperimenterMessageCase.class, tlvExperimenterConverter));
    }

    public void unregisterExtension() {
        LOGGER.info("unregisterExtension");
        // unregister match codec
        niciraExtensionCodecRegistrator.unregisterMatchEntrySerializer(MatchConditionCodec.SERIALIZER_KEY);
        niciraExtensionCodecRegistrator.unregisterMatchEntryDeserializer(MatchConditionCodec.DESERIALIZER_KEY);

        // unregister action codec
        niciraExtensionCodecRegistrator.unregisterActionSerializer(ActionMovementCodec.SERIALIZER_KEY);
        niciraExtensionCodecRegistrator.unregisterActionDeserializer(ActionMovementCodec.DESERIALIZER_KEY);

        switchConnectionProvider.unregisterSerializer(TlvExperimenterCodec.SERIALIZER_KEY);
        switchConnectionProvider.unregisterDeserializer(TlvExperimenterCodec.DESERIALIZER_KEY);

        // unregister converter
        registrations.forEach(p -> {
            try {
                p.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private ObjectRegistration<?> registerActionToOFJava(final Class<? extends Action> actionCaseType,
                                                         final ConvertorActionToOFJava<Action,
                                                                 org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.actions.grouping.Action> actionConverter) {
        TypeVersionKey<? extends Action> key = new TypeVersionKey<>(actionCaseType, EncodeConstants.OF13_VERSION_ID);
        return extensionConverterRegistrator.registerActionConvertor(key, actionConverter);
    }

    private ObjectRegistration<?> registerActionFromOFJave(NiciraActionSerializerKey niciraActionSerializerKey,
                                                           ConvertorActionFromOFJava<org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.actions.grouping.Action, ActionPath> actionConverter) {
        ActionSerializerKey<?> key = NiciraUtil.createOfJavaKeyFrom(niciraActionSerializerKey);
        return extensionConverterRegistrator.registerActionConvertor(key, actionConverter);
    }

    private ObjectRegistration<?> registerMessageToOFJava(final Class<? extends ExperimenterMessageOfChoice> actionCaseType,
                                                          final ConverterMessageToOFJava actionConvertor) {
        TypeVersionKey<? extends ExperimenterMessageOfChoice> key = new TypeVersionKey<>(actionCaseType, EncodeConstants.OF13_VERSION_ID);
        return extensionConverterRegistrator.registerMessageConvertor(key, actionConvertor);
    }

    private ObjectRegistration<?> registerMessageFromOFJave(final Class<? extends ExperimenterDataOfChoice> messageCaseType,
                                                            ConvertorMessageFromOFJava convertor) {
        MessageTypeKey<? extends ExperimenterDataOfChoice> key = new MessageTypeKey<>(EncodeConstants.OF13_VERSION_ID, messageCaseType);
        return extensionConverterRegistrator.registerMessageConvertor(key, convertor);
    }

}
