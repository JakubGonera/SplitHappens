package com.ogr.splithappens.view;

import com.ogr.splithappens.Program;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class InvalidNamePopupView {


    @FXML
    Button OkButton;

    @FXML
    Button ExitButton;

    @FXML
    public Label textLabel;

    @FXML
    public void OnButton(ActionEvent a) {
        popup.close();
    }


    static Stage popup;


    public void Show(String message) {

        final Stage errorPopup = new Stage();
        errorPopup.setTitle("Invalid name");

        FXMLLoader fxmlLoader = new FXMLLoader(Program.class.getResource("InvalidNamePopup.fxml"));
        fxmlLoader.setController(this);

        errorPopup.initModality(Modality.APPLICATION_MODAL);
        errorPopup.setResizable(false);

        try {
            Scene dialogScene = new Scene(fxmlLoader.load(), 300, 200);
            errorPopup.setScene(dialogScene);
            errorPopup.show();
        } catch (Throwable t) {
        }

        textLabel.setText("Invalid name: \n" + message);
        popup = errorPopup;

    }

}
