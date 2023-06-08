package com.ogr.splithappens.views;

import com.ogr.splithappens.models.IPerson;
import com.ogr.splithappens.viewmodels.IViewModel;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PersonBlockFactory {

    private static final String currency = "zł";
    public static IViewModel viewModel;

    static TitledPane createPersonBlock(IPerson p) {

        VBox vb = new VBox();
        HBox head = new HBox();

//        Button settings = new Button("Settings");
//        Button remove = new Button("Remove");
//        head.getChildren().add(settings);
//        head.getChildren().add(remove);
        vb.getChildren().add(head);

        for (var exp : p.getDetailedBalances()) {

            String text;
            if (exp.balance() == 0) continue;
            if (exp.balance() > 0)
                text = " <- " + Double.valueOf(exp.balance() * 0.01).toString() + " " + currency + " from " + exp.name();
            else
                text = " -> " + Double.valueOf(-exp.balance() * 0.01).toString() + " " + currency + " to " + exp.name();

            Label l = new Label(text + " ");

            Button b = new Button("...");
            b.setScaleY(0.7);
            b.setOnAction(x -> onPersonOptionsSchema(p, viewModel.getPersonByName(exp.name()), exp.balance(), x));

            HBox hb = new HBox();
            hb.getChildren().add(l);
            hb.getChildren().add(b);
            vb.getChildren().add(hb);
        }

        if(p.getDetailedBalances().size()==0){
            Label l = new Label("Nothing to show");
            HBox hb = new HBox();
            hb.getChildren().add(l);
            vb.getChildren().add(hb);

        }

        AnchorPane ap = new AnchorPane();
        ap.setId("eee");
        ap.getChildren().add(vb);
        TitledPane tp = new TitledPane();

        tp.setContent(ap);

        String balanceText;
        if (p.getBalance() == 0) balanceText = "(Settled up)";
        else balanceText = "(" + Double.valueOf(p.getBalance() * 0.01).toString() + " " + currency + ")";
//        tp.setText(p.getName() + " " + balanceText);


        BorderPane borderPane = new BorderPane();
        Label titleOfTitledPane = new Label(p.getName() + " " + balanceText);
        Button buttonClose = new Button("X");
        borderPane.setCenter(titleOfTitledPane);
        borderPane.setRight(buttonClose);
        borderPane.prefWidthProperty().bind(tp.widthProperty().subtract(40));
        borderPane.setPrefHeight(23);
        tp.setGraphic(borderPane);

        return tp;
    }


    public static void onPersonOptionsSchema(IPerson payer, IPerson receiver, int balance, ActionEvent e) {
        if (balance < 0)
            new SettleUpView(viewModel).Show(receiver, payer, -balance);
        else
            new SettleUpView(viewModel).Show(payer, receiver, balance);
    }


}
