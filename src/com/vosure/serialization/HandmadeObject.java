package com.vosure.serialization;

import java.util.ArrayList;

import static com.vosure.serialization.SerializationUtils.*;

public class HandmadeObject extends HandmadeBase{

    public static final byte CONTAINER_TYPE = ContainerType.OBJECT;

    private short fieldCount;
    private ArrayList<HandmadeField> fields = new ArrayList<>();
    private short stringCount;
    private ArrayList<HandmadeString> strings = new ArrayList<>();
    private short arrayCount;
    private ArrayList<HandmadeArray> arrays = new ArrayList<>();

    private HandmadeObject() {

    }

    public HandmadeObject(String name) {
        size += 1 + 2 + 2 + 2;
        setName(name);
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

    public HandmadeString findString(String name) {
        for (HandmadeString string : strings) {
            if (string.getName().equals(name))
                return string;
        }

        return null;
    }

    public HandmadeArray findArray(String name) {
        for (HandmadeArray array : arrays) {
            if (array.getName().equals(name))
                return array;
        }

        return null;
    }

    public HandmadeField findField(String name) {
        for (HandmadeField field : fields) {
            if (field.getName().equals(name))
                return field;
        }

        return null;
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
