package com.ogr.splithappens.views;

import com.ogr.splithappens.models.IExpense;
import com.ogr.splithappens.models.IPerson;
import com.ogr.splithappens.models.Pair;
import com.ogr.splithappens.viewmodels.IViewModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.skin.TextAreaSkin;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExpenseController {
    static class ExpensePayload implements IExpense {
        String title;
        int value;
        int payerID;
        List<Pair<Integer, Integer>> borrowers;
        public ExpensePayload(String title, int value, int payerID, List<Pair<Integer, Integer>> borrowers){
            this.title = title;
            this.value = value;
            this.payerID = payerID;
            this.borrowers = borrowers;
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

        @Override
        public List<Pair<Integer, Integer>> getBorrowers() {
            return borrowers;
        }
    }

    private final IViewModel viewModel;
    private final Stage window;
    @FXML
    TextField titleField;
    @FXML
    TextField valueField;
    @FXML
    ChoiceBox<IPerson> payerField;
    @FXML
    Text errorText;
    @FXML
    Button addButton;
    @FXML
    CheckBox detailedCheck;
    @FXML
    ScrollPane splitPane;
    public ExpenseController(IViewModel viewModel, Stage window){
        this.viewModel = viewModel;
        this.window = window;
    }

    public void setBindings(){
        ObservableList<IPerson> personsList = viewModel.getPersonsList().getValue();
        valueField.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
        payerField.getItems().addAll(personsList);

        GridPane splitGrid = new GridPane();
        splitGrid.prefWidthProperty().bind(splitPane.widthProperty());
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMaxWidth(150);
        col1.setPrefWidth(150);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setMaxWidth(150);
        col2.setPrefWidth(150);
        splitGrid.getColumnConstraints().addAll(col1, col2);

        splitGrid.setVgap(7);

        int row = 0;

        for(IPerson person : personsList){
            Text name = new Text(person.getName());
            name.setFont(Font.font("System", 14));
            splitGrid.add(name, 0, row);

            TextField value = new TextField();
            value.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
            splitGrid.add(value, 1, row);
            row++;
        }

        splitPane.setContent(splitGrid);

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Validation of the data
                if(Objects.equals(titleField.getText(), "")){
                    errorText.setText("Empty title!");
                    return;
                }
                if(Objects.equals(valueField.getText(), "")){
                    errorText.setText("Empty value!");
                    return;
                }
                if(payerField.getSelectionModel().isEmpty()){
                    errorText.setText("Payer not selected!");
                    return;
                }

                float value = Float.parseFloat(valueField.getText());

                List<Pair<Integer, Integer>> borrowers = new ArrayList<>();
                List<IPerson> personList = viewModel.getPersonsList().getValue();
                for(IPerson person : personList){
                    borrowers.add(new Pair<>(person.getID(), (int)(value/personList.size() * 100)));
                }

                // Construct payload and send it to the viewmodel and close window
                ExpensePayload expensePayload = new ExpensePayload(titleField.getText(), 100 * (int)value, payerField.getSelectionModel().getSelectedItem().getID(), borrowers);
                viewModel.addExpense(expensePayload);

                window.close();
            }
        });

        splitPane.disableProperty().bind(detailedCheck.selectedProperty().not());
    }
}
