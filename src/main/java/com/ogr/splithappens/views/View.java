package com.ogr.splithappens.views;

import com.ogr.splithappens.models.IExpense;
import com.ogr.splithappens.viewmodels.IViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


class DummyExpense implements IExpense{

    @Override
    public String getTitle() {
        return "Test";
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public int getAmount() {
        return 0;
    }

    @Override
    public int getPayerID() {
        return 0;
    }
}

public class View {
    private final IViewModel viewModel;
    @FXML
    Button newExpense;
    @FXML
    VBox expensesTable;
    public View(IViewModel viewModel){
        this.viewModel = viewModel;
    }

    public void setBindings(){
        viewModel.getExpensesList().addListener(new ChangeListener<List<IExpense>>() {
            @Override
            public void changed(ObservableValue<? extends List<IExpense>> observableValue, List<IExpense> iExpenses, List<IExpense> t1) {
                recalculateExpensesTable(iExpenses);
            }
        });


        List<IExpense> dummyExpenseList = Stream.generate(DummyExpense::new).limit(3).collect(Collectors.toList());

        recalculateExpensesTable(dummyExpenseList);
    }

    private void recalculateExpensesTable(List<IExpense> iExpenses){
        expensesTable.getChildren().clear();
        for (IExpense expense : iExpenses) {
            expensesTable.getChildren().add(ExpenseBlockFactory.createExpenseBlock(expense));
        }
    }
}
