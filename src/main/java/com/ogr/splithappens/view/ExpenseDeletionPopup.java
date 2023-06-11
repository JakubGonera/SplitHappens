package com.ogr.splithappens.view;

import com.ogr.splithappens.Program;
import com.ogr.splithappens.model.Expense;
import com.ogr.splithappens.model.Person;
import com.ogr.splithappens.viewmodel.IViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ExpenseDeletionPopup {
    Stage window;
    IViewModel vm;
    Expense expense;
    Person person;
    @FXML
    public void onContinue() {
        vm.removeExpense(expense.getID());
        person.getPersonsManager().setPersonsActiveFromExpense(expense);
        vm.refreshPersons();
        window.close();
    }
    @FXML
    public void onCancel() {
        window.close();
    }
    public void show(Expense expense, Person person, IViewModel vm) {
        this.expense = expense;
        this.person = person;
        this.vm = vm;
        final Stage dialog = new Stage();
        dialog.setTitle("Confirm deletion");

        FXMLLoader loader = new FXMLLoader(Program.class.getResource("ExpenseDeletionPopup.fxml"));
        loader.setController(this);

        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setResizable(false);

        try {
            dialog.setScene(new Scene(loader.load()));
            window = dialog;
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
