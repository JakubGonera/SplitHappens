package com.ogr.splithappens.viewmodel;

import com.ogr.splithappens.model.IExpense;
import com.ogr.splithappens.model.Person;
import javafx.beans.property.ReadOnlyListProperty;


public interface IViewModel {
    ReadOnlyListProperty<Person> getPersonsList();

    ReadOnlyListProperty<IExpense> getExpensesList();

    void addExpense(IExpense expense);

    boolean removeExpense(int expenseID);


    void addPerson(String name);

    Person getPersonByName(String name);

    void save();
}
