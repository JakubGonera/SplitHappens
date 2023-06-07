package com.ogr.splithappens.views;

import com.ogr.splithappens.Program;
import com.ogr.splithappens.models.Expense;
import com.ogr.splithappens.models.IPerson;
import com.ogr.splithappens.models.Pair;
import com.ogr.splithappens.viewmodels.IViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import java.util.ArrayList;


public class SettleUpView {

    @FXML
    Label detailLabel;
    @FXML
    TextField amountField;
    IViewModel viewModel;
    Stage window;

    IPerson payer;
    IPerson receiver;
    int amount;

    SettleUpView(IViewModel vm) {
        viewModel = vm;
    }

    public void DummyTransaction(int payer, int receiver, int amount) {
        ArrayList<Pair<Integer, Integer>> a = new ArrayList<Pair<Integer, Integer>>();
        a.add(new Pair<>(payer, amount));
        viewModel.addExpense(new Expense("Transaction ", receiver, amount, a));
    }

    @FXML
    public void onCancelButton(ActionEvent e) {
        window.close();
    }

    @FXML
    public void onConfirmButton(ActionEvent e) {
        DummyTransaction(payer.getID(), receiver.getID(), Integer.valueOf(amountField.getText()));
        window.close();
    }

    void SetBindings() {
        amountField.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
    }

    public void Show(IPerson payer, IPerson receiver, int amount) {

        this.payer = payer;
        this.receiver = receiver;
        this.amount = amount;

        final Stage transaction = new Stage();
        transaction.setTitle("Transaction");


        FXMLLoader fxmlLoader = new FXMLLoader(Program.class.getResource("SettleUp.fxml"));
        fxmlLoader.setController(this);

        try {
            Scene dialogScene = new Scene(fxmlLoader.load(), 300, 200);
            transaction.setScene(dialogScene);
            transaction.show();
            SetBindings();

        } catch (Throwable t) {
            System.out.println(t);
        }
        detailLabel.setText(payer.getName() + " -> " + receiver.getName());
        amountField.setText(Integer.valueOf(amount).toString());
        window = transaction;


    }

}
