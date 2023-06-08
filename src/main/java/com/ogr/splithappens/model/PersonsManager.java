package com.ogr.splithappens.model;

import com.ogr.splithappens.view.InvalidNamePopupView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PersonsManager implements IPersonsManager, Serializable {
    IExpenseManager expenseManager;
    List<IPerson> persons;
    int globalID = 0;

    public IExpenseManager getExpenseManager() {
        return expenseManager;
    }

    @Override
    public void incrementGlobalID() {
        globalID++;
    }

    @Override
    public int getGlobalID() {
        return globalID;
    }

    public PersonsManager(IExpenseManager expenseManager) {
        this.expenseManager = expenseManager;
        persons = new ArrayList<>();
    }

    @Override
    public List<IPerson> getPersons() {
        return persons;
    }

    @Override
    public void addPerson(String name) {
        if (name.equals("")) {
            new InvalidNamePopupView().Show("Name cannot be empty!");
            return;
        }
        for (IPerson p : persons) {
            if (p.getName().equals(name)) {
                new InvalidNamePopupView().Show("This person already exists!");
                return;
            }
        }

        IPerson temp = new Person(name, this);
        persons.add(temp);
    }

    public IPerson getPersonByName(String name) {
        for (IPerson ip : persons)
            if (ip.getName().equals(name)) return ip;
        throw new RuntimeException("There is no person with name " + name);
    }


}