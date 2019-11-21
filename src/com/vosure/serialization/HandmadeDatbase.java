package com.vosure.serialization;

import java.util.ArrayList;

import static com.vosure.serialization.SerializationWriter.writeBytes;

public class HandmadeDatbase {

    public static final byte[] HEADER = "HSDB".getBytes();
    public static final byte CONTAINER_TYPE = ContainerType.DATABASE;
    public short nameLength;
    public byte[] name;

    private int size = HEADER.length + 1 + 2 + 4 + 2;
    private short objectCount;
    private ArrayList<HandmadeObject> objects = new ArrayList<>();

    public HandmadeDatbase(String name) {

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

    public void addObject(HandmadeObject object) {
        objects.add(object);
        size += object.getSize();
        objectCount++;
    }

    public int getSize() {
        return size;
    }

    public int getBytes(byte[] dest, int pointer) {
        pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
        pointer = writeBytes(dest, pointer, nameLength);
        pointer = writeBytes(dest, pointer, name);
        pointer = writeBytes(dest, pointer, size);

        pointer = writeBytes(dest, pointer, objectCount);
        for (HandmadeObject object : objects)
            pointer = object.getBytes(dest, pointer);

        return pointer;
    }

}
