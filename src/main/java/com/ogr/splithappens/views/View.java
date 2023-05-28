package com.ogr.splithappens.views;

import com.ogr.splithappens.models.IExpense;
import com.ogr.splithappens.models.IPerson;
import com.ogr.splithappens.models.Pair;
import com.ogr.splithappens.viewmodels.IViewModel;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyProperty;
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
    private final IViewModel viewModel;
    private final Stage primaryStage;
    @FXML
    Button newExpense;
    @FXML
    VBox expensesTable;

    public View(IViewModel viewModel, Stage primaryStage){
        this.viewModel = viewModel;
        this.primaryStage = primaryStage;
    }

    public void setBindings(){
        viewModel.getExpensesList().addListener(new ChangeListener<List<IExpense>>() {
            @Override
            public void changed(ObservableValue<? extends List<IExpense>> observableValue, List<IExpense> iExpenses, List<IExpense> t1) {
                recalculateExpensesTable(t1);
                System.out.println("BINDING!");
                updatePeople();

            }
        });

        newExpense.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openNewExpense();

            }

        });


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
            VBox child = ExpenseBlockFactory.createExpenseBlock(expense, getUniquePerson(personList.stream(), expense.getPayerID()), viewModel);
            expensesTable.getChildren().add(child);
            child.prefWidthProperty().bind(expensesTable.prefWidthProperty());
        }
    }




    //          PEOPLE


    @FXML
    TextField inputName;

    @FXML
    Accordion accordion;



    @FXML
    public void INP_OnButton(ActionEvent e){
        System.out.println("Exit");
    }

    public void updatePeople(){
        System.out.println("Updating people");
        System.out.println("Number of expenses: " + viewModel.getExpensesList().getValue().size());

        ReadOnlyListProperty<IPerson> people = viewModel.getPersonsList();
        accordion.getPanes().remove(0, accordion.getPanes().size());
        for(IPerson ip: people.getValue()){
            TitledPane tp = createPersonBlock(ip);
            accordion.getPanes().add(tp);
        }
    }
    @FXML
    public void onAddPerson(ActionEvent e) {
        String name = inputName.getText();
        inputName.setText("");

        viewModel.addPerson(name);
        updatePeople();


    }

//    @FXML
    public void onPersonOptionesSchema(int idx, ActionEvent e) {
        System.out.println("pressed button: "+ Integer.valueOf(idx).toString());

    }


}
