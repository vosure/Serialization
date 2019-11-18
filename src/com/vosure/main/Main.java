package com.vosure.main;

import com.vosure.serialization.Array;
import com.vosure.serialization.Field;

public class Main {

    static void printBytes(byte[] data) {
        for (int i = 0; i < data.length; i++)
            System.out.printf("0x%x ", data[i]);
    }

    public static void main(String[] args) {
        int[] elements = new int[] {1, 2, 3, 4,5};
        Array array = Array.Integer("Test", elements);

        byte[] writtenData = new byte[array.getSize()];
        array.getBytes(writtenData, 0);
        printBytes(writtenData);

    }

}
