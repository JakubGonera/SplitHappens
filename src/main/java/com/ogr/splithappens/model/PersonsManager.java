package com.ogr.splithappens.model;

import com.ogr.splithappens.view.InvalidNamePopupView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PersonsManager implements Serializable {
    ExpenseManager expenseManager;
    List<Person> persons;
    int globalID = 0;

    public ExpenseManager getExpenseManager() {
        return expenseManager;
    }

    public void incrementGlobalID() {
        globalID++;
    }

    public int getGlobalID() {
        return globalID;
    }

    public PersonsManager(ExpenseManager expenseManager) {
        this.expenseManager = expenseManager;
        persons = new ArrayList<>();
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void addPerson(String name) {
        if (name.equals("")) {
            new InvalidNamePopupView().Show("Name cannot be empty!");
            return;
        }
        for (Person p : persons) {
            if (p.getName().equals(name)) {
                new InvalidNamePopupView().Show("This person already exists!");
                return;
            }
        }

        Person temp = new Person(name, this);
        persons.add(temp);
    }

    public Person getPersonByName(String name) {
        for (Person ip : persons)
            if (ip.getName().equals(name)) return ip;
        throw new RuntimeException("There is no person with name " + name);
    }


}
