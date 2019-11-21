package com.vosure.serialization;

import java.util.ArrayList;

import static com.vosure.serialization.SerializationWriter.writeBytes;

public class HandmadeObject {

    public static final byte CONTAINER_TYPE = ContainerType.OBJECT;
    public short nameLength;
    public byte[] name;

    private int size = 1 + 2 + 4 + 2 + 2;
    private short fieldCount;
    private ArrayList<HandmadeField> fields = new ArrayList<>();
    private short arrayCount;
    private ArrayList<HandmadeArray> arrays = new ArrayList<>();



    public HandmadeObject(String name) {
        setName(name);
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

        pointer = writeBytes(dest, pointer, arrayCount);
        for (HandmadeArray array : arrays)
            pointer = array.getBytes(dest, pointer);

        return pointer;
    }

}
