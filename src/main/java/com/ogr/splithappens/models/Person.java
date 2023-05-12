package com.ogr.splithappens.models;

import com.ogr.splithappens.viewmodels.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class Person implements IPerson {
    // TODO: implement person interface

    private final String name;
//    private final ViewModel viewModel;
    private final int id;

    public Person(String name, int id){
        this.name = name;
        this.id = id;
//        this.viewModel = viewModel
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
    public int getBalance(){
//        var expenses = getDetailedBalances();
//        int balance = 0;
//        for(var e:expenses) balance += e.balance();
//        return balance;
        return 0;
    }

    @Override
    public List<detailedBalance> getDetailedBalances() {
//        List<detailedBalance> list = new ArrayList<>();
//
//        var expenses = viewModel.getExpensesList();
//        expenses.getValue().forEach(x -> {
//            if(x.getPayerID() == id)
//                list.add(new detailedBalance(x.getTitle(), x.getID(), -x.getAmount()));
//            else
//                list.add(new detailedBalance(x.getTitle(), x.getID(), x.getAmount()));
//        });
//
//        return list;
        return null;
    }
}
