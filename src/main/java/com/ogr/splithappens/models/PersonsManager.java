package com.ogr.splithappens.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PersonsManager implements IPersonsManager, Serializable {
    IExpenseManager expenseManager;
    List<IPerson>persons;
    int globalID = 0;
    public IExpenseManager getExpenseManager() {
        return expenseManager;
    }
    @Override
    public void incrementGlobalID(){
        globalID++;
    }

    @Override
    public int getGlobalID(){
        return globalID;
    }
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
