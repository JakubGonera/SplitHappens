package com.ogr.splithappens.viewmodels;

import com.ogr.splithappens.models.IExpense;
import com.ogr.splithappens.models.IExpenseManager;
import com.ogr.splithappens.models.IPerson;
import com.ogr.splithappens.models.IPersonsManager;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.List;

public class ViewModel implements IViewModel {
    private final IPersonsManager personsManager;
    private final IExpenseManager expensesManager;
    // Remember to change personsList value whenever needed by personsList.setValue(personsManager.getPersons)

    private final SimpleObjectProperty<List<IPerson>> personsList;

    private final SimpleObjectProperty<List<IExpense>> expensesList;


    public ViewModel(IPersonsManager personsManager, IExpenseManager expensesManager){
        this.personsManager = personsManager;
        this.personsList = new SimpleObjectProperty<>(this, "personsList", personsManager.getPersons());
        this.expensesManager = expensesManager;
        this.expensesList = new SimpleObjectProperty<>(this, "expensesList", expensesManager.getExpenses());
    }

    @Override
    public ReadOnlyProperty<List<IPerson>> getPersonsList() {
        return personsList;
    }

    @Override
    public ReadOnlyProperty<List<IExpense>> getExpensesList() {
        return expensesList;
    }


    public void AddPerson(String name){
        personsManager.addPerson(name);
    }

}
