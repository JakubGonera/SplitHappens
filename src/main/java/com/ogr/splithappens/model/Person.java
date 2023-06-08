package com.ogr.splithappens.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Person implements Serializable {
    public record detailedBalance(String name, int id, int balance) { }
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

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

    public int getBalance() {
        int balance = 0;
        for (var detailedBalance : getDetailedBalances()) {
            balance += detailedBalance.balance();
        }
        return balance;
    }

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

    public String toString() {
        return getName();
    }

    public boolean canBeSetInactive() {
        return getBalance() == 0;
    }

    public boolean setInactive() {
        if (getBalance() != 0) {
            return false;
        }
        isActive = false;
        personsManager.refreshActivePersons();
        return true;
    }

    public boolean isActive() {
        return isActive;
    }
}
