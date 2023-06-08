package com.ogr.splithappens.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Person implements IPerson, Serializable {
    String name;
    int id;
    boolean isActive = true;
    PersonsManager personsManager;
    Person(String name, PersonsManager personsManager) {
        this.name = name;
        this.personsManager = personsManager;
        id = personsManager.getGlobalID();
        personsManager.incrementGlobalID();
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
        for (var detailedBalance : getDetailedBalances()) {
            balance += detailedBalance.balance();
        }
        return balance;
    }

    @Override
    public List<detailedBalance> getDetailedBalances() {
        List<detailedBalance> result = new ArrayList<>();
        if (!personsManager.expenseManager.getDetailedBalances().containsKey(id))
            return new ArrayList<>();
        for (var x : personsManager.expenseManager.getDetailedBalances().get(id)) {
            int amount = x.second;
            int receivingIndex = x.first;
            String receivingName = "";
            for (var person : personsManager.getPersons()) {
                if (person.getID() == receivingIndex) {
                    receivingName = String.valueOf(person.getName());
                    break;
                }
            }
            result.add(new detailedBalance(receivingName, receivingIndex, amount));
        }
        return result;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean canBeSetInactive() {
        return getBalance() == 0;
    }

    @Override
    public boolean setInactive() {
        if (getBalance() != 0) {
            return false;
        }
        isActive = false;
        personsManager.refreshActivePersons();
        return true;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }
}
