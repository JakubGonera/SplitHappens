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

import static com.ogr.splithappens.view.PersonBlockFactory.createPersonBlock;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class View {
    final IViewModel viewModel;
    final Stage primaryStage;
    final MainViewExpenseHandler expenseHandler = new MainViewExpenseHandler(this);
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
                updatePeople();

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
        updatePeople();
    }

    @FXML
    TextField inputName;

    @FXML
    Accordion accordion;


    @FXML
    public void INP_OnButton(ActionEvent e) {
        System.out.println("Exit");
    }

    public void updatePeople() {
        System.out.println("Updating people");
        System.out.println("Number of expenses: " + viewModel.getExpensesList().getValue().size());

        ReadOnlyListProperty<IPerson> people = viewModel.getPersonsList();
        accordion.getPanes().remove(0, accordion.getPanes().size());
        for (IPerson ip : people.getValue()) {
            TitledPane tp = createPersonBlock(ip);
            accordion.getPanes().add(tp);
        }
    }

    @FXML //there ARE usages
    public void onAddPerson(ActionEvent e) {
        String name = inputName.getText();
        inputName.setText("");

        viewModel.addPerson(name);
        updatePeople();
    }
}
