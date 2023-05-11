package com.ogr.splithappens.views;

import com.ogr.splithappens.models.IExpense;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ExpenseBlockFactory {
    static Node createExpenseBlock(IExpense expense){
        VBox out = new VBox();
        out.setPadding(new Insets(10, 10, 10, 10));
        out.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, new Insets(5, 5, 5, 5))));
        out.getChildren().add(new Text(expense.getTitle()));
        out.getChildren().add(new Text(Integer.toString(expense.getAmount())));

//        out.setPrefSize(30, 30);
//        out.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
//        out.setBorder(new Border(new BorderStroke(Color.valueOf("#9E9E9E"),
//                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5)
//                )));
        return out;
    }
}
