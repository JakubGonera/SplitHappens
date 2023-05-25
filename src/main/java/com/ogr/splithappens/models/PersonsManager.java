package com.ogr.splithappens.models;

import com.ogr.splithappens.views.InvalidNamePopupView;

import java.util.ArrayList;
import java.util.List;

import static com.ogr.splithappens.views.InvalidNamePopupView.*;

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
    public void addPerson(String name) {
        if(name.equals("")){
            new InvalidNamePopupView().Pop("Name cannot be empty!");
            return;
        }
        for(IPerson p: persons){
            if(p.getName().equals(name)) {
                new InvalidNamePopupView().Pop("This person already exists!");
                return;
            }
        }

        IPerson temp = new Person(name, this);
        persons.add(temp);
    }



}
