package com.xietaojie.lab.impl.listeners;

import org.opendaylight.controller.liblldp.EtherTypes;
import org.opendaylight.controller.liblldp.Ethernet;
import org.opendaylight.controller.liblldp.NetUtils;
import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingListener;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketReceived;
import org.opendaylight.yangtools.concepts.ListenerRegistration;
import org.opendaylight.yangtools.yang.binding.NotificationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xietaojie1992
 */
public class PacketInListener implements PacketProcessingListener, AutoCloseable {

    private Logger logger = LoggerFactory.getLogger(PacketInListener.class);

    private NotificationProviderService                notificationProviderService;
    private ListenerRegistration<NotificationListener> packetInListenerRegistration;

    public PacketInListener(NotificationProviderService notificationProviderService) {
        this.notificationProviderService = notificationProviderService;
    }

    public void init() {
        packetInListenerRegistration = notificationProviderService.registerNotificationListener(this);
    }

    @Override
    public void onPacketReceived(PacketReceived notification) {
        byte[] payload = notification.getPayload();
        Ethernet ethPkt = new Ethernet();
        try {
            ethPkt.deserialize(payload, 0, (payload.length * NetUtils.NumBitsInAByte - 32));// no need to deserialize frame check sequence
        } catch (Exception e) {
            logger.warn("Failed to decode packet in {}", e);
        }

        logger.debug(EtherTypes.getEtherTypeName(ethPkt.getEtherType()));
        if (EtherTypes.MPLSUCAST.shortValue() == ethPkt.getEtherType()) {
            logger.info("Mpls packet received");
        } else if (EtherTypes.LLDP.shortValue() == ethPkt.getEtherType()) {
            logger.info("Lldp packet received");
        }
    }

    @Override
    public void close() throws Exception {
        packetInListenerRegistration.close();
    }
}
