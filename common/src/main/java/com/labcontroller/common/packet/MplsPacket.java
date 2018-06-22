package com.labcontroller.common.packet;

import org.opendaylight.controller.liblldp.Packet;
import org.opendaylight.controller.liblldp.PacketException;

/**
 * @author xietaojie1992
 */
public class MplsPacket extends Packet {

    // TODO

    @Override
    public byte[] serialize() throws PacketException {
        return super.serialize();
    }

    @Override
    public Packet deserialize(byte[] data, int bitOffset, int size) throws PacketException {
        return super.deserialize(data, bitOffset, size);
    }
}
