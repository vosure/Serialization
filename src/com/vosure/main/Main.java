package com.vosure.main;

import com.vosure.serialization.SerializationWriter;

public class Main {

    static void printBytes(byte[] data) {
        for (int i = 0; i < data.length; i++)
            System.out.printf("0x%x ", data[i]);
    }

    public static void main(String[] args) {
        byte[] data = new byte[16];

        long number = Long.MAX_VALUE;

        int pointer = SerializationWriter.writeBytes(data, 0, number);
        pointer = SerializationWriter.writeBytes(data, pointer, number);

        printBytes(data);
    }

}
