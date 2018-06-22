package com.xietaojie.lab.impl.service;

import com.labcontroller.common.packet.MplsPacket;
import org.opendaylight.controller.liblldp.EtherTypes;
import org.opendaylight.controller.liblldp.Ethernet;
import org.opendaylight.controller.liblldp.HexEncode;
import org.opendaylight.controller.liblldp.Packet;
import org.opendaylight.controller.liblldp.PacketException;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorRef;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeRef;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.Nodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.node.NodeConnector;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.node.NodeConnectorKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.NodeKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.TransmitPacketInputBuilder;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xietaojie1992
 */
public class PacketOutService {

    private Logger logger = LoggerFactory.getLogger(PacketOutService.class);
    private PacketProcessingService packetProcessingService;

    public PacketOutService(final PacketProcessingService packetProcessingService) {
        this.packetProcessingService = packetProcessingService;
    }

    public void sendPacketOut(String dpid, String portId, byte[] payload) {
        try {
            NodeConnectorId nodeConnectorId = new NodeConnectorId(portId);
            InstanceIdentifier<NodeConnector> nodeConnectorIID = InstanceIdentifier.create(Nodes.class).child(Node.class,
                    new NodeKey(new NodeId(dpid))).child(NodeConnector.class, new NodeConnectorKey(nodeConnectorId));
            InstanceIdentifier<Node> nodeIID = nodeConnectorIID.firstIdentifierOf(Node.class);
            TransmitPacketInputBuilder builder = new TransmitPacketInputBuilder().setEgress(new NodeConnectorRef(nodeConnectorIID)).setNode(
                    new NodeRef(nodeIID)).setPayload(payload);
            packetProcessingService.transmitPacket(builder.build());
        } catch (Exception e) {
            logger.error("TRANSMIT_PACKET_ERROR");
            logger.error(e.toString());
        }
    }

    private Ethernet createMplsFrame(MplsPacket mplsPacket) throws PacketException {
        return createEthernetFlame("srcMac", "dstMac", EtherTypes.MPLSMCAST.shortValue(), mplsPacket.getPayload());
    }

    public static Ethernet createEthernetFlame(String srcMac, String dstMac, short etherType, Packet payload) {
        Ethernet flexFrame = new Ethernet();
        flexFrame.setSourceMACAddress(HexEncode.bytesFromHexString(srcMac)).setDestinationMACAddress(HexEncode.bytesFromHexString(dstMac))
                .setEtherType(etherType).setPayload(payload);
        return flexFrame;
    }

}
