package com.vosure.serialization;

import java.nio.ByteBuffer;

public class SerializationUtils {

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

    public static void readBytes(byte[] src, int pointer, byte[] dest) {
        for (int i = 0; i < dest.length; i++) {
            dest[i] = src[pointer + i];
        }
    }

    public static short readShort(byte[] data, int pointer) {
        return ByteBuffer.wrap(data, pointer, 2).getShort();
    }

    public static void readShorts(byte[] src, int pointer, short[] dest) {
        for (int i = 0; i < dest.length; i++) {
            dest[i] = readShort(src, pointer);
            pointer += Type.getSize(Type.SHORT);
        }
    }

    public static char readChar(byte[] data, int pointer) {
        return ByteBuffer.wrap(data, pointer, 2).getChar();
    }

    public static void readChars(byte[] src, int pointer, char[] dest) {
        for (int i = 0; i < dest.length; i++) {
            dest[i] = readChar(src, pointer);
            pointer += Type.getSize(Type.CHAR);
        }
    }

    public static int readInt(byte[] data, int pointer) {
        return ByteBuffer.wrap(data, pointer, 4).getInt();
    }

    public static void readInts(byte[] src, int pointer, int[] dest) {
        for (int i = 0; i < dest.length; i++) {
            dest[i] = readInt(src, pointer);
            pointer += Type.getSize(Type.INTEGER);
        }
    }

    public static long readLong(byte[] data, int pointer) {
        return ByteBuffer.wrap(data, pointer, 8).getLong();
    }

    public static void readLongs(byte[] src, int pointer, long[] dest) {
        for (int i = 0; i < dest.length; i++) {
            dest[i] = readLong(src, pointer);
            pointer += Type.getSize(Type.LONG);
        }
    }

    public static float readFloat(byte[] data, int pointer) {
        return Float.intBitsToFloat(readInt(data, pointer));
    }

    public static void readFloats(byte[] src, int pointer, float[] dest) {
        for (int i = 0; i < dest.length; i++) {
            dest[i] = readFloat(src, pointer);
            pointer += Type.getSize(Type.FLOAT);
        }
    }

    public static double readDouble(byte[] data, int pointer) {
        return Double.longBitsToDouble(readLong(data, pointer));
    }

    public static void readDoubles(byte[] src, int pointer, double[] dest) {
        for (int i = 0; i < dest.length; i++) {
            dest[i] = readDouble(src, pointer);
            pointer += Type.getSize(Type.DOUBLE);
        }
    }

    public static boolean readBoolean(byte[] data, int pointer) {
        return data[pointer] != 0;
    }

    public static void readBooleans(byte[] src, int pointer, boolean[] dest) {
        for (int i = 0; i < dest.length; i++) {
            dest[i] = readBoolean(src, pointer);
            pointer += Type.getSize(Type.BOOLEAN);
        }
    }

    public static String readString(byte[] data, int pointer, int length) {
        return new String(data, pointer, length);
    }

}
