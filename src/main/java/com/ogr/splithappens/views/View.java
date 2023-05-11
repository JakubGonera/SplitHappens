package com.ogr.splithappens.views;

import com.ogr.splithappens.viewmodels.ViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.TextField;

public class View {

    private final ViewModel viewModel;



    @FXML
    TextField inputName;


    public View(ViewModel vm){
        this.viewModel = vm;
    }
    @FXML
    public void onAddPerson(ActionEvent e) {
        String name = inputName.getText();
        System.out.println(name);
        viewModel.AddPerson(name);
    }

}
