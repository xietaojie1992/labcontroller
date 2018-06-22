package com.labcontroller.common.packet.header;

import com.labcontroller.common.utils.ByteUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.opendaylight.controller.liblldp.BitBufferHelper;
import org.opendaylight.controller.liblldp.Packet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * MPLS报文头，32比特，分为4个部分：
 * 1.标签值，占20个比特；
 * 2.试验位，占3个比特；
 * 3.栈底标志位，占一个比特；
 * 4.TTL，存活时间，占8个比特；
 *
 * @author xietaojie1992
 */
public class MplsHeader extends Packet {

    private final Logger logger = LoggerFactory.getLogger(MplsHeader.class);

    private static final String MPLS_LABEL            = "MplsLabel";
    private static final String EXPERIMENTAL_BITS     = "Experimental_Bits";
    private static final String BOTTOM_OF_LABEL_STACK = "Bottom_Of_Label_Stack";
    private static final String TTL                   = "TimeToLive";
    private static final int    MPLS_HEADER_FIELDS    = 4;
    public static final  int    MPLS_HEADER_LENGTH    = 4; // MPLS报头一共4个字节

    private static final byte[] VALUES_EXPERIMENTAL_BITS           = new byte[] {0};
    private static final byte[] VALUES_TTL                         = new byte[] {(byte) 255};
    private static final byte[] VALUES_BOTTOM_OF_LABEL_STACK_TRUE  = new byte[] {(byte) 255};
    private static final byte[] VALUES_BOTTOM_OF_LABEL_STACK_FALSE = new byte[] {0};

    private static Map<String, Pair<Integer, Integer>> fieldCoordinates = new LinkedHashMap<String, Pair<Integer, Integer>>() {
        private static final long serialVersionUID = 1L;

        {
            put(MPLS_LABEL, new ImmutablePair<>(0, 20));
            put(EXPERIMENTAL_BITS, new ImmutablePair<>(20, 3));
            put(BOTTOM_OF_LABEL_STACK, new ImmutablePair<>(23, 1));
            put(TTL, new ImmutablePair<>(24, 8));
        }
    };
    private final Map<String, byte[]> fieldValues;

    public MplsHeader() {
        super();
        fieldValues = new HashMap<>();
        hdrFieldCoordMap = fieldCoordinates;
        hdrFieldsMap = fieldValues;
    }

    public MplsHeader(int label) {
        super();
        fieldValues = new HashMap<>(MPLS_HEADER_FIELDS);
        hdrFieldCoordMap = fieldCoordinates;
        hdrFieldsMap = fieldValues;
        setLabel(label, false);
    }

    public MplsHeader(int label, boolean isBottomOfLabelStack) {
        super();
        fieldValues = new HashMap<>(MPLS_HEADER_FIELDS);
        hdrFieldCoordMap = fieldCoordinates;
        hdrFieldsMap = fieldValues;
        setLabel(label, isBottomOfLabelStack);
    }

    public int getBitSize() {
        return MplsHeader.fieldCoordinates.get(MPLS_LABEL).getRight() + MplsHeader.fieldCoordinates.get(EXPERIMENTAL_BITS).getRight()
                + MplsHeader.fieldCoordinates.get(BOTTOM_OF_LABEL_STACK).getRight() + MplsHeader.fieldCoordinates.get(TTL).getRight();
    }

    public int getLabel() {
        byte[] bytes = fieldValues.get(MPLS_LABEL);
        int label = BitBufferHelper.getInt(bytes);
        return label;
    }

    public void setLabel(int label, boolean isBottomOfLabelStack) {
        buildHeader(label, isBottomOfLabelStack);
    }

    public void setLabel(int label) {
        buildHeader(label, false);
    }

    private void buildHeader(int label, boolean isBottomOfLabelStack) {
        byte[] lableBytes = BitBufferHelper.toByteArray(label);
        logger.debug("buildMPLSHeader: {}, Binary String: {}", label, ByteUtils.bytesToBit(lableBytes));
        fieldValues.put(MPLS_LABEL, new byte[] {lableBytes[1], lableBytes[2], lableBytes[3]});
        fieldValues.put(EXPERIMENTAL_BITS, VALUES_EXPERIMENTAL_BITS);
        fieldValues.put(BOTTOM_OF_LABEL_STACK,
                isBottomOfLabelStack ? VALUES_BOTTOM_OF_LABEL_STACK_TRUE : VALUES_BOTTOM_OF_LABEL_STACK_FALSE);
        fieldValues.put(TTL, VALUES_TTL);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((fieldValues == null) ? 0 : fieldValues.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        MplsHeader other = (MplsHeader) obj;
        if (fieldValues == null) {
            if (other.fieldValues != null) {
                return false;
            }
        } else if (!fieldValues.equals(other.fieldValues)) {
            return false;
        }
        return true;
    }

}
