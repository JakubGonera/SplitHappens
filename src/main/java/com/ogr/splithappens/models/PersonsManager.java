package com.ogr.splithappens.models;

import java.util.ArrayList;
import java.util.List;

public class PersonsManager implements IPersonsManager {
    // TODO: implement PersonsManager
    IExpenseManager expenseManager;
    List<IPerson>persons;
    public PersonsManager(IExpenseManager expenseManager){
        this.expenseManager = expenseManager;
        persons = new ArrayList<>();
    }
    @Override
    public List<IPerson> getPersons() {
        return persons;
    }
    @Override
    public IPerson addPerson(String name) {
        IPerson temp = new Person(name, this);
        persons.add(temp);
        return temp;
    }



}
