package com.ogr.splithappens.view;

import com.ogr.splithappens.model.Expense;
import com.ogr.splithappens.model.Person;
import com.ogr.splithappens.viewmodel.IViewModel;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;


public class ExpenseBlockFactory {
    static TitledPane createExpenseBlock(Expense expense, Person person, IViewModel viewModel) {
        TitledPane out = new TitledPane();
        out.setPadding(new Insets(10, 10, 10, 10));
        Button removeButton = new Button("X");
        removeButton.setOnAction(event -> {
            viewModel.removeExpense(expense.getID());
        });

        BorderPane borderPane = new BorderPane();
        Label titleOfTitledPane = new Label(expense.getTitle() + "\n"+ Common.formatAmountWithCurrency(expense.getAmount()) +" by "+person.getName());
        borderPane.setLeft(titleOfTitledPane);
        borderPane.setRight(removeButton);
        borderPane.prefWidthProperty().bind(out.widthProperty().subtract(40));
        out.setGraphic(borderPane);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // Set up the grid to display borrowers, date, category, description, and photo
        GridPane gridPane = new GridPane();
        gridPane.prefWidthProperty().bind(gridPane.widthProperty().add(-10));
        gridPane.getColumnConstraints().clear();
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMaxWidth(300);
        col1.setPrefWidth(300);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setMaxWidth(150);
        col2.setPrefWidth(150);
        gridPane.getColumnConstraints().addAll(col1, col2);

        gridPane.setVgap(7);

        Label gridTitle = new Label("Borrowers:");
        gridPane.add(gridTitle, 0, 0);
        GridPane.setMargin(gridTitle, new Insets(0, 0, 0, 10));


        for(int i = 0; i < expense.getBorrowers().size(); i++) {
            final int index = i;
            Person borrower = viewModel.getAllPersonsList().stream().filter(p -> p.getID() == expense.getBorrowers().get(index).first).findFirst().orElse(null);
            Label borrowerLabel = new Label(borrower.getName());
            Label amountLabel = new Label(Common.formatAmountWithCurrency(expense.getBorrowers().get(index).second));
            gridPane.add(borrowerLabel, 0, i + 1);
            gridPane.add(amountLabel, 1, i + 1);
            GridPane.setMargin(borrowerLabel, new Insets(0, 0, 0, 20));
        }

        Label dateLabel = new Label("Date: " + expense.getDateAdded().toString());
        gridPane.add(dateLabel, 0, expense.getBorrowers().size() + 1);
        GridPane.setMargin(dateLabel, new Insets(0, 0, 0, 10));

        Label categoryLabel = new Label("Category: " + expense.getCategory().toString());
        gridPane.add(categoryLabel, 0, expense.getBorrowers().size() + 2);
        GridPane.setMargin(categoryLabel, new Insets(0, 0, 0, 10));

        int pictureIndex = expense.getBorrowers().size() + 3;
        if(expense.getDescription() != null){
            Label descriptionLabel = new Label("Description: " + expense.getDescription());
            gridPane.add(descriptionLabel, 0, expense.getBorrowers().size() + 3);
            GridPane.setMargin(descriptionLabel, new Insets(0, 0, 0, 10));
            pictureIndex++;
        }

        if(expense.getImage() != null) {
            Label imageLabel = new Label("Photo: ");
            gridPane.add(imageLabel, 0, pictureIndex);
            GridPane.setMargin(imageLabel, new Insets(0, 0, 0, 10));
            ImageView imageView = new ImageView(expense.getImage());
            imageView.setFitHeight(200);
            imageView.setFitWidth(200);
            gridPane.add(imageView, 0, pictureIndex + 1);
            GridPane.setMargin(imageView, new Insets(0, 0, 0, 10));
        }

        scrollPane.setContent(gridPane);
        out.setContent(scrollPane);

        return out;
    }
}
