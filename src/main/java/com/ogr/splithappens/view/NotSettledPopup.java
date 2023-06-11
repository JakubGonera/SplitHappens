package com.ogr.splithappens.view;

import com.ogr.splithappens.Program;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NotSettledPopup {
    Stage window;

    @FXML
    public void onClickOK() {
        window.close();
    }

    public void show() {
        final Stage dialog = new Stage();
        dialog.setTitle("Not settled");

        FXMLLoader loader = new FXMLLoader(Program.class.getResource("NotSettledPopup.fxml"));
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
