package com.ogr.splithappens.view;

import com.ogr.splithappens.model.Expense;
import com.ogr.splithappens.model.Person;
import com.ogr.splithappens.viewmodel.IViewModel;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class ExpenseBlockFactory {
    static VBox createExpenseBlock(Expense expense, Person person, IViewModel viewModel) {
        VBox out = new VBox();
        out.setPadding(new Insets(10, 10, 10, 10));
        out.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, new Insets(5, 5, 5, 5))));

        GridPane grid = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(col1, col2);

        grid.add(new Text(expense.getTitle()), 0, 0);
        grid.add(new Text(convertValue(expense.getAmount())), 1, 0);
        grid.add(new Text(person.getName()), 1, 1);
        Button removeButton = new Button("Remove");
        removeButton.setOnAction(event -> {
            viewModel.removeExpense(expense.getID());
        });
        grid.add(removeButton, 0, 1);
        out.getChildren().add(grid);
        grid.prefWidthProperty().bind(out.prefWidthProperty());
        return out;
    }

    private static String convertValue(int value) {
        String out = Integer.toString(value);
        if (out.length() > 2) {
            out = out.substring(0, out.length() - 2) + "." + out.substring(out.length() - 2);
        } else if (out.length() == 2) {
            out = "0." + out;
        } else {
            out = "0.0" + out;
        }
        return out + "zł";
    }
}
