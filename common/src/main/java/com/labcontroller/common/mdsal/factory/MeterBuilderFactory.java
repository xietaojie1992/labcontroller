package com.labcontroller.common.mdsal.factory;

import com.google.common.collect.Lists;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.meters.MeterBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.meters.MeterKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.BandId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.MeterBandType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.MeterFlags;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.MeterId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.band.type.band.type.DropBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.band.type.band.type.ExperimenterBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.meter.MeterBandHeadersBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.meter.meter.band.headers.MeterBandHeader;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.meter.meter.band.headers.MeterBandHeaderBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.meter.meter.band.headers.meter.band.header.MeterBandTypesBuilder;

import java.util.List;

/**
 * @author xietaojie1992
 */
public class MeterBuilderFactory {

    enum MeterType {
        KB_DROP,

        PKT_DROP,

        UNKNOWN;

        public static MeterType getByName(String name) {
            try {
                return MeterType.valueOf(name);
            } catch (IllegalArgumentException e) {
                return UNKNOWN;
            }
        }
    }

    public static MeterBuilder createMeterBuilder(MeterType meterType, Long meterId, Long bandwidth, boolean isBarrier) {
        switch (meterType) {
            case KB_DROP:
                return createKbMeterBuilder(meterId, bandwidth, isBarrier, false);
            case PKT_DROP:
                return createPktMeterBuilder(meterId, bandwidth, isBarrier, false);
        }
        return null;
    }

    private static MeterBandHeaderBuilder createDropBandHeader(Long rate, Long bandId) {
        final DropBuilder dropBuilder = new DropBuilder().setDropRate(rate).setDropBurstSize((long) 1000);// 不同厂商设置Burst Size规则不同
        final MeterBandTypesBuilder meterBandTypesBuilder = new MeterBandTypesBuilder().setFlags(new MeterBandType(true, false, false));
        final MeterBandHeaderBuilder meterBandHeaderBuilder = new MeterBandHeaderBuilder().setBandRate(rate);
        meterBandHeaderBuilder.setBandType(dropBuilder.build());
        meterBandHeaderBuilder.setBandId(new BandId(bandId)).setMeterBandTypes(meterBandTypesBuilder.build());
        return meterBandHeaderBuilder;
    }

    // test meter experimenter
    private static MeterBandHeaderBuilder createExperimenterBandHeader(Long rate, Long bandId) {
        final ExperimenterBuilder experimenterBuilder = new ExperimenterBuilder();
        experimenterBuilder.setExperimenter((long) 1234).setExperimenterRate(rate).setExperimenterBurstSize((long) 1234);
        final MeterBandTypesBuilder meterBandTypesBuilder = new MeterBandTypesBuilder().setFlags(new MeterBandType(false, false, true));
        final MeterBandHeaderBuilder meterBandHeaderBuilder = new MeterBandHeaderBuilder().setBandRate(rate).setBandType(
                experimenterBuilder.build()).setMeterBandTypes(meterBandTypesBuilder.build()).setBandId(new BandId(bandId));
        return meterBandHeaderBuilder;
    }

    public static MeterBuilder createKbMeterBuilder(Long meterId, Long rate, boolean isBarrier, boolean enableBurst) {
        MeterBuilder meterBuilder = new MeterBuilder().setFlags(new MeterFlags(enableBurst, true, false, true));
        meterBuilder.setMeterId(new MeterId(meterId)).setKey(new MeterKey(new MeterId(meterId))).setBarrier(isBarrier);
        final List<MeterBandHeader> meterBandHeaders = Lists.newArrayList(createDropBandHeader(rate, 0L).build());
        meterBuilder.setMeterBandHeaders(new MeterBandHeadersBuilder().setMeterBandHeader(meterBandHeaders).build());
        return meterBuilder;
    }

    public static MeterBuilder createPktMeterBuilder(Long meterId, Long rate, boolean isBarrier, boolean enableBurst) {
        MeterBuilder meterBuilder = new MeterBuilder().setFlags(new MeterFlags(enableBurst, false, true, true));
        meterBuilder.setMeterId(new MeterId(meterId)).setKey(new MeterKey(meterBuilder.getMeterId())).setBarrier(isBarrier);
        final List<MeterBandHeader> meterBandHeaders = Lists.newArrayList(createDropBandHeader(rate, 0L).build());
        meterBuilder.setMeterBandHeaders(new MeterBandHeadersBuilder().setMeterBandHeader(meterBandHeaders).build());

        return meterBuilder;
    }

    public static MeterBuilder createRemoveMeterBuilder(Long meterId, boolean isBarrier) {
        return new MeterBuilder().setMeterId(new MeterId(meterId)).setKey(new MeterKey(new MeterId(meterId))).setBarrier(isBarrier);
    }
}
