package com.ogr.splithappens.models;

import java.util.ArrayList;
import java.util.List;

public class Person implements IPerson {
    static int globalID;
    String name;
    int id;
    PersonsManager personsManager;
    Person(String name, PersonsManager personsManager){
        this.name = name;
        this.personsManager = personsManager;
        id = globalID;
        globalID++;
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
        int balance = 0;
        for(var detailedBalance : getDetailedBalances()){
            balance+=detailedBalance.balance();
        }
        return balance;
    }

    @Override
    public List<detailedBalance> getDetailedBalances() {
        List<detailedBalance>result = new ArrayList<>();
        for(var x: personsManager.expenseManager.getDetailedBalances().get(id)){
            int amount = x.second;
            int receivingIndex = x.first;
            String receivingName = "";
            for(var person : personsManager.getPersons()){
                if(person.getID() == receivingIndex){
                    receivingName = String.valueOf(person.getName());
                    break;
                }
            }
            result.add(new detailedBalance(receivingName, receivingIndex, amount));
        }
        return result;
    }
}
