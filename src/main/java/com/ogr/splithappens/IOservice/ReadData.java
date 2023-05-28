package com.ogr.splithappens.IOservice;
import com.ogr.splithappens.models.ExpenseManager;
import com.ogr.splithappens.models.IExpenseManager;
import com.ogr.splithappens.models.IPersonsManager;
import com.ogr.splithappens.models.PersonsManager;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class ReadData {
static String fileName = WriteData.getFileName();
    public static IPersonsManager readData(){
        IPersonsManager personsManager;
        try {
            FileInputStream inputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            personsManager = (IPersonsManager) objectInputStream.readObject();
        }
        catch (Exception e){
            System.out.println("Error reading from file: " + e.getMessage());
            IExpenseManager em = new ExpenseManager();
            personsManager = new PersonsManager(em);
            return personsManager;
        }
        return personsManager;
    }
}
