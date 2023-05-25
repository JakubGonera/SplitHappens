package com.ogr.splithappens.views;

import com.ogr.splithappens.Program;
import com.ogr.splithappens.models.IExpense;
import com.ogr.splithappens.models.IPerson;
import com.ogr.splithappens.models.Pair;
import com.ogr.splithappens.viewmodels.IViewModel;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class InvalidNamePopupView {


    @FXML
    Button OkButton;

    @FXML
    Button ExitButton;

    @FXML
    public Label textLabel;

    @FXML
    public void OnButton(ActionEvent a){
        popup.close();
    }


    static Stage popup;


     public void Pop(String message){

        final Stage errorPopup = new Stage();
        errorPopup.setTitle("Invalid name");


        FXMLLoader fxmlLoader = new FXMLLoader(Program.class.getResource("InvalidNamePopup.fxml"));
        fxmlLoader.setController(this);

        try {
            Scene dialogScene = new Scene(fxmlLoader.load(), 300, 200);
            errorPopup.setScene(dialogScene);
            errorPopup.show();
        } catch (Throwable t){
            System.out.println(t);
        }


        textLabel.setText("Invalid name: \n" + message);
        popup = errorPopup;

    }

}
