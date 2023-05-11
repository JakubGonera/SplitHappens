package com.ogr.splithappens;

import com.ogr.splithappens.models.ExpenseManager;
import com.ogr.splithappens.models.PersonsManager;
import com.ogr.splithappens.viewmodels.ViewModel;
import com.ogr.splithappens.views.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Program extends Application {
    @Override
    public void start(Stage stage) throws IOException {


        PersonsManager pm = new PersonsManager();
        ExpenseManager em = new ExpenseManager();
        ViewModel vm = new ViewModel(pm, em);

        View c = new View(vm);

        FXMLLoader fxmlLoader = new FXMLLoader(Program.class.getResource("view.fxml"));
        fxmlLoader.setController(c);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 320, 240);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}
