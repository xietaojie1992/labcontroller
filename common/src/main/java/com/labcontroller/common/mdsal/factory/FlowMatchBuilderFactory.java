package com.labcontroller.common.mdsal.factory;

import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Ipv4Prefix;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.PortNumber;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.yang.types.rev100924.MacAddress;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.l2.types.rev130827.EtherType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.l2.types.rev130827.VlanId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.ethernet.match.fields.EthernetDestinationBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.ethernet.match.fields.EthernetSourceBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.ethernet.match.fields.EthernetTypeBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.EthernetMatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.Icmpv4MatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.IpMatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.ProtocolMatchFieldsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.Tunnel;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.TunnelBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.VlanMatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._3.match.ArpMatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._3.match.Ipv4MatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._4.match.TcpMatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._4.match.UdpMatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.vlan.match.fields.VlanIdBuilder;

import java.math.BigInteger;

/**
 * @author xietaojie1992
 */
public class FlowMatchBuilderFactory {

    public static NodeConnectorId createInPortMatch(String inPort) {
        return new NodeConnectorId(inPort);
    }

    public static VlanMatchBuilder createVlanMatch(Integer vlan) {
        return new VlanMatchBuilder().setVlanId(new VlanIdBuilder().setVlanId(new VlanId(vlan)).setVlanIdPresent(true).build());
    }

    public static Icmpv4MatchBuilder createIcmpv4Match(short icmpCode, short icmpType) {
        return new Icmpv4MatchBuilder().setIcmpv4Code(icmpCode).setIcmpv4Type(icmpType);
    }

    public static EthernetMatchBuilder createEtherTypeMatch(Long etherType) {
        return new EthernetMatchBuilder().setEthernetType(new EthernetTypeBuilder().setType(new EtherType(etherType)).build());
    }

    public static EthernetMatchBuilder createDstMacMatch(String dstMac) {
        return new EthernetMatchBuilder().setEthernetDestination(
                new EthernetDestinationBuilder().setAddress(new MacAddress(dstMac)).build());
    }

    public static EthernetMatchBuilder createSrcMacMatch(String srcMac) {
        return new EthernetMatchBuilder().setEthernetSource(new EthernetSourceBuilder().setAddress(new MacAddress(srcMac)).build());
    }

    public static EthernetMatchBuilder createSrcDstMacMatch(String srcMac, String dstMac) {
        return new EthernetMatchBuilder().setEthernetSource(new EthernetSourceBuilder().setAddress(new MacAddress(srcMac)).build())
                .setEthernetDestination(new EthernetDestinationBuilder().setAddress(new MacAddress(dstMac)).build());
    }

    public static ProtocolMatchFieldsBuilder createMplsLabelMatch(Long label) {
        return new ProtocolMatchFieldsBuilder().setMplsLabel(label);
    }

    public static Tunnel createTunnelMatch(BigInteger tunnelId) {
        return new TunnelBuilder().setTunnelId(tunnelId).build();
    }

    public static Ipv4MatchBuilder createSrcIpMatch(String srcIpv4Prefix) {
        return new Ipv4MatchBuilder().setIpv4Source(new Ipv4Prefix(srcIpv4Prefix));
    }

    public static Ipv4MatchBuilder createDstIpMatch(String dstIpv4Prefix) {
        return new Ipv4MatchBuilder().setIpv4Destination(new Ipv4Prefix(dstIpv4Prefix));
    }

    public static Ipv4MatchBuilder createSrcAndDstIpMatch(String srcIpv4Prefix, String dstIpv4Prefix) {
        return new Ipv4MatchBuilder().setIpv4Source(new Ipv4Prefix(srcIpv4Prefix)).setIpv4Destination(new Ipv4Prefix(dstIpv4Prefix));
    }

    public static ArpMatchBuilder createArpSpaMatch(String srcIpv4Prefix) {
        return new ArpMatchBuilder().setArpSourceTransportAddress(new Ipv4Prefix(srcIpv4Prefix));
    }

    public static ArpMatchBuilder createArpTpaMatch(String dstIpv4Prefix) {
        return new ArpMatchBuilder().setArpTargetTransportAddress(new Ipv4Prefix(dstIpv4Prefix));
    }

    public static ArpMatchBuilder createArpSpaAndTpaMatch(String srcIpv4Prefix, String dstIpv4Prefix) {
        return new ArpMatchBuilder().setArpSourceTransportAddress(new Ipv4Prefix(srcIpv4Prefix)).setArpTargetTransportAddress(
                new Ipv4Prefix(dstIpv4Prefix));
    }

    public static ArpMatchBuilder createArpOpTpaMatch(Integer arpOp, String ipv4Prefix) {
        return new ArpMatchBuilder().setArpOp(arpOp).setArpTargetTransportAddress(new Ipv4Prefix(ipv4Prefix));
    }

    public static IpMatchBuilder createIpProtoMatch(Short protocol) {
        return new IpMatchBuilder().setIpProtocol(protocol);
    }

    public static TcpMatchBuilder createTcpSrcMatch(Integer portNumber) {
        return new TcpMatchBuilder().setTcpSourcePort(new PortNumber(portNumber));
    }

    public static TcpMatchBuilder createTcpDstMatch(Integer portNumber) {
        return new TcpMatchBuilder().setTcpDestinationPort(new PortNumber(portNumber));
    }

    public static UdpMatchBuilder createUdpDstMatch(Integer portNumber) {
        return new UdpMatchBuilder().setUdpDestinationPort(new PortNumber(portNumber));
    }

    public static UdpMatchBuilder createUdpSrcMatch(Integer portNumber) {
        return new UdpMatchBuilder().setUdpSourcePort(new PortNumber(portNumber));
    }

}
