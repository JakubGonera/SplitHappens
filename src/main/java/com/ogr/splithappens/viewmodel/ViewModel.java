package com.ogr.splithappens.viewmodel;

import com.ogr.splithappens.IOservice.IOService;
import com.ogr.splithappens.model.Expense;
import com.ogr.splithappens.model.ExpenseManager;
import com.ogr.splithappens.model.Person;
import com.ogr.splithappens.model.PersonsManager;
import javafx.beans.property.*;
import javafx.collections.FXCollections;


public class ViewModel implements IViewModel {
    private final PersonsManager personsManager;
    private final ExpenseManager expensesManager;
    // Remember to change personsList value whenever needed by personsList.setValue(personsManager.getPersons)

    private final ListProperty<Person> personsList;
    private final ListProperty<Person> allPersons;

    private final ListProperty<Expense> expensesList;

    public ViewModel(PersonsManager personsManager, ExpenseManager expensesManager) {
        this.personsManager = personsManager;
        this.personsList = new SimpleListProperty<>(this, "personsList", FXCollections.observableList(personsManager.getPersons()));
        this.expensesManager = expensesManager;
        this.expensesList = new SimpleListProperty<>(this, "expensesList", FXCollections.observableList(expensesManager.getExpenses()));
        this.allPersons = new SimpleListProperty<>(this, "allPersons", FXCollections.observableList(personsManager.getAllPersons()));
    }

    @Override
    public ReadOnlyListProperty<Person> getPersonsList() {
        return personsList;
    }

    @Override
    public ReadOnlyListProperty<Person> getAllPersonsList() {
        return allPersons;
    }

    @Override
    public ReadOnlyListProperty<Expense> getExpensesList() {
        return expensesList;
    }

    @Override
    public void addExpense(Expense expense) {
        expensesManager.addExpense(expense);
        expensesList.setValue(FXCollections.observableList(expensesManager.getExpenses()));
    }

    @Override
    public boolean removeExpense(int expenseID) {
        boolean removed = expensesManager.removeExpense(expenseID);
        expensesList.setValue(FXCollections.observableList(expensesManager.getExpenses()));
        return removed;
    }


    public void addPerson(String name) {
        personsManager.addPerson(name);
        personsList.setValue(FXCollections.observableList(personsManager.getPersons()));
        allPersons.setValue(FXCollections.observableList(personsManager.getAllPersons()));
    }


    public Person getPersonByName(String name) {
        return personsManager.getPersonByName(name);
    }

    @Override
    public void save() {
        IOService.writeData(personsManager);
    }
    @Override
    public void deletePerson(Person person){
        person.setInactive();
        personsList.setValue(FXCollections.observableList(personsManager.getPersons()));
    }
}
