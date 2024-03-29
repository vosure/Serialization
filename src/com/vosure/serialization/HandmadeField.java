package com.vosure.serialization;

import static com.vosure.serialization.SerializationUtils.*;

public class HandmadeField extends HandmadeBase{

    public static final byte CONTAINER_TYPE = ContainerType.FIELD;
    public byte type;
    public byte[] data;

    private HandmadeField() {
    }

    public <T> T value() {
        switch (this.type) {
            case Type.BYTE:
                return (T)(Byte)readByte(data, 0);
            case Type.SHORT:
                return (T)(Short)readShort(data, 0);
            case Type.CHAR:
                return (T)(Character)readChar(data, 0);
            case Type.INTEGER:
                return (T)(Integer)readInt(data, 0);
            case Type.LONG:
                return (T)(Long)readLong(data, 0);
            case Type.FLOAT:
                return (T)(Float)readFloat(data, 0);
            case Type.DOUBLE:
                return (T)(Double)readDouble(data, 0);
            case Type.BOOLEAN:
                return (T)(Boolean)readBoolean(data, 0);
        }
        return null;
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

    public static HandmadeField Byte(String name, byte value) {
        HandmadeField field = new HandmadeField();
        field.setName(name);
        field.type = Type.BYTE;
        field.data = new byte[Type.getSize(Type.BYTE)];
        writeBytes(field.data, 0, value);

        return field;
    }

    public static HandmadeField Short(String name, short value) {
        HandmadeField field = new HandmadeField();
        field.setName(name);
        field.type = Type.SHORT;
        field.data = new byte[Type.getSize(Type.SHORT)];
        writeBytes(field.data, 0, value);

        return field;
    }

    public static HandmadeField Char(String name, char value) {
        HandmadeField field = new HandmadeField();
        field.setName(name);
        field.type = Type.CHAR;
        field.data = new byte[Type.getSize(Type.CHAR)];
        writeBytes(field.data, 0, value);

        return field;
    }

    public static HandmadeField Integer(String name, int value) {
        HandmadeField field = new HandmadeField();
        field.setName(name);
        field.type = Type.INTEGER;
        field.data = new byte[Type.getSize(Type.INTEGER)];
        writeBytes(field.data, 0, value);

        return field;
    }

    public static HandmadeField Long(String name, long value) {
        HandmadeField field = new HandmadeField();
        field.setName(name);
        field.type = Type.LONG;
        field.data = new byte[Type.getSize(Type.LONG)];
        writeBytes(field.data, 0, value);

        return field;
    }

    public static HandmadeField Float(String name, float value) {
        HandmadeField field = new HandmadeField();
        field.setName(name);
        field.type = Type.FLOAT;
        field.data = new byte[Type.getSize(Type.FLOAT)];
        writeBytes(field.data, 0, value);

        return field;
    }

    public static HandmadeField Double(String name, double value) {
        HandmadeField field = new HandmadeField();
        field.setName(name);
        field.type = Type.DOUBLE;
        field.data = new byte[Type.getSize(Type.DOUBLE)];
        writeBytes(field.data, 0, value);

        return field;
    }

    public static HandmadeField Boolean(String name, boolean value) {
        HandmadeField field = new HandmadeField();
        field.setName(name);
        field.type = Type.BOOLEAN;
        field.data = new byte[Type.getSize(Type.BOOLEAN)];
        writeBytes(field.data, 0, value);

        return field;
    }

    public static HandmadeField Deserialize(byte[] data, int pointer) {
        byte containerType = data[pointer++];
        assert (containerType == CONTAINER_TYPE);

        HandmadeField result = new HandmadeField();

        result.nameLength = readShort(data, pointer);
        pointer += 2;

        result.name = readString(data, pointer, result.nameLength).getBytes();
        pointer += result.nameLength;

        result.type = data[pointer++];

        result.data = new byte[Type.getSize(result.type)];
        readBytes(data, pointer, result.data);
        pointer += Type.getSize(result.type);

        return result;
    }

}
