package com.vosure.main;

import com.vosure.serialization.*;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Random;

public class Main {

    static Random random = new Random();

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

    public static void serializationTest() {
        int data[] = new int[50000];
        for (int i = 0; i < data.length; i++) {
            data[i] = random.nextInt();
        }

        HandmadeDatabase database = new HandmadeDatabase("DataBase");

        HandmadeArray array = HandmadeArray.Integer("Random Numbers", data);
        HandmadeField field = HandmadeField.Integer("Integer", 8);

        HandmadeObject object = new HandmadeObject("Entity");

        object.addArray(array);
        object.addField(field);
        object.addString(HandmadeString.Create("qwe", "test string"));

        database.addObject(object);

        byte[] stream = new byte[database.getSize()];
        database.getBytes(stream, 0);
        saveToFile("test.hmsr", stream);
    }
    public static void deserializationTest() {

    }

    public static void main(String[] args) {

    }

}
