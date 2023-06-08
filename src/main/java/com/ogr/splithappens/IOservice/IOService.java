package com.ogr.splithappens.IOservice;

import com.ogr.splithappens.model.IPersonsManager;

public class IOService {
    public static IPersonsManager readData() {
        return ReadData.readData();
    }

    public static void writeData(IPersonsManager personsManager) {
        WriteData.writeData(personsManager);
    }
}
