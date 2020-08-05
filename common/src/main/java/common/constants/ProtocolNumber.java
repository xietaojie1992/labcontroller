package common.constants;

/**
 * @author xietaojie1992
 */
public class ProtocolNumber {

    public static final Long  TYPE_IP   = 0x0800L;
    public static final Long  TYPE_ARP  = 0x0806L;
    public static final Long  TYPE_MPLS = 0x8847L;
    public static final Long  TYPE_LLDP = 0x88ccL;
    public static final Long  TYPE_VLAN = 0x8100L;
    public static final Short TYPE_ICMP = 0x0001;
    public static final Short TYPE_UDP  = 0x0011;
    public static final Short TYPE_TCP  = 0x0006;
    public static final Short TYPE_ESP  = 0x0032;

    public static final Integer PORT_ISAKMP = 500;
    public static final Integer PORT_NAT_T  = 4500;
    public static final Integer PORT_SSH    = 22;
    public static final Integer PORT_DNS    = 53;
    public static final Integer PORT_NTP    = 123;

    // ARP request and reply
    public static final Integer ARP_OP_REQUEST = 1;
    public static final Integer ARP_OP_REPLY   = 2;

}
