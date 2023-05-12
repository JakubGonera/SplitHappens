package com.ogr.splithappens.viewmodels;

import com.ogr.splithappens.models.IExpense;
import com.ogr.splithappens.models.IPerson;
import javafx.beans.property.ReadOnlyProperty;

import java.util.List;

public interface IViewModel {
    ReadOnlyProperty<List<IPerson>> getPersonsList();
    ReadOnlyProperty<List<IExpense>> getExpensesList();
    void addExpense(IExpense expense);
}
