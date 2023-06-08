package com.ogr.splithappens.model;

import java.util.List;

public interface IExpense {
    String getTitle();

    int getID();

    int getAmount();

    int getPayerID();

    List<Pair<Integer, Integer>> getBorrowers();
}
