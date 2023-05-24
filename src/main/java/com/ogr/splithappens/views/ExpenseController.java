package com.ogr.splithappens.views;

import com.ogr.splithappens.models.IExpense;
import com.ogr.splithappens.models.IPerson;
import com.ogr.splithappens.models.Pair;
import com.ogr.splithappens.viewmodels.IViewModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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
    public ExpenseController(IViewModel viewModel, Stage window){
        this.viewModel = viewModel;
        this.window = window;
    }

    public void setBindings(){
        valueField.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
        payerField.getItems().addAll(viewModel.getPersonsList().getValue());



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
                ExpensePayload expensePayload = new ExpensePayload(titleField.getText(), (int)value, payerField.getSelectionModel().getSelectedItem().getID(), borrowers);
                viewModel.addExpense(expensePayload);

                // TODO: fix this
                //recalculateExpensesTable(viewModel.getExpensesList().getValue());
                window.close();
            }
        });
    }
}
