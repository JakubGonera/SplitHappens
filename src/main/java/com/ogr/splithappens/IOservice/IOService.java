package com.ogr.splithappens.IOservice;

import com.ogr.splithappens.model.PersonsManager;

public class IOService {
    public static PersonsManager readData() {
        return ReadData.readData();
    }

    public static void writeData(PersonsManager personsManager) {
        WriteData.writeData(personsManager);
    }
}
