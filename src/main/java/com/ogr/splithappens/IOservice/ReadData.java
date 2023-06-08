package com.ogr.splithappens.IOservice;

import com.ogr.splithappens.model.ExpenseManager;
import com.ogr.splithappens.model.PersonsManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;

public class ReadData {
    static String fileName = WriteData.getFileName();

    public static PersonsManager readData() {
        PersonsManager personsManager;
        try {
            FileInputStream inputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            personsManager = (PersonsManager) objectInputStream.readObject();
            return personsManager;
        }
        catch (FileNotFoundException e) {
            // file not yet created - correct behaviour
            System.out.println("1");
            ExpenseManager em = new ExpenseManager();
            personsManager = new PersonsManager(em);
            return personsManager;
        }
        catch (Exception e) {
            // unknown error
            e.printStackTrace();
            ExpenseManager em = new ExpenseManager();
            personsManager = new PersonsManager(em);
            return personsManager;
        }
    }
}
