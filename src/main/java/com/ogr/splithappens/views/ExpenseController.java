package com.ogr.splithappens.views;

import com.ogr.splithappens.models.IExpense;
import com.ogr.splithappens.models.IPerson;
import com.ogr.splithappens.models.Pair;
import com.ogr.splithappens.viewmodels.IViewModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
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

        // Detailed split set up
        GridPane splitGrid = new GridPane();
        splitGrid.prefWidthProperty().bind(splitPane.widthProperty());
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
        for(IPerson person : personsList){
            Text name = new Text(person.getName());
            name.setFont(Font.font("System", 14));
            splitGrid.add(name, 0, row);

            TextField value = new TextField();
            // TODO: change the formatter so that it replaces empty values with 0s
            value.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
            value.setText("0");
            splitGrid.add(value, 1, row);
            detailedFields.add(value);
            row++;
        }

        splitPane.setContent(splitGrid);
        // ------------------

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Validation of the data
                if(Objects.equals(titleField.getText(), "")){
                    errorText.setText("Empty title!");
                    return;
                }
                if(detailedCheck.isSelected()){
                    boolean nonZero = false;
                    for(TextField f : detailedFields){
                        if(Objects.equals(f.getText(), "")){
                            errorText.setText("Detailed split not filled!");
                            return;
                        }
                        if(Float.parseFloat(f.getText()) != 0){
                            nonZero = true;
                        }
                    }
                    if(!nonZero){
                        errorText.setText("Detailed split filled with zeros!");
                        return;
                    }
                }
                else{
                    if(Objects.equals(valueField.getText(), "")){
                        errorText.setText("Empty value!");
                        return;
                    }
                    if(Float.parseFloat(valueField.getText()) == 0){
                        errorText.setText("Zero value!");
                        return;
                    }
                }
                if(payerField.getSelectionModel().isEmpty()){
                    errorText.setText("Payer not selected!");
                    return;
                }

                List<Pair<Integer, Integer>> borrowers = new ArrayList<>();

                int sumValue = 0;
                if(detailedCheck.isSelected()){
                    for(int i = 0; i < detailedFields.size(); i++){
                        float value = Float.parseFloat(detailedFields.get(i).getText());
                        if(value != 0){
                            sumValue += (int)(value * 100);
                            borrowers.add(new Pair<>(personsList.get(i).getID(), (int)(value * 100)));
                        }
                    }
                }
                else{
                    float value = Float.parseFloat(valueField.getText());
                    sumValue += (int)(value/personsList.size() * 100);
                    for(IPerson person : personsList){
                        borrowers.add(new Pair<>(person.getID(), (int)(value/personsList.size() * 100)));
                    }
                }

                // Construct payload and send it to the viewmodel and close window
                ExpensePayload expensePayload = new ExpensePayload(titleField.getText(), sumValue, payerField.getSelectionModel().getSelectedItem().getID(), borrowers);
                viewModel.addExpense(expensePayload);

                window.close();
            }
        });

        splitPane.disableProperty().bind(detailedCheck.selectedProperty().not());
    }
}
