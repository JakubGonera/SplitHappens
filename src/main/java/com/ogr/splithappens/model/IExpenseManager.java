package com.ogr.splithappens.model;

import java.util.List;
import java.util.Map;

public interface IExpenseManager {
    List<Expense> getExpenses();

    void addExpense(Expense expense);

    boolean removeExpense(int id);

    Map<Integer, List<Pair<Integer, Integer>>> getDetailedBalances();

    int getGlobalID();

    void incrementGlobalID();
}
