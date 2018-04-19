package com.xietaojie.lab.impl.extension.match;

import com.google.common.base.Optional;
import org.opendaylight.openflowplugin.extension.api.ConvertorFromOFJava;
import org.opendaylight.openflowplugin.extension.api.ConvertorToOFJava;
import org.opendaylight.openflowplugin.extension.api.ExtensionAugment;
import org.opendaylight.openflowplugin.extension.api.GroupingResolver;
import org.opendaylight.openflowplugin.extension.api.path.MatchPath;
import org.opendaylight.openflowplugin.extension.vendor.nicira.convertor.CodecPreconditionException;
import org.opendaylight.openflowplugin.extension.vendor.nicira.convertor.match.MatchUtil;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.match.mdsal.ext.rev180421.TestMatchConditionKey;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.match.mdsal.ext.rev180421.TestMatchConditionNodesNodeTableFlow;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.match.mdsal.ext.rev180421.TestMatchConditionNotifPacketIn;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.match.mdsal.ext.rev180421.TestMatchConditionNotifPacketInBuilder;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.match.mdsal.ext.rev180421.TestMatchConditionNotifSwitchFlowRemoved;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.match.mdsal.ext.rev180421.TestMatchConditionNotifSwitchFlowRemovedBuilder;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.match.mdsal.ext.rev180421.TestMatchConditionNotifUpdateFlowStats;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.match.mdsal.ext.rev180421.TestMatchConditionNotifUpdateFlowStatsBuilder;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.match.mdsal.ext.rev180421.TestMatchConditionRpcAddFlow;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.match.mdsal.ext.rev180421.TestMatchConditionRpcRemoveFlow;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.match.mdsal.ext.rev180421.TestMatchConditionRpcUpdateFlowOriginal;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.match.mdsal.ext.rev180421.TestMatchConditionRpcUpdateFlowUpdated;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.match.ofj.ext.rev180421.TestMatchCondition;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.match.ofj.ext.rev180421.TestMatchConditionGrouping;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.match.ofj.ext.rev180421.oxm.container.match.entry.value
        .TestMatchConditionCase;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.match.ofj.ext.rev180421.oxm.container.match.entry.value
        .TestMatchConditionCaseBuilder;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.match.ofj.ext.rev180421.test.match.condition.grouping.TestMatchConditionValue;
import org.opendaylight.yang.gen.v1.ns.yang.labcontroller.lab.match.ofj.ext.rev180421.test.match.condition.grouping
        .TestMatchConditionValueBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.Nxm1Class;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.match.entries.grouping.MatchEntry;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.extension.general.rev140714.general.extension.grouping.Extension;
import org.opendaylight.yangtools.yang.binding.Augmentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @author xietaojie1992
 */
public class MatchConditionConverter implements ConvertorToOFJava<MatchEntry>, ConvertorFromOFJava<MatchEntry, MatchPath> {

    private static final Logger                                                  LOGGER        = LoggerFactory.getLogger(
            MatchConditionConverter.class);
    private static final Set<Class<? extends Augmentation<Extension>>>           augmentations = new HashSet<>();
    private static final GroupingResolver<TestMatchConditionGrouping, Extension> RESOLVER      = new GroupingResolver<>(
            TestMatchConditionGrouping.class);

    static {
        augmentations.add(TestMatchConditionRpcAddFlow.class);
        augmentations.add(TestMatchConditionRpcRemoveFlow.class);
        augmentations.add(TestMatchConditionRpcUpdateFlowUpdated.class);
        augmentations.add(TestMatchConditionRpcUpdateFlowOriginal.class);
        augmentations.add(TestMatchConditionNodesNodeTableFlow.class);
        augmentations.add(TestMatchConditionNotifPacketIn.class);
        augmentations.add(TestMatchConditionNotifSwitchFlowRemoved.class);
        augmentations.add(TestMatchConditionNotifUpdateFlowStats.class);
        RESOLVER.setAugmentations(augmentations);
    }

    @Override
    public ExtensionAugment<? extends Augmentation<Extension>> convert(MatchEntry input, MatchPath path) {
        LOGGER.info("convert : {}", input);
        TestMatchConditionCase conditionCase = (TestMatchConditionCase) input.getMatchEntryValue();
        TestMatchConditionValue value = new TestMatchConditionValueBuilder().setCondition(
                conditionCase.getTestMatchConditionValue().getCondition().longValue()).build();
        switch (path) {
            case FLOWSSTATISTICSUPDATE_FLOWANDSTATISTICSMAPLIST_MATCH:
                return new ExtensionAugment<>(TestMatchConditionNotifUpdateFlowStats.class,
                        new TestMatchConditionNotifUpdateFlowStatsBuilder().setTestMatchConditionValue(value).build(),
                        TestMatchConditionKey.class);
            case PACKETRECEIVED_MATCH:
                return new ExtensionAugment<>(TestMatchConditionNotifPacketIn.class,
                        new TestMatchConditionNotifPacketInBuilder().setTestMatchConditionValue(value).build(),
                        TestMatchConditionKey.class);
            case SWITCHFLOWREMOVED_MATCH:
                return new ExtensionAugment<>(TestMatchConditionNotifSwitchFlowRemoved.class,
                        new TestMatchConditionNotifSwitchFlowRemovedBuilder().setTestMatchConditionValue(value).build(),
                        TestMatchConditionKey.class);
            default:
                throw new CodecPreconditionException(path);
        }
    }

    @Override
    public MatchEntry convert(Extension extension) {
        Optional<TestMatchConditionGrouping> optional = RESOLVER.getExtension(extension);
        if (!optional.isPresent()) {
            throw new CodecPreconditionException(extension);
        }
        return MatchUtil.createDefaultMatchEntryBuilder(TestMatchCondition.class, Nxm1Class.class,
                new TestMatchConditionCaseBuilder(optional.get()).build()).build();
    }
}
