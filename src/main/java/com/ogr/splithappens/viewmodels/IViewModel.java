package com.ogr.splithappens.viewmodels;

import com.ogr.splithappens.models.IExpense;
import com.ogr.splithappens.models.IPerson;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyProperty;

import java.util.List;

public interface IViewModel {
    ReadOnlyListProperty<IPerson> getPersonsList();
    ReadOnlyListProperty<IExpense> getExpensesList();
    void addExpense(IExpense expense);
    boolean removeExpense(int expenseID);

    /**
     * Attempts to add a person
     * @param name name of the person
     * @return if successful returns the sreated person
     */
    IPerson addPerson(String name);
}
