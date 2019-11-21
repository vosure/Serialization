package com.vosure.serialization;

import static com.vosure.serialization.SerializationWriter.writeBytes;

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

}
