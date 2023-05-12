package com.ogr.splithappens;

import com.ogr.splithappens.models.ExpenseManager;
import com.ogr.splithappens.models.IExpenseManager;
import com.ogr.splithappens.models.IPersonsManager;
import com.ogr.splithappens.models.PersonsManager;
import com.ogr.splithappens.viewmodels.IViewModel;
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



        IExpenseManager expenseManager = new ExpenseManager();
        IPersonsManager personsManager = new PersonsManager(expenseManager);
        IViewModel viewModel = new ViewModel(personsManager, expenseManager);

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
