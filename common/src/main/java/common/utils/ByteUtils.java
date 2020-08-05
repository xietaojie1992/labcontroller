package common.utils;

import java.math.BigInteger;

/**
 *
 * @author xietaojie1992
 */
public class ByteUtils {

    public static String byteToBit(byte b) {
        return "" + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1) + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1) + (byte) ((b >> 3)
                & 0x1) + (byte) ((b >> 2) & 0x1) + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);
    }

    public static String bytesToBit(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            stringBuilder.append(byteToBit(b));
        }
        return stringBuilder.toString();
    }

    /**
     * 将int数值转换为占四个字节的byte数组(高位在前，低位在后)
     */
    public static byte[] intToBytes(int value) {
        byte[] src = new byte[4];
        src[0] = (byte) ((value >> 24) & 0xFF);
        src[1] = (byte) ((value >> 16) & 0xFF);
        src[2] = (byte) ((value >> 8) & 0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }

    /**
     * byte数组中取int数值(低位在后，高位在前)
     */
    public static int bytesToInt(byte[] src, int offset) {
        int value;
        value = (int) (((src[offset] & 0xFF) << 24) | ((src[offset + 1] & 0xFF) << 16) | ((src[offset + 2] & 0xFF) << 8) | (src[offset + 3]
                & 0xFF));
        return value;
    }

    public static byte[] hexStringToBytes(String s) {
        return new BigInteger(s, 16).toByteArray();
    }
}
