package com.labcontroller.common.mdsal.factory;

import com.labcontroller.common.constants.ProtocolNumber;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Ipv4Prefix;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.PortNumber;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.yang.types.rev100924.MacAddress;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.Match;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.MatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.l2.types.rev130827.EtherType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.l2.types.rev130827.VlanId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.ethernet.match.fields.EthernetDestinationBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.ethernet.match.fields.EthernetSourceBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.ethernet.match.fields.EthernetTypeBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.EthernetMatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.Icmpv4MatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.IpMatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.ProtocolMatchFieldsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.TunnelBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.VlanMatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._3.match.ArpMatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._3.match.Ipv4MatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._4.match.TcpMatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.vlan.match.fields.VlanIdBuilder;

import java.math.BigInteger;

/**
 * @author xietaojie1992
 */
public class FlowMatchBuilder {

    private MatchBuilder matchBuilder = new MatchBuilder();

    private FlowMatchBuilder() {
    }

    public static FlowMatchBuilder builder() {
        return new FlowMatchBuilder();
    }

    public Match build() {
        return matchBuilder.build();
    }

    ////////////////////////////////// Basic Match ////////////////////////////

    public FlowMatchBuilder setInport(String inport) {
        matchBuilder.setInPort(FlowMatchBuilderFactory.createInPortMatch(inport));
        return this;
    }

    public FlowMatchBuilder createVlanMatch(Integer vlan) {
        matchBuilder.setVlanMatch(new VlanMatchBuilder().setVlanId(new VlanIdBuilder().setVlanId(new VlanId(vlan)).setVlanIdPresent(true)
                .build()).build());
        return this;
    }

    public FlowMatchBuilder createIcmpv4Match(short icmpCode, short icmpType) {
        matchBuilder.setIcmpv4Match(new Icmpv4MatchBuilder().setIcmpv4Code(icmpCode).setIcmpv4Type(icmpType).build());
        return this;
    }

    public FlowMatchBuilder createEtherTypeMatch(Long etherType) {
        matchBuilder.setEthernetMatch(
                new EthernetMatchBuilder().setEthernetType(new EthernetTypeBuilder().setType(new EtherType(etherType)).build()).build());
        return this;
    }

    public FlowMatchBuilder createDstMacMatch(String dstMac) {
        matchBuilder.setEthernetMatch(new EthernetMatchBuilder()
                .setEthernetDestination(new EthernetDestinationBuilder().setAddress(new MacAddress(dstMac)).build()).build());
        return this;
    }

    public FlowMatchBuilder createSrcMacMatch(String srcMac) {
        matchBuilder.setEthernetMatch(
                new EthernetMatchBuilder().setEthernetSource(new EthernetSourceBuilder().setAddress(new MacAddress(srcMac)).build())
                        .build());
        return this;
    }

    public FlowMatchBuilder createSrcDstMacMatch(String srcMac, String dstMac) {
        matchBuilder.setEthernetMatch(new EthernetMatchBuilder().setEthernetSource(
                new EthernetSourceBuilder().setAddress(new MacAddress(srcMac)).build())
                .setEthernetDestination(new EthernetDestinationBuilder().setAddress(new MacAddress(dstMac)).build()).build());
        return this;
    }

    public FlowMatchBuilder createMplsLabelMatch(Long label) {
        matchBuilder.setProtocolMatchFields(new ProtocolMatchFieldsBuilder().setMplsLabel(label).build());
        return this;
    }

    public FlowMatchBuilder createTunnelMatch(BigInteger tunnelId) {
        matchBuilder.setTunnel(new TunnelBuilder().setTunnelId(tunnelId).build());
        return this;
    }

    public FlowMatchBuilder createSrcIpMatch(String srcIpv4Prefix) {
        matchBuilder.setLayer3Match(new Ipv4MatchBuilder().setIpv4Source(new Ipv4Prefix(srcIpv4Prefix)).build());
        return this;
    }

    public FlowMatchBuilder createDstIpMatch(String dstIpv4Prefix) {
        matchBuilder.setLayer3Match(new Ipv4MatchBuilder().setIpv4Destination(new Ipv4Prefix(dstIpv4Prefix)).build());
        return this;
    }

    public FlowMatchBuilder createSrcAndDstIpMatch(String srcIpv4Prefix, String dstIpv4Prefix) {
        matchBuilder.setLayer3Match(new Ipv4MatchBuilder().setIpv4Source(new Ipv4Prefix(srcIpv4Prefix))
                .setIpv4Destination(new Ipv4Prefix(dstIpv4Prefix)).build());
        return this;
    }

    public FlowMatchBuilder createArpSpaMatch(String srcIpv4Prefix) {
        matchBuilder.setLayer3Match(new ArpMatchBuilder().setArpSourceTransportAddress(new Ipv4Prefix(srcIpv4Prefix)).build());
        return this;
    }

    public FlowMatchBuilder createArpTpaMatch(String dstIpv4Prefix) {
        matchBuilder.setLayer3Match(new ArpMatchBuilder().setArpTargetTransportAddress(new Ipv4Prefix(dstIpv4Prefix)).build());
        return this;
    }

    public FlowMatchBuilder createArpSpaAndTpaMatch(String srcIpv4Prefix, String dstIpv4Prefix) {
        matchBuilder.setLayer3Match(new ArpMatchBuilder().setArpSourceTransportAddress(new Ipv4Prefix(srcIpv4Prefix))
                .setArpTargetTransportAddress(new Ipv4Prefix(dstIpv4Prefix)).build());
        return this;
    }

    public FlowMatchBuilder createArpOpTpaMatch(Integer arpOp, String ipv4Prefix) {
        matchBuilder.setLayer3Match(new ArpMatchBuilder().setArpOp(arpOp).setArpTargetTransportAddress(new Ipv4Prefix(ipv4Prefix)).build());
        return this;
    }

    public FlowMatchBuilder createIpProtoMatch(Short protocol) {
        matchBuilder.setIpMatch(new IpMatchBuilder().setIpProtocol(protocol).build());
        return this;
    }

    public FlowMatchBuilder createTcpSrcMatch(Integer portNumber) {
        matchBuilder.setLayer4Match(new TcpMatchBuilder().setTcpSourcePort(new PortNumber(portNumber)).build());
        return this;
    }

    public FlowMatchBuilder createTcpDstMatch(Integer portNumber) {
        matchBuilder.setLayer4Match(new TcpMatchBuilder().setTcpDestinationPort(new PortNumber(portNumber)).build());
        return this;
    }

    public FlowMatchBuilder createUdpDstMatch(Integer portNumber) {
        matchBuilder.setLayer4Match(FlowMatchBuilderFactory.createUdpDstMatch(portNumber).build());
        return this;
    }

    public FlowMatchBuilder createUdpSrcMatch(Integer portNumber) {
        matchBuilder.setLayer4Match(FlowMatchBuilderFactory.createUdpDstMatch(portNumber).build());
        return this;
    }

    ///////////////////////// Combine Examples //////////////////////////////

    public FlowMatchBuilder createAllowSshMatch() {
        matchBuilder.setEthernetMatch(FlowMatchBuilderFactory.createEtherTypeMatch(ProtocolNumber.TYPE_IP).build());
        matchBuilder.setIpMatch(FlowMatchBuilderFactory.createIpProtoMatch(ProtocolNumber.TYPE_TCP).build());
        matchBuilder.setLayer4Match(FlowMatchBuilderFactory.createTcpDstMatch(ProtocolNumber.PORT_SSH).build());
        return this;
    }

    public FlowMatchBuilder createAllowNtpMatch() {
        matchBuilder.setEthernetMatch(FlowMatchBuilderFactory.createEtherTypeMatch(ProtocolNumber.TYPE_IP).build());
        matchBuilder.setIpMatch(FlowMatchBuilderFactory.createIpProtoMatch(ProtocolNumber.TYPE_UDP).build());
        matchBuilder.setLayer4Match(FlowMatchBuilderFactory.createTcpDstMatch(ProtocolNumber.PORT_NTP).build());
        return this;
    }

    public FlowMatchBuilder createAllowNatMatch(String srcIpSubnet) {
        matchBuilder.setEthernetMatch(FlowMatchBuilderFactory.createEtherTypeMatch(ProtocolNumber.TYPE_IP).build());
        matchBuilder.setLayer3Match(FlowMatchBuilderFactory.createSrcIpMatch(srcIpSubnet).build());
        matchBuilder.setIpMatch(FlowMatchBuilderFactory.createIpProtoMatch(ProtocolNumber.TYPE_UDP).build());
        matchBuilder.setLayer4Match(FlowMatchBuilderFactory.createTcpDstMatch(ProtocolNumber.PORT_NAT_T).build());
        return this;
    }

    public FlowMatchBuilder createAllowIsakmpMatch(String srcIpSubnet) {
        matchBuilder.setEthernetMatch(FlowMatchBuilderFactory.createEtherTypeMatch(ProtocolNumber.TYPE_IP).build());
        matchBuilder.setLayer3Match(FlowMatchBuilderFactory.createSrcIpMatch(srcIpSubnet).build());
        matchBuilder.setIpMatch(FlowMatchBuilderFactory.createIpProtoMatch(ProtocolNumber.TYPE_UDP).build());
        matchBuilder.setLayer4Match(FlowMatchBuilderFactory.createTcpDstMatch(ProtocolNumber.PORT_ISAKMP).build());
        return this;
    }

    public FlowMatchBuilder createAllowDnsMatch() {
        matchBuilder.setEthernetMatch(FlowMatchBuilderFactory.createEtherTypeMatch(ProtocolNumber.TYPE_IP).build());
        matchBuilder.setIpMatch(FlowMatchBuilderFactory.createIpProtoMatch(ProtocolNumber.TYPE_UDP).build());
        matchBuilder.setLayer4Match(FlowMatchBuilderFactory.createTcpDstMatch(ProtocolNumber.PORT_DNS).build());
        return this;
    }

    public FlowMatchBuilder createAllowIcmpMatch(Short code, Short type) {
        matchBuilder.setEthernetMatch(FlowMatchBuilderFactory.createEtherTypeMatch(ProtocolNumber.TYPE_IP).build());
        matchBuilder.setIpMatch(FlowMatchBuilderFactory.createIpProtoMatch(ProtocolNumber.TYPE_ICMP).build());
        matchBuilder.setIcmpv4Match(FlowMatchBuilderFactory.createIcmpv4Match(code, type).build());
        return this;
    }

    public FlowMatchBuilder createAllowDstIpMatch(String dstIpSubnet) {
        matchBuilder.setEthernetMatch(FlowMatchBuilderFactory.createEtherTypeMatch(ProtocolNumber.TYPE_IP).build());
        matchBuilder.setLayer3Match(FlowMatchBuilderFactory.createDstIpMatch(dstIpSubnet).build());
        return this;
    }

    public FlowMatchBuilder createAllowIpMatch(String srcIpSubnet, String dstIpSubnet) {
        matchBuilder.setEthernetMatch(FlowMatchBuilderFactory.createEtherTypeMatch(ProtocolNumber.TYPE_IP).build());
        matchBuilder.setLayer3Match(FlowMatchBuilderFactory.createSrcAndDstIpMatch(srcIpSubnet, dstIpSubnet).build());
        return this;
    }

    public FlowMatchBuilder createAllowEspDstMatch(String dstIpSubnet) {
        matchBuilder.setEthernetMatch(FlowMatchBuilderFactory.createEtherTypeMatch(ProtocolNumber.TYPE_IP).build());
        matchBuilder.setLayer3Match(FlowMatchBuilderFactory.createDstIpMatch(dstIpSubnet).build());
        matchBuilder.setIpMatch(FlowMatchBuilderFactory.createIpProtoMatch(ProtocolNumber.TYPE_ESP).build());
        return this;
    }

    public FlowMatchBuilder createAllowMplsMatch(Long mplsLabel) {
        matchBuilder.setEthernetMatch(FlowMatchBuilderFactory.createEtherTypeMatch(ProtocolNumber.TYPE_MPLS).build());
        matchBuilder.setProtocolMatchFields(FlowMatchBuilderFactory.createMplsLabelMatch(mplsLabel).build());
        return this;
    }
}
