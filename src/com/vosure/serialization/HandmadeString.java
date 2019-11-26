package com.vosure.serialization;

import static com.vosure.serialization.SerializationWriter.*;
import static com.vosure.serialization.SerializationWriter.readBytes;

public class HandmadeString {

    public static final byte CONTAINER_TYPE = ContainerType.STRING;
    public short nameLength;
    public byte[] name;
    public int size = 1 + 2 + 4 +4;
    public int count;

    private char[] characters;

    private HandmadeString(){

    }

    public void setName(String name) {
        assert (name.length() > Short.MAX_VALUE);

        if (this.name != null) {
            size -= this.name.length;
        }

        nameLength = (short) name.length();
        this.name = name.getBytes();
        size += nameLength;
    }

    private void updateSize() {
        size += getDataSize();
    }

    public int getBytes(byte[] dest, int pointer) {
        pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
        pointer = writeBytes(dest, pointer, nameLength);
        pointer = writeBytes(dest, pointer, name);
        pointer = writeBytes(dest, pointer, size);
        pointer = writeBytes(dest, pointer, count);
        pointer = writeBytes(dest, pointer, characters);

        return pointer;
    }

    public int getSize() {
        return size;
    }

    public int getDataSize() {
        return characters.length * Type.getSize(Type.CHAR);
    }

    public static HandmadeString Create(String name, String data) {
        HandmadeString string = new HandmadeString();
        string.setName(name);
        string.count = data.length();
        string.characters = data.toCharArray();
        string.updateSize();

        return string;
    }

    public static HandmadeString Deserialize(byte[] data, int pointer) {
        byte containerType = data[pointer++];
        assert (containerType == CONTAINER_TYPE);

        HandmadeString result = new HandmadeString();

        result.nameLength = readShort(data, pointer);
        pointer += 2;

        result.name = readString(data, pointer, result.nameLength).getBytes();
        pointer += result.nameLength;

        result.size = readInt(data, pointer);
        pointer += 4;

        result.count = readInt(data, pointer);
        pointer += 4;

        result.characters = new char[result.count];
        readChars(data, pointer, result.characters);

        pointer += result.count * Type.getSize(Type.CHAR);

        return result;
    }

}
