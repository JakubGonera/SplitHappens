package com.ogr.splithappens.viewmodels;

import com.ogr.splithappens.models.IExpense;
import com.ogr.splithappens.models.IPerson;
import javafx.beans.property.ReadOnlyListProperty;


public interface IViewModel {
    ReadOnlyListProperty<IPerson> getPersonsList();

    ReadOnlyListProperty<IExpense> getExpensesList();

    void addExpense(IExpense expense);

    boolean removeExpense(int expenseID);


    void addPerson(String name);

    IPerson getPersonByName(String name);

    void save();

    void deletePerson(IPerson person);
}
