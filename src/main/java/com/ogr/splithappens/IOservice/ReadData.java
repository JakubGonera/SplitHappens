package com.ogr.splithappens.IOservice;

import com.ogr.splithappens.model.ExpenseManager;
import com.ogr.splithappens.model.PersonsManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;

public class ReadData {
    public static PersonsManager readData() {
        PersonsManager personsManager;
        try (FileInputStream inputStream = new FileInputStream(IOService.fileName); ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            personsManager = (PersonsManager) objectInputStream.readObject();
            return personsManager;
        }
        catch (FileNotFoundException e) {
            // file not yet created - correct behaviour
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
