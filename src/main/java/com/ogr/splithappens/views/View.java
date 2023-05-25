package com.ogr.splithappens.views;

import com.ogr.splithappens.models.IExpense;
import com.ogr.splithappens.models.IPerson;
import com.ogr.splithappens.models.Pair;
import com.ogr.splithappens.viewmodels.IViewModel;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.util.converter.NumberStringConverter;

import static com.ogr.splithappens.views.PersonBlockFactory.createPersonBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class View {
    static class DummyExpense implements IExpense{
        static int counter = 0;
        int payerID = counter++;
        @Override
        public String getTitle() {
            return "Expense" + Integer.toString(payerID);
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
            return payerID;
        }

        @Override
        public List<Pair<Integer, Integer>> getBorrowers() {
            return null;
        }
    }
    static class DummyPerson implements IPerson{
        static int globalCounter = 0;
        int id = globalCounter++;
        @Override
        public String getName() {
            return "Person" + Integer.toString(id);
        }

        @Override
        public int getID() {
            return id;
        }

        @Override
        public int getBalance() {
            return 0;
        }

        @Override
        public List<detailedBalance> getDetailedBalances() {
            return null;
        }

        @Override
        public String toString(){
            return getName();
        }
    }
    static class ExpensePayload implements IExpense{
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
    private final Stage primaryStage;
    @FXML
    Button newExpense;
    @FXML
    VBox expensesTable;

    List<IPerson> dummyPersonList = Stream.generate(DummyPerson::new).limit(3).collect(Collectors.toList());
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

        //List<IExpense> dummyExpenseList = Stream.generate(DummyExpense::new).limit(3).collect(Collectors.toList());

        // recalculateExpensesTable(dummyExpenseList);
        recalculateExpensesTable(viewModel.getExpensesList().getValue());
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

        Label payer = new Label("Payer:");
        form.add(payer, 0, 3);

        ChoiceBox<IPerson> payerPicker = new ChoiceBox<>();
        for(IPerson person : viewModel.getPersonsList().getValue()){
            payerPicker.getItems().add(person);
        }

//        for(IPerson person : dummyPersonList){
//            payerPicker.getItems().add(person);
//        }
        form.add(payerPicker, 1, 3);

        Button btn = new Button("Add");
        final Text errorText = new Text();
        errorText.setFill(Color.FIREBRICK);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Validation of the data
                if(Objects.equals(expenseTextField.getText(), "")){
                    errorText.setText("Empty title!");
                    return;
                }
                if(Objects.equals(valueTextField.getText(), "")){
                    errorText.setText("Empty value!");
                    return;
                }
                if(payerPicker.getSelectionModel().isEmpty()){
                    errorText.setText("Payer not selected!");
                    return;
                }

                float value = Float.parseFloat(valueTextField.getText());

                List<Pair<Integer, Integer>> borrowers = new ArrayList<>();
                List<IPerson> personList = viewModel.getPersonsList().getValue();
                for(IPerson person : personList){
                    borrowers.add(new Pair<>(person.getID(), (int)(value/personList.size() * 100)));

                }

                // Construct payload and send it to the viewmodel and close window
                ExpensePayload expensePayload = new ExpensePayload(expenseTextField.getText(), 100*(int)value, payerPicker.getSelectionModel().getSelectedItem().getID(), borrowers);
                viewModel.addExpense(expensePayload);

                recalculateExpensesTable(viewModel.getExpensesList().getValue());
                expenseWindow.close();
            }
        });
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        form.add(hbBtn, 1, 5);

        form.add(errorText, 1, 6);

        Scene dialogScene = new Scene(dialogVbox, 300, 250);
        expenseWindow.setScene(dialogScene);
        expenseWindow.show();
    }

    private static IPerson getUniquePerson(Stream<IPerson> persons, int personID) {
        return persons.filter(p -> p.getID() == personID)
                .reduce((a, b) -> {
                    throw new IllegalStateException("Multiple persons with same ID");
                })
                .orElseThrow(() -> new IllegalStateException("No person matches ID"));
    }

    private void recalculateExpensesTable(List<IExpense> iExpenses){
        expensesTable.getChildren().clear();
        List<IPerson> personList = viewModel.getPersonsList().getValue();
        //List<IPerson> personList = dummyPersonList;
        for (IExpense expense : iExpenses) {
            VBox child = ExpenseBlockFactory.createExpenseBlock(expense, getUniquePerson(personList.stream(), expense.getPayerID()));
            expensesTable.getChildren().add(child);
            child.prefWidthProperty().bind(expensesTable.prefWidthProperty());
        }
    }
    @FXML
    TextField inputName;

    @FXML
    Accordion accordion;
    @FXML
    public void onAddPerson(ActionEvent e) {
        String name = inputName.getText();
        inputName.setText("");

        System.out.println(name);
        IPerson p = viewModel.addPerson(name);

        if(p!=null){
            addPersonBlock(p);
            return;
        }

        final Stage errorPopup = new Stage();
        errorPopup.initModality(Modality.APPLICATION_MODAL);
        errorPopup.initOwner(primaryStage);


        VBox dialogVbox = new VBox(20);
        Text form = new Text("Invalid name.");
        dialogVbox.getChildren().add(form);


        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        errorPopup.setScene(dialogScene);
        errorPopup.show();
    }

    public void addPersonBlock(IPerson p){
        TitledPane tp = createPersonBlock(p);
        accordion.getPanes().add(tp);
    }

}
