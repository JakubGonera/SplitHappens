package com.ogr.splithappens.view;

import com.ogr.splithappens.model.IPerson;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.scene.control.TitledPane;

import static com.ogr.splithappens.view.PersonBlockFactory.createPersonBlock;

public class MainViewPersonHandler {
    private final View view;
    MainViewPersonHandler(View view) {
        this.view = view;
    }
    public void updatePeople() {
        System.out.println("Updating people");
        System.out.println("Number of expenses: " + view.viewModel.getExpensesList().getValue().size());

        ReadOnlyListProperty<IPerson> people = view.viewModel.getPersonsList();
        view.accordion.getPanes().remove(0, view.accordion.getPanes().size());
        for (IPerson ip : people.getValue()) {
            TitledPane tp = createPersonBlock(ip);
            view.accordion.getPanes().add(tp);
        }
    }
}
