package com.ogr.splithappens.views;

import com.ogr.splithappens.models.IPerson;
import com.ogr.splithappens.viewmodels.IViewModel;
import com.ogr.splithappens.viewmodels.ViewModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import static com.ogr.splithappens.views.PersonBlockFactory.createPersonBlock;

public class View {



    private final IViewModel viewModel;
    private final Stage primaryStage;

    @FXML
    TextField inputName;

    @FXML
    Accordion accordion;

    public View(IViewModel viewModel, Stage primaryStage){
        this.viewModel = viewModel;
        this.primaryStage = primaryStage;
    }


    @FXML
    public void onAddPerson(ActionEvent e) {
        String name = inputName.getText();
        inputName.setText("");

        System.out.println(name);
        IPerson p = viewModel.addPerson(name);

        if(p!=null){
            addPersonBlock(p);
            return;
        }

        final Stage errorPopup = new Stage();
        errorPopup.initModality(Modality.APPLICATION_MODAL);
        errorPopup.initOwner(primaryStage);


        VBox dialogVbox = new VBox(20);
        Text form = new Text("Invalid name.");
        dialogVbox.getChildren().add(form);


        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        errorPopup.setScene(dialogScene);
        errorPopup.show();
    }

    public void addPersonBlock(IPerson p){
        TitledPane tp = createPersonBlock(p);
        accordion.getPanes().add(tp);
    }

}
