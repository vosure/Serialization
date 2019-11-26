package com.vosure.serialization;

import java.util.ArrayList;

import static com.vosure.serialization.SerializationWriter.*;

public class HandmadeObject {

    public static final byte CONTAINER_TYPE = ContainerType.OBJECT;
    public short nameLength;
    public byte[] name;

    private int size = 1 + 2 + 4 + 2 + 2 + 2;
    private short fieldCount;
    private ArrayList<HandmadeField> fields = new ArrayList<>();
    private short stringCount;
    private ArrayList<HandmadeString> strings = new ArrayList<>();
    private short arrayCount;
    private ArrayList<HandmadeArray> arrays = new ArrayList<>();

    private static final int sizeOffset = 1 + 2 + 4;

    private HandmadeObject() {

    }

    public HandmadeObject(String name) {
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

    public void addField(HandmadeField field) {
        fields.add(field);
        size += field.getSize();
        fieldCount++;
    }

    public void addString(HandmadeString string) {
        strings.add(string);
        size += string.getSize();
        stringCount++;
    }

    public void addArray(HandmadeArray array) {
        arrays.add(array);
        size += array.getSize();
        arrayCount++;
    }

    public int getSize() {
        return size;
    }

    public int getBytes(byte[] dest, int pointer) {
        pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
        pointer = writeBytes(dest, pointer, nameLength);
        pointer = writeBytes(dest, pointer, name);
        pointer = writeBytes(dest, pointer, size);

        pointer = writeBytes(dest, pointer, fieldCount);
        for (HandmadeField field : fields)
            pointer = field.getBytes(dest, pointer);

        pointer = writeBytes(dest, pointer, stringCount);
        for (HandmadeString string : strings)
            pointer = string.getBytes(dest, pointer);


        pointer = writeBytes(dest, pointer, arrayCount);
        for (HandmadeArray array : arrays)
            pointer = array.getBytes(dest, pointer);

        return pointer;
    }

    public static HandmadeObject Deserialize(byte[] data, int pointer) {
        byte containerType = data[pointer++];
        assert (containerType == CONTAINER_TYPE);

        HandmadeObject result = new HandmadeObject();

        result.nameLength = readShort(data, pointer);
        pointer += 2;

        result.name = readString(data, pointer, result.nameLength).getBytes();
        pointer += result.nameLength;

        result.size = readInt(data, pointer);
        pointer += 4;

        pointer += result.size - sizeOffset - result.nameLength;
        if (true)
            return result;

        result.fieldCount = readShort(data, pointer);
        pointer += 2;
        for (int i = 0; i < result.fieldCount; i++) {
            HandmadeField field = HandmadeField.Deserialize(data, pointer);
            result.fields.add(field);
            pointer += field.getSize();
        }

        result.stringCount = readShort(data, pointer);
        pointer += 2;
        for (int i = 0; i < result.stringCount; i++) {
            HandmadeString string = HandmadeString.Deserialize(data, pointer);
            result.strings.add(string);
            pointer += string.getSize();
        }

        result.arrayCount = readShort(data, pointer);
        pointer += 2;
        for (int i = 0; i < result.arrayCount; i++) {
            HandmadeArray array = HandmadeArray.Deserialize(data, pointer);
            result.arrays.add(array);
            pointer += array.getSize();
        }

        return result;
    }

}
