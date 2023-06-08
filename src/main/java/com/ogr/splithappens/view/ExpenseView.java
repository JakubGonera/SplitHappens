package com.ogr.splithappens.view;

import com.ogr.splithappens.model.Expense;
import com.ogr.splithappens.model.Person;
import com.ogr.splithappens.model.Pair;
import com.ogr.splithappens.viewmodel.IViewModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.util.*;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExpenseView {

    static final class SimpleStringConverter extends StringConverter<Number> {
        @Override
        public String toString(Number object) {
            if (object == null)
                return "0";
            return String.format("%.2f", object.floatValue());
        }

        @Override
        public Number fromString(String string) {
            return Float.parseFloat(string);
        }

    }



    private final IViewModel viewModel;
    private final Stage window;
    @FXML
    TextField titleField;
    @FXML
    TextField valueField;
    @FXML
    ChoiceBox<Person> payerField;
    @FXML
    Text errorText;
    @FXML
    Button addButton;
    @FXML
    CheckBox detailedCheck;
    @FXML
    ScrollPane splitPane;

    public ExpenseView(IViewModel viewModel, Stage window) {
        this.viewModel = viewModel;
        this.window = window;
    }

    public void setBindings() {
        ObservableList<Person> personsList = viewModel.getPersonsList().getValue();
        valueField.setTextFormatter(new TextFormatter<>(new SimpleStringConverter()));
        payerField.getItems().addAll(personsList);

        // Detailed split set up
        GridPane splitGrid = new GridPane();
        splitGrid.prefWidthProperty().bind(splitPane.widthProperty().add(-10));
        valueField.disableProperty().bind(detailedCheck.selectedProperty());
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMaxWidth(150);
        col1.setPrefWidth(150);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setMaxWidth(150);
        col2.setPrefWidth(150);
        splitGrid.getColumnConstraints().addAll(col1, col2);

        splitGrid.setVgap(7);

        int row = 0;

        List<TextField> detailedFields = new ArrayList<>();
        for (Person person : personsList) {
            Text name = new Text(person.getName());
            name.setFont(Font.font("System", 14));
            splitGrid.add(name, 0, row);
            GridPane.setMargin(name, new Insets(0, 0, 0, 10));

            TextField value = new TextField();
            value.setTextFormatter(new TextFormatter<>(new SimpleStringConverter()));
            value.setText("0");
            splitGrid.add(value, 1, row);
            detailedFields.add(value);

            Text currencyText = new Text("zł");
            splitGrid.add(currencyText, 1, row);
            GridPane.setHalignment(currencyText, HPos.RIGHT);
            GridPane.setMargin(currencyText, new Insets(0, 10, 0, 0));
            row++;
        }

        splitPane.setContent(splitGrid);
        splitGrid.setPadding(new Insets(10, 0, 0, 0));
        // ------------------

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Validation of the data
                if (Objects.equals(titleField.getText(), "")) {
                    errorText.setText("Empty title!");
                    return;
                }
                if (detailedCheck.isSelected()) {
                    boolean nonZero = false;
                    for (TextField f : detailedFields) {
                        if (Objects.equals(f.getText(), "")) {
                            errorText.setText("Detailed split not filled!");
                            return;
                        }
                        if (Float.parseFloat(f.getText()) < 0) {
                            errorText.setText("Negative value!");
                            return;
                        }
                        if (Float.parseFloat(f.getText()) != 0) {
                            nonZero = true;
                        }
                    }
                    if (!nonZero) {
                        errorText.setText("Detailed split filled with zeros!");
                        return;
                    }
                } else {
                    if (Objects.equals(valueField.getText(), "")) {
                        errorText.setText("Empty value!");
                        return;
                    }
                    if (Float.parseFloat(valueField.getText()) == 0) {
                        errorText.setText("Zero value!");
                        return;
                    }
                    if (Float.parseFloat(valueField.getText()) < 0) {
                        errorText.setText("Negative value!");
                        return;
                    }
                }
                if (payerField.getSelectionModel().isEmpty()) {
                    errorText.setText("Payer not selected!");
                    return;
                }

                List<Pair<Integer, Integer>> borrowers = new ArrayList<>();

                int sumValue = 0;
                if (detailedCheck.isSelected()) {
                    for (int i = 0; i < detailedFields.size(); i++) {
                        float value = Float.parseFloat(detailedFields.get(i).getText());
                        if (value != 0) {
                            sumValue += (int) (value * 100);
                            borrowers.add(new Pair<>(personsList.get(i).getID(), (int) (value * 100)));
                        }
                    }
                } else {
                    float value = Float.parseFloat(valueField.getText());
                    sumValue += (int) (value * 100);
                    for (Person person : personsList) {
                        borrowers.add(new Pair<>(person.getID(), (int) (value / personsList.size() * 100)));
                    }
                }

                // Construct payload and send it to the viewmodel and close window
                Expense expensePayload = new Expense(titleField.getText(), payerField.getSelectionModel().getSelectedItem().getID(), sumValue, borrowers);
                viewModel.addExpense(expensePayload);

                window.close();

            }
        });

        splitPane.disableProperty().bind(detailedCheck.selectedProperty().not());
    }
}