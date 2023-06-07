package com.ogr.splithappens.IOservice;

import com.ogr.splithappens.models.IPersonsManager;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class WriteData {
    static String fileName = "data.txt";

    public static String getFileName() {
        return fileName;
    }

    public static void writeData(IPersonsManager personsManager) {
        try {
            FileOutputStream outputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(personsManager);
        } catch (Exception e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
