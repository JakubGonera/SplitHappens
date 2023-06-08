package com.ogr.splithappens.viewmodel;

import com.ogr.splithappens.model.Expense;
import com.ogr.splithappens.model.Person;
import javafx.beans.property.ReadOnlyListProperty;


public interface IViewModel {
    ReadOnlyListProperty<Person> getPersonsList();

    ReadOnlyListProperty<Expense> getExpensesList();

    void addExpense(Expense expense);

    boolean removeExpense(int expenseID);

    void addPerson(String name);

    Person getPersonByName(String name);
    void save();
    void deletePerson(Person person);
}
