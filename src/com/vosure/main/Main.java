package com.vosure.main;

import com.vosure.serialization.Field;
import com.vosure.serialization.IntField;
import com.vosure.serialization.SerializationWriter;

public class Main {

    static void printBytes(byte[] data) {
        for (int i = 0; i < data.length; i++)
            System.out.printf("0x%x ", data[i]);
    }

    public static void main(String[] args) {

        Field field = new IntField("Test", 8);
        byte[] data = new byte[100];

        field.getBytes(data, 0);
        printBytes(data);

    }

}
