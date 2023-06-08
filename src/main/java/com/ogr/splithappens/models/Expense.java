package com.ogr.splithappens.models;

import java.io.Serializable;
import java.util.List;

// I am not too convinced whether we need this interface, if it will be easier, maybe delete this and keep just Person class
public class Expense implements IExpense, Serializable {
    String title;
    int payerID;
    int amount;
    int ID;
    List<Pair<Integer, Integer>> borrowers;

    public Expense(String title, int payerID, int amount, List<Pair<Integer, Integer>> borrowers, int ID) {
        this.ID = ID;
        this.title = title;
        this.payerID = payerID;
        this.amount = amount;
        this.borrowers = borrowers;
    }

    public Expense(String title, int payerID, int amount, List<Pair<Integer, Integer>> borrowers) {
        this.ID = -1;
        this.title = title;
        this.payerID = payerID;
        this.amount = amount;
        this.borrowers = borrowers;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public int getPayerID() {
        return payerID;
    }

    public List<Pair<Integer, Integer>> getBorrowers() {
        return borrowers;
    }
}