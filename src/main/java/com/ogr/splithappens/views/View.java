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
import javafx.fxml.FXMLLoader;
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

import java.io.IOException;
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
        // Method that opens pop-up for filling in details for a new expense
        final Stage expenseWindow = new Stage();
        ExpenseController controller = new ExpenseController(viewModel, expenseWindow);

        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("addExpense.fxml"));
        fxmlLoader.setController(controller);

        expenseWindow.initModality(Modality.APPLICATION_MODAL);
        expenseWindow.initOwner(primaryStage);
        expenseWindow.setResizable(false);

        try {
            Scene dialogScene = new Scene(fxmlLoader.load(), 400, 450);
            expenseWindow.setScene(dialogScene);
            expenseWindow.show();
            controller.setBindings();
        }
        catch(IOException ignored){

        }
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
