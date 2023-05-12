package com.ogr.splithappens.views;

import com.ogr.splithappens.models.IExpense;
import com.ogr.splithappens.models.IPerson;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ExpenseBlockFactory {
    static VBox createExpenseBlock(IExpense expense, IPerson person){
        VBox out = new VBox();
        out.setPadding(new Insets(10, 10, 10, 10));
        out.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, new Insets(5, 5, 5, 5))));

        GridPane grid = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(col1,col2);

        grid.add(new Text(expense.getTitle()), 0, 0);
        grid.add(new Text(Integer.toString(expense.getAmount())), 1, 0);
        grid.add(new Text(person.getName()), 1, 1);
        out.getChildren().add(grid);
        grid.prefWidthProperty().bind(out.prefWidthProperty());
        return out;
    }
}