package com.ogr.splithappens.models;


import java.util.ArrayList;
import java.util.List;

public class PersonsManager implements IPersonsManager {
    // TODO: implement PersonsManager

    private List<IPerson> personList = new ArrayList<>();
    @Override
    public List<IPerson> getPersons() {
        return personList;
    }

    @Override
    public void addPerson(String name) {
        Person p = new Person(name, personList.size());
        personList.add(p);
        System.out.println("added "+name);
    }



}
