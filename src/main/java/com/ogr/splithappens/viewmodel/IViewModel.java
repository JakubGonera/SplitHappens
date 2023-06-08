package com.ogr.splithappens.viewmodel;

import com.ogr.splithappens.model.IExpense;
import com.ogr.splithappens.model.IPerson;
import javafx.beans.property.ReadOnlyListProperty;


public interface IViewModel {
    ReadOnlyListProperty<IPerson> getPersonsList();

    ReadOnlyListProperty<IExpense> getExpensesList();

    void addExpense(IExpense expense);

    boolean removeExpense(int expenseID);


    void addPerson(String name);

    IPerson getPersonByName(String name);

    void save();
}
