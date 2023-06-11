package com.ogr.splithappens.view;

import com.ogr.splithappens.model.Expense;
import com.ogr.splithappens.model.Person;
import com.ogr.splithappens.viewmodel.IViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;


import java.util.List;

public class View {
    final IViewModel viewModel;
    final Stage primaryStage;
    final MainViewExpenseHandler expenseHandler = new MainViewExpenseHandler(this);
    final MainViewPersonHandler personHandler = new MainViewPersonHandler(this);
    @FXML
    Button newExpense;
    @FXML
    Button save;
    @FXML
    Accordion expensesTable;

    public View(IViewModel viewModel, Stage primaryStage) {
        this.viewModel = viewModel;
        this.primaryStage = primaryStage;
    }

    public void setBindings() {
        viewModel.getExpensesList().addListener(new ChangeListener<List<Expense>>() {
            @Override
            public void changed(ObservableValue<? extends List<Expense>> observableValue, List<Expense> iExpenses, List<Expense> t1) {
                expenseHandler.recalculateExpensesTable(t1);
                personHandler.updatePeople();
            }
        });

        viewModel.getPersonsList().addListener(new ChangeListener<List<Person>>() {
            @Override
            public void changed(ObservableValue<? extends List<Person>> observableValue, List<Person> iPeople, List<Person> t1) {
                personHandler.updatePeople();
            }
        });

        newExpense.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                expenseHandler.openNewExpense();
            }
        });
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                viewModel.save();
            }
        });

        expenseHandler.recalculateExpensesTable(viewModel.getExpensesList().getValue());
        personHandler.updatePeople();
    }
    @FXML
    TextField inputName;

    @FXML
    Accordion accordion;


    @FXML
    public void INP_OnButton(ActionEvent e) {
    }

    @FXML //there ARE usages
    public void onAddPerson(ActionEvent e) {
        String name = inputName.getText();
        inputName.setText("");

        viewModel.addPerson(name);
    }
}
