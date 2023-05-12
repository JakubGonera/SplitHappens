package com.ogr.splithappens.models;

import java.util.ArrayList;
import java.util.List;

public class PersonsManager implements IPersonsManager {
    // TODO: implement PersonsManager
    ExpenseManager expenseManager;
    List<IPerson>persons;
    PersonsManager(ExpenseManager expenseManager){
        this.expenseManager = expenseManager;
        persons = new ArrayList<>();
    }
    @Override
    public List<IPerson> getPersons() {
        return persons;
    }
    @Override
    public void addPerson(String name) {
        persons.add(new Person(name, this));
    }



}
