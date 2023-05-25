package com.ogr.splithappens.views;

import com.ogr.splithappens.models.IPerson;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PersonBlockFactory {

    private static final String currency = "gr";
        
    static TitledPane createPersonBlock(IPerson p){

        AnchorPane ap = new AnchorPane();

        for(var exp: p.getDetailedBalances()){

            String text;
            if(exp.balance()==0) continue;
            if(exp.balance()>0)
                text = "<- " + Integer.valueOf(exp.balance()).toString() + " " + currency + " from " + exp.name();
            else
                text = "-> " + Integer.valueOf(-exp.balance()).toString() + " " + currency + " to " + exp.name();

            Label l = new Label(text);
            ap.getChildren().add(l);
        }

        TitledPane tp = new TitledPane();
        tp.setContent(ap);

        String balanceText;
        if(p.getBalance()==0) balanceText = "(Settled up)";
        else balanceText = "(" + Integer.valueOf(p.getBalance()).toString() + " " + currency + ")";
        tp.setText(p.getName() + " " + balanceText );

        return tp;

    }


}
