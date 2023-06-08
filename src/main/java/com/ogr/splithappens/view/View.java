package com.ogr.splithappens.view;

import com.ogr.splithappens.model.IExpense;
import com.ogr.splithappens.model.IPerson;
import com.ogr.splithappens.viewmodel.IViewModel;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;


import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

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
    VBox expensesTable;

    public View(IViewModel viewModel, Stage primaryStage) {
        this.viewModel = viewModel;
        this.primaryStage = primaryStage;
    }

    public void setBindings() {
        viewModel.getExpensesList().addListener(new ChangeListener<List<IExpense>>() {
            @Override
            public void changed(ObservableValue<? extends List<IExpense>> observableValue, List<IExpense> iExpenses, List<IExpense> t1) {
                expenseHandler.recalculateExpensesTable(t1);
                System.out.println("BINDING!");
                personHandler.updatePeople();
            }
        });

        viewModel.getPersonsList().addListener(new ChangeListener<List<IPerson>>() {
            @Override
            public void changed(ObservableValue<? extends List<IPerson>> observableValue, List<IPerson> iPeople, List<IPerson> t1) {
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
        System.out.println("Exit");
    }

    @FXML //there ARE usages
    public void onAddPerson(ActionEvent e) {
        String name = inputName.getText();
        inputName.setText("");

        viewModel.addPerson(name);
    }
}
