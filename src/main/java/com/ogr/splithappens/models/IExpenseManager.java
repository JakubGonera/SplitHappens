package com.ogr.splithappens.models;

import java.util.List;
import java.util.Map;

public interface IExpenseManager {
    List<IExpense> getExpenses();

    void addExpense(IExpense expense);

    boolean removeExpense(int id);

    Map<Integer, List<Pair<Integer, Integer>>> getDetailedBalances();

    int getGlobalID();

    void incrementGlobalID();
}
