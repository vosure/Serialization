package com.vosure.serialization;

import java.nio.ByteBuffer;

public class SerializationWriter {

    public static final byte[] HEADER = "HS".getBytes();
    public static final short VERSION = 0x0100;

    public static int writeBytes(byte[] dest, int pointer, byte[] data) {
        for (int i = 0; i < data.length; i++)
            dest[pointer++] = data[i];

        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, short[] data) {
        for (int i = 0; i < data.length; i++)
            pointer = writeBytes(dest, pointer, data[i]);

        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, char[] data) {
        for (int i = 0; i < data.length; i++)
            pointer = writeBytes(dest, pointer, data[i]);

        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, int[] data) {
        for (int i = 0; i < data.length; i++)
            pointer = writeBytes(dest, pointer, data[i]);

        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, long[] data) {
        for (int i = 0; i < data.length; i++)
            pointer = writeBytes(dest, pointer, data[i]);

        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, float[] data) {
        for (int i = 0; i < data.length; i++)
            pointer = writeBytes(dest, pointer, data[i]);

        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, double[] data) {
        for (int i = 0; i < data.length; i++)
            pointer = writeBytes(dest, pointer, data[i]);

        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, boolean[] data) {
        for (int i = 0; i < data.length; i++)
            pointer = writeBytes(dest, pointer, data[i]);

        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, byte value) {
        dest[pointer++] = value;

        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, char value) {
        dest[pointer++] = (byte) ((value >> 8) & 0xff);
        dest[pointer++] = (byte) ((value >> 0) & 0xff);

        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, short value) {
        dest[pointer++] = (byte) ((value >> 8) & 0xff);
        dest[pointer++] = (byte) ((value >> 0) & 0xff);

        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, int value) {
        dest[pointer++] = (byte) ((value >> 24) & 0xff);
        dest[pointer++] = (byte) ((value >> 16) & 0xff);
        dest[pointer++] = (byte) ((value >> 8) & 0xff);
        dest[pointer++] = (byte) ((value >> 0) & 0xff);

        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, long value) {
        dest[pointer++] = (byte) ((value >> 56) & 0xff);
        dest[pointer++] = (byte) ((value >> 48) & 0xff);
        dest[pointer++] = (byte) ((value >> 40) & 0xff);
        dest[pointer++] = (byte) ((value >> 32) & 0xff);
        dest[pointer++] = (byte) ((value >> 24) & 0xff);
        dest[pointer++] = (byte) ((value >> 16) & 0xff);
        dest[pointer++] = (byte) ((value >> 8) & 0xff);
        dest[pointer++] = (byte) ((value >> 0) & 0xff);

        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, float value) {
        int data = Float.floatToIntBits(value);

        return writeBytes(dest, pointer, data);
    }

    public static int writeBytes(byte[] dest, int pointer, double value) {
        long data = Double.doubleToLongBits(value);

        return writeBytes(dest, pointer, data);
    }

    public static int writeBytes(byte[] dest, int pointer, boolean value) {
        dest[pointer++] = (byte) (value ? 1 : 0);

        return pointer;
    }

    public static int writeBytes(byte[] dest, int pointer, String string) {
        pointer = writeBytes(dest, pointer, (short) string.length());

        return writeBytes(dest, pointer, string.getBytes());
    }

    public static byte readByte(byte[] data, int pointer) {
        return data[pointer];
    }

    public static short readShort(byte[] data, int pointer) {
        return (short) ((data[pointer] << 8) | (data[pointer + 1]));
    }

    public static char readChar(byte[] data, int pointer) {
        return (char) ((data[pointer] << 8) | (data[pointer + 1]));
    }

    public static int readInt(byte[] data, int pointer) {
        return ByteBuffer.wrap(data, pointer, 4).getInt();
       //return (int) ((data[pointer] << 24) | (data[pointer + 1] << 16) | (data[pointer + 2] << 8) | (data[pointer + 3]));
    }

    public static long readLong(byte[] data, int pointer) {
        return (long) ((data[pointer] << 56) | (data[pointer + 1] << 48) | (data[pointer + 2] << 40) | (data[pointer + 3] << 32) |
                (data[pointer + 4] << 24) | (data[pointer + 5] << 16) | (data[pointer + 6] << 8) | (data[pointer + 7]));
    }

    public static float readFloat(byte[] data, int pointer) {
        return Float.intBitsToFloat(readInt(data, pointer));
    }

    public static double readDouble(byte[] data, int pointer) {
        return Double.longBitsToDouble(readLong(data, pointer));
    }

    public static boolean readBoolean(byte[] data, int pointer) {
        return data[pointer] != 0;
    }

    public static String readString(byte[] data, int pointer, int length) {
        return new String(data, pointer, length);
    }

}
