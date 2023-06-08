package com.ogr.splithappens.IOservice;

import com.ogr.splithappens.model.PersonsManager;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class WriteData {
    static String fileName = "data.txt";

    public static String getFileName() {
        return fileName;
    }

    public static void writeData(PersonsManager personsManager) {
        try {
            FileOutputStream outputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(personsManager);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
