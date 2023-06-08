package com.ogr.splithappens.views;

import com.ogr.splithappens.Program;
import com.ogr.splithappens.models.IPerson;
import com.ogr.splithappens.viewmodels.IViewModel;
import com.ogr.splithappens.viewmodels.ViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ConfirmDeletionPopup {
    Stage window;
    IViewModel vm;
    View view;
    IPerson person;
    @FXML
    Text TextField;
    @FXML
    public void onClickDelete() {
        //person.setInactive();
        vm.deletePerson(person);
        view.updatePeople();
        window.close();
    }
    public void show(IPerson p, View view, IViewModel vm) {
        this.person = p;
        this.view = view;
        this.vm = vm;
        final Stage dialog = new Stage();
        dialog.setTitle("Confirm deletion");

        FXMLLoader loader = new FXMLLoader(Program.class.getResource("ConfirmDeletionPopup.fxml"));
        loader.setController(this);

        try {
            dialog.setScene(new Scene(loader.load()));
            window = dialog;
            dialog.show();
            TextField.setText("Are you sure you want to delete " + person.getName() + "?");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
