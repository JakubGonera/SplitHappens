package com.ogr.splithappens.models;
// I am not too convinced whether we need this interface, if it will be easier, maybe delete this and keep just Person class
public class Expense implements IExpense {
    // TODO: implement
    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public int getAmount() {
        return 0;
    }

    @Override
    public int getPayerID() {
        return 0;
    }
}
