package com.ogr.splithappens.models;

import java.util.List;

public interface IExpenseManager {
    List<IExpense> getExpenses();
    void addExpense(IExpense expense);
}
