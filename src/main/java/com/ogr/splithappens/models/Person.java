package com.ogr.splithappens.models;

import java.util.List;

public class Person implements IPerson {
    // TODO: implement person interface

    private final String name;
    private final int id;

    public Person(String name, int id){
        this.name = name;
        this.id = id;
        System.out.println("creatad person: "+id+" "+name);
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public int getBalance() {
        return 0;
    }

    @Override
    public List<detailedBalance> getDetailedBalances() {
        return null;
    }
}
