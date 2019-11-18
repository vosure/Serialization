package com.vosure.serialization;

import static com.vosure.serialization.SerializationWriter.*;

public class Field {

    public static final byte CONTAINER_TYPE = ContainerType.FIELD;
    public short nameLength;
    public byte[] name;
    public byte type;
    public byte[] data;

    public void setName(String name) {
        assert (name.length() > Short.MAX_VALUE);

        nameLength = (short) name.length();
        this.name = name.getBytes();
    }

    public int getBytes(byte[] dest, int pointer) {
        pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
        pointer = writeBytes(dest, pointer, nameLength);
        pointer = writeBytes(dest, pointer, name);
        pointer = writeBytes(dest, pointer, type);
        pointer = writeBytes(dest, pointer, data);

        return pointer;
    }

    public int getSize() {
        return (1 + 2 + name.length + 1 + data.length);
    }

    public static Field Byte(String name, byte value) {
        Field field = new Field();
        field.setName(name);
        field.type = Type.BYTE;
        field.data = new byte[Type.getSize(Type.BYTE)];
        writeBytes(field.data, 0, value);

        return field;
    }

    public static Field Short(String name, short value) {
        Field field = new Field();
        field.setName(name);
        field.type = Type.SHORT;
        field.data = new byte[Type.getSize(Type.SHORT)];
        writeBytes(field.data, 0, value);

        return field;
    }

    public static Field Char(String name, char value) {
        Field field = new Field();
        field.setName(name);
        field.type = Type.CHAR;
        field.data = new byte[Type.getSize(Type.CHAR)];
        writeBytes(field.data, 0, value);

        return field;
    }

    public static Field Integer(String name, int value) {
        Field field = new Field();
        field.setName(name);
        field.type = Type.INTEGER;
        field.data = new byte[Type.getSize(Type.INTEGER)];
        writeBytes(field.data, 0, value);

        return field;
    }

    public static Field Long(String name, long value) {
        Field field = new Field();
        field.setName(name);
        field.type = Type.LONG;
        field.data = new byte[Type.getSize(Type.LONG)];
        writeBytes(field.data, 0, value);

        return field;
    }

    public static Field Float(String name, float value) {
        Field field = new Field();
        field.setName(name);
        field.type = Type.FLOAT;
        field.data = new byte[Type.getSize(Type.FLOAT)];
        writeBytes(field.data, 0, value);

        return field;
    }

    public static Field Double(String name, double value) {
        Field field = new Field();
        field.setName(name);
        field.type = Type.DOUBLE;
        field.data = new byte[Type.getSize(Type.DOUBLE)];
        writeBytes(field.data, 0, value);

        return field;
    }

    public static Field Boolean(String name, boolean value) {
        Field field = new Field();
        field.setName(name);
        field.type = Type.BOOLEAN;
        field.data = new byte[Type.getSize(Type.BOOLEAN)];
        writeBytes(field.data, 0, value);

        return field;
    }


}
