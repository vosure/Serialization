package com.vosure.main;

import com.vosure.serialization.HandmadeArray;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

public class Main {

    static void printBytes(byte[] data) {
        for (int i = 0; i < data.length; i++)
            System.out.printf("0x%x ", data[i]);
    }

    static void saveToFile(String path, byte[] data) {
        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(path));
            stream.write(data);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int[] elements = new int[] {1, 2, 3, 4,5};
        HandmadeArray array = HandmadeArray.Integer("Test", elements);

        byte[] writtenData = new byte[array.getSize()];
        array.getBytes(writtenData, 0);
        printBytes(writtenData);

    }

}
