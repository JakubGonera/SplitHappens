package com.ogr.splithappens.models;

import java.util.List;
import java.util.Map;

public interface IExpenseManager {
    List<IExpense> getExpenses();
    void addExpense(IExpense expense);
    Map<Integer,List<Pair<Integer,Integer>>> getDetailedBalances();
}
