package com.ogr.splithappens.models;


import com.ogr.splithappens.viewmodels.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class PersonsManager implements IPersonsManager {
    // TODO: implement PersonsManager

//    private final ViewModel viewModel;
    private List<IPerson> personList = new ArrayList<>();

//    public PersonsManager(ViewModel viewModel){
//        this.viewModel = viewModel;
//    }
    @Override
    public List<IPerson> getPersons() {
        return personList;
    }

    @Override
    public IPerson addPerson(String name) {

        if(name == null || name.equals("")) return null;
        for(var p:personList) if(p.getName().equals(name)) return null;

        Person p = new Person(name, personList.size());
        personList.add(p);
        System.out.println("added "+name);
        return p;
    }



}
