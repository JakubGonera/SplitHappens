package com.ogr.splithappens;

import com.ogr.splithappens.IOservice.IOService;
import com.ogr.splithappens.model.PersonsManager;
import com.ogr.splithappens.viewmodel.IViewModel;
import com.ogr.splithappens.viewmodel.ViewModel;
import com.ogr.splithappens.view.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Program extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        PersonsManager personsManager = IOService.readData();
        IViewModel viewModel = new ViewModel(personsManager, personsManager.getExpenseManager());

        View view = new View(viewModel, stage);

        FXMLLoader fxmlLoader = new FXMLLoader(Program.class.getResource("view.fxml"));
        fxmlLoader.setController(view);

        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        stage.setTitle("SplitHappens");
        stage.setScene(scene);
        stage.show();

        view.setBindings();
    }

    public static void main(String[] args) {
        launch();
    }
}
