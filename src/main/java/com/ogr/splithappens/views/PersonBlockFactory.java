package com.ogr.splithappens.views;

import com.ogr.splithappens.models.IPerson;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

public class PersonBlockFactory {

    static TitledPane createPersonBlock(IPerson p){

        int b = p.getBalance();

        Label l = new Label();
        if(b == 0) l.setText("Settled ;)");
        else l.setText(Integer.valueOf(b).toString());

        AnchorPane ap = new AnchorPane(l);

        TitledPane tp = new TitledPane();
        tp.setContent(ap);
        tp.setText(p.getName());

        return tp;

    }


}
