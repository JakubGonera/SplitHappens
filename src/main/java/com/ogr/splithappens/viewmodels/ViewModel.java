package com.ogr.splithappens.viewmodels;

import com.ogr.splithappens.IOservice.IOService;
import com.ogr.splithappens.models.IExpense;
import com.ogr.splithappens.models.IExpenseManager;
import com.ogr.splithappens.models.IPerson;
import com.ogr.splithappens.models.IPersonsManager;
import javafx.beans.property.*;
import javafx.collections.FXCollections;


public class ViewModel implements IViewModel {
    private final IPersonsManager personsManager;
    private final IExpenseManager expensesManager;
    // Remember to change personsList value whenever needed by personsList.setValue(personsManager.getPersons)

    private final ListProperty<IPerson> personsList;

    private final ListProperty<IExpense> expensesList;

    public ViewModel(IPersonsManager personsManager, IExpenseManager expensesManager) {
        this.personsManager = personsManager;
        this.personsList = new SimpleListProperty<>(this, "personsList", FXCollections.observableList(personsManager.getPersons()));
        this.expensesManager = expensesManager;
        this.expensesList = new SimpleListProperty<>(this, "expensesList", FXCollections.observableList(expensesManager.getExpenses()));
    }

    @Override
    public ReadOnlyListProperty<IPerson> getPersonsList() {
        return personsList;
    }

    @Override
    public ReadOnlyListProperty<IExpense> getExpensesList() {
        return expensesList;
    }

    @Override
    public void addExpense(IExpense expense) {
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
    }


    public IPerson getPersonByName(String name) {
        return personsManager.getPersonByName(name);
    }

    @Override
    public void save() {
        IOService.writeData(personsManager);
    }
}
