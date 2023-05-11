package com.ogr.splithappens.views;

import com.ogr.splithappens.models.IExpense;
import com.ogr.splithappens.viewmodels.IViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.util.converter.NumberStringConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;




public class View {
    static class DummyExpense implements IExpense{

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
    static class ExpensePayload implements IExpense{
        String title;
        int value;
        int payerID;
        public ExpensePayload(String title, int value, int payerID){
            this.title = title;
            this.value = value;
            this.payerID = payerID;
        }
        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public int getID() {
            // As a payload this doesn't have an ID, it should be assigned by the model
            return -1;
        }

        @Override
        public int getAmount() {
            return value;
        }

        @Override
        public int getPayerID() {
            return payerID;
        }
    }
    private final IViewModel viewModel;
    private final Stage primaryStage;
    @FXML
    Button newExpense;
    @FXML
    VBox expensesTable;

    public View(IViewModel viewModel, Stage primaryStage){
        this.viewModel = viewModel;
        this.primaryStage = primaryStage;
    }

    public void setBindings(){
        viewModel.getExpensesList().addListener(new ChangeListener<List<IExpense>>() {
            @Override
            public void changed(ObservableValue<? extends List<IExpense>> observableValue, List<IExpense> iExpenses, List<IExpense> t1) {
                recalculateExpensesTable(iExpenses);
            }
        });

        newExpense.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openNewExpense();
            }
        });


        List<IExpense> dummyExpenseList = Stream.generate(DummyExpense::new).limit(3).collect(Collectors.toList());

        recalculateExpensesTable(dummyExpenseList);
    }

    private void openNewExpense(){
        // Method that opens pop-up for filling in details for a new expense + bindings to viewmodel to add expense + simple validation
        final Stage expenseWindow = new Stage();
        expenseWindow.initModality(Modality.APPLICATION_MODAL);
        expenseWindow.initOwner(primaryStage);


        VBox dialogVbox = new VBox(20);
        GridPane form = new GridPane();
        dialogVbox.getChildren().add(form);
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(25, 25, 25, 25));

        Text title = new Text("Add a new expense");
        title.setFont(Font.font("Montserrat", FontWeight.NORMAL, 20));
        form.add(title, 0, 0, 2, 1);

        Label expenseTitle = new Label("Expense title:");
        form.add(expenseTitle, 0, 1);

        TextField expenseTextField = new TextField();
        form.add(expenseTextField, 1, 1);

        Label value = new Label("Value:");
        form.add(value, 0, 2);

        TextField valueTextField = new TextField();
        valueTextField.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
        form.add(valueTextField, 1, 2);

        Button btn = new Button("Add");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Validation of the data

                // If not valid show error

                // Construct payload and send it to the viewmodel and close window
                ExpensePayload expensePayload = new ExpensePayload(expenseTextField.getText(), Integer.parseInt(valueTextField.getText()), 0);
                viewModel.addExpense(expensePayload);
                expenseWindow.close();
            }
        });
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        form.add(hbBtn, 1, 4);

        final Text errorText = new Text();
        form.add(errorText, 1, 6);

        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        expenseWindow.setScene(dialogScene);
        expenseWindow.show();
    }
    private void recalculateExpensesTable(List<IExpense> iExpenses){
        expensesTable.getChildren().clear();
        for (IExpense expense : iExpenses) {
            expensesTable.getChildren().add(ExpenseBlockFactory.createExpenseBlock(expense));
        }
    }
}
