package com.vosure.serialization;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static com.vosure.serialization.SerializationWriter.*;

public class HandmadeDatabase {

    public static final byte[] HEADER = "HSDB".getBytes();
    public static final byte CONTAINER_TYPE = ContainerType.DATABASE;
    public short nameLength;
    public byte[] name;

    public int size = HEADER.length + 1 + 2 + 4 + 2;
    public short objectCount;
    public ArrayList<HandmadeObject> objects = new ArrayList<>();

    private HandmadeDatabase() {

    }

    public HandmadeDatabase(String name) {
        setName(name);
    }

    public String getName() {
        return new String(name, 0, nameLength);
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

    public static HandmadeDatabase Deserialize(byte[] data) {
        int pointer = 0;
        assert (readString(data, pointer, HEADER.length).equals(HEADER));
        pointer += HEADER.length;

        byte containerType = readByte(data, pointer++);
        assert (containerType == CONTAINER_TYPE);

        HandmadeDatabase result = new HandmadeDatabase();

        result.nameLength = readShort(data, pointer);
        pointer += 2;

        result.name = readString(data, pointer, result.nameLength).getBytes();
        pointer += result.nameLength;

        result.size = readInt(data, pointer);
        pointer += 4;

        result.objectCount = readShort(data, pointer);
        pointer += 2;
        for (int i = 0; i < result.objectCount; i++) {
            HandmadeObject object = HandmadeObject.Deserialize(data, pointer);
            result.objects.add(object);
            pointer += object.getSize();
        }

        return result;
    }

    public static HandmadeDatabase DeserializeFromFile(String path) {
        byte[] buffer = null;

        try {
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream(path));
            buffer = new byte[stream.available()];
            stream.read(buffer);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Deserialize(buffer);
    }

}
