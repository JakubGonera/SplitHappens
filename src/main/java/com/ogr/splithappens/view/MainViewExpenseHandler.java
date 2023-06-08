package com.ogr.splithappens.view;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import com.ogr.splithappens.model.Expense;
import com.ogr.splithappens.model.Person;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainViewExpenseHandler {
    private final View view;
    MainViewExpenseHandler(View view) {
        this.view = view;
    }

    void openNewExpense() {
        // Method that opens pop-up for filling in details for a new expense
        final Stage expenseWindow = new Stage();
        ExpenseView controller = new ExpenseView(view.viewModel, expenseWindow);

        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("addExpense.fxml"));
        fxmlLoader.setController(controller);

        expenseWindow.initModality(Modality.APPLICATION_MODAL);
        expenseWindow.initOwner(view.primaryStage);
        expenseWindow.setResizable(false);

        try {
            Scene dialogScene = new Scene(fxmlLoader.load(), 400, 450);
            expenseWindow.setScene(dialogScene);
            expenseWindow.show();
            controller.setBindings();
        } catch (IOException ignored) {

        }
    }

    static Person getUniquePerson(Stream<Person> persons, int personID) {
        return persons.filter(p -> p.getID() == personID)
                .reduce((a, b) -> {
                    throw new IllegalStateException("Multiple persons with same ID");
                })
                .orElseThrow(() -> new IllegalStateException("No person matches ID"));
    }

    void recalculateExpensesTable(List<Expense> iExpenses) {
        view.expensesTable.getChildren().clear();
        List<Person> personList = view.viewModel.getPersonsList().getValue();
        //List<IPerson> personList = dummyPersonList;
        for (Expense expense : iExpenses) {
            if (expense.getAmount() > 0) {
                VBox child = ExpenseBlockFactory.createExpenseBlock(expense, getUniquePerson(personList.stream(), expense.getPayerID()), view.viewModel);
                view.expensesTable.getChildren().add(child);
                child.prefWidthProperty().bind(view.expensesTable.prefWidthProperty());
            }
        }
    }
}
